/*
* Copyright 2008 Federal Chancellery Austria and
* Graz University of Technology
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package at.gv.egiz.smcc;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.gv.egiz.smcc.util.ISO7816Utils;
import at.gv.egiz.smcc.util.SMCCHelper;

public class BELPICCard extends AbstractSignatureCard implements SignatureCard {
  
    /**
     * Logging facility.
     */
    private static Log log = LogFactory.getLog(BELPICCard.class);

    public static final byte[] MF = new byte[] { (byte) 0x3F, (byte) 0x00 };

    public static final byte[] DF_BELPIC = new byte[] { (byte) 0xDF,
            (byte) 0x00 };

    public static final byte[] DF_ID = new byte[] { (byte) 0xDF, (byte) 0x01 };

    public static final byte[] SIGN_CERT = new byte[] { (byte) 0x50,
            (byte) 0x39 };

//    public static final byte MSE_SET_ALGO_REF = (byte) 0x02;

//    public static final byte MSE_SET_PRIV_KEY_REF = (byte) 0x83;

    public static final int SIGNATURE_LENGTH = (int) 0x80;

    public static final byte KID = (byte) 0x01;

    public static final int READ_BUFFER_LENGTH = 256;

    public static final int PINSPEC_SS = 0;
    
  private static final PINSpec SS_PIN_SPEC = 
    new PINSpec(4, 12, "[0-9]",
      "at/gv/egiz/smcc/BELPICCard", "sig.pin", KID, DF_BELPIC);
    
  /**
   * Creates a new instance.
   */
  public BELPICCard() {
    super("at/gv/egiz/smcc/BelpicCard");
    pinSpecs.add(SS_PIN_SPEC);
  }

  @Override
  @Exclusive
  public byte[] getCertificate(KeyboxName keyboxName)
      throws SignatureCardException {

    if (keyboxName != KeyboxName.SECURE_SIGNATURE_KEYPAIR) {
      throw new IllegalArgumentException("Keybox " + keyboxName
          + " not supported");
    }

    try {
      CardChannel channel = getCardChannel();
      // SELECT MF
      execSELECT_FID(channel, MF);
      // SELECT application
      execSELECT_FID(channel, DF_BELPIC);
      // SELECT file
      execSELECT_FID(channel, SIGN_CERT);
      // READ BINARY
      byte[] certificate = ISO7816Utils.readTransparentFileTLV(channel, -1, (byte) 0x30);
      if (certificate == null) {
        throw new NotActivatedException();
      }
      return certificate;
    } catch (FileNotFoundException e) {
      throw new NotActivatedException();
    } catch (CardException e) {
      log.info("Failed to get certificate.", e);
      throw new SignatureCardException(e);
    }

  }

  @Override
  @Exclusive
  public byte[] getInfobox(String infobox, PINProvider provider, String domainId)
      throws SignatureCardException, InterruptedException {
      
    throw new IllegalArgumentException("Infobox '" + infobox
        + "' not supported.");
  }

  @Override
  @Exclusive
  public byte[] createSignature(InputStream input, KeyboxName keyboxName,
      PINProvider provider, String alg) throws SignatureCardException, InterruptedException, IOException {
    
    if (KeyboxName.SECURE_SIGNATURE_KEYPAIR != keyboxName) {
      throw new SignatureCardException("Card does not support key " + keyboxName + ".");
    }
    if (!"http://www.w3.org/2000/09/xmldsig#rsa-sha1".equals(alg)) {
      throw new SignatureCardException("Card does not support algorithm " + alg + ".");
    }
    
    byte[] dst = new byte[] { (byte) 0x04, // number of following
        // bytes
        (byte) 0x80, // tag for algorithm reference
        (byte) 0x02, // algorithm reference
        (byte) 0x84, // tag for private key reference
        (byte) 0x83 // private key reference
    };
    
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      log.error("Failed to get MessageDigest.", e);
      throw new SignatureCardException(e);
    }
    // calculate message digest
    byte[] digest = new byte[md.getDigestLength()];
    for (int l; (l = input.read(digest)) != -1;) {
      md.update(digest, 0, l);
    }
    digest = md.digest();

    try {
      
      CardChannel channel = getCardChannel();

      // SELECT MF
      execSELECT_FID(channel, MF);
      // VERIFY
      execMSE(channel, 0x41, 0xb6, dst);
      // PERFORM SECURITY OPERATION : COMPUTE DIGITAL SIGNATURE
      verifyPINLoop(channel, SS_PIN_SPEC, provider);
      // MANAGE SECURITY ENVIRONMENT : SET DST
      return execPSO_COMPUTE_DIGITAL_SIGNATURE(channel, digest);

    } catch (CardException e) {
      log.warn(e);
      throw new SignatureCardException("Failed to access card.", e);
    }

  }

  public String toString() {
    return "Belpic Card";
  }
  
  protected void verifyPINLoop(CardChannel channel, PINSpec spec,
      PINProvider provider) throws LockedException, NotActivatedException,
      SignatureCardException, InterruptedException, CardException {
    
    int retries = -1; //verifyPIN(channel, spec, null, -1);
    do {
      retries = verifyPIN(channel, spec, provider, retries);
    } while (retries > 0);
  }

  protected int verifyPIN(CardChannel channel, PINSpec pinSpec,
      PINProvider provider, int retries) throws SignatureCardException,
      LockedException, NotActivatedException, InterruptedException,
      CardException {
    
    VerifyAPDUSpec apduSpec = new VerifyAPDUSpec(
        new byte[] {
            (byte) 0x00, (byte) 0x20, (byte) 0x00, pinSpec.getKID(), (byte) 0x08,
            (byte) 0x20, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff }, 
        1, VerifyAPDUSpec.PIN_FORMAT_BCD, 7, 4, 4);
    
    ResponseAPDU resp = reader.verify(channel, apduSpec, pinSpec, provider, retries);
    
    if (resp.getSW() == 0x9000) {
      return -1;
    }
    if (resp.getSW() >> 4 == 0x63c) {
      return 0x0f & resp.getSW();
    }
    
    switch (resp.getSW()) {
    case 0x6983:
      // authentication method blocked
      throw new LockedException();
    case 0x6984:
      // reference data not usable
      throw new NotActivatedException();
    case 0x6985:
      // conditions of use not satisfied
      throw new NotActivatedException();
  
    default:
      String msg = "VERIFY failed. SW=" + Integer.toHexString(resp.getSW()); 
      log.info(msg);
      throw new SignatureCardException(msg);
    }
    
  }
  
  protected byte[] execSELECT_FID(CardChannel channel, byte[] fid)
      throws SignatureCardException, CardException {
    
    ResponseAPDU resp = channel.transmit(
        new CommandAPDU(0x00, 0xA4, 0x02, 0x0C, fid, 256));
    
    if (resp.getSW() == 0x6A82) {
      String msg = "File or application not found FID="
          + SMCCHelper.toString(fid) + " SW="
          + Integer.toHexString(resp.getSW()) + ".";
      log.info(msg);
      throw new FileNotFoundException(msg);
    } else if (resp.getSW() != 0x9000) {
      String msg = "Failed to select application FID="
          + SMCCHelper.toString(fid) + " SW="
          + Integer.toHexString(resp.getSW()) + ".";
      log.error(msg);
      throw new SignatureCardException(msg);
    } else {
      return resp.getBytes();
    }
    
  }
  
  protected void execMSE(CardChannel channel, int p1, int p2, byte[] data)
      throws CardException, SignatureCardException {
    ResponseAPDU resp = channel.transmit(
        new CommandAPDU(0x00, 0x22, p1, p2, data, 256));
    if (resp.getSW() != 0x9000) {
      throw new SignatureCardException("MSE:SET failed: SW="
          + Integer.toHexString(resp.getSW()));
    }
  }

  protected byte[] execPSO_COMPUTE_DIGITAL_SIGNATURE(CardChannel channel, byte[] hash)
      throws CardException, SignatureCardException {
    ResponseAPDU resp;
    resp = channel.transmit(
        new CommandAPDU(0x00, 0x2A, 0x9E, 0x9A, hash, 256));
    if (resp.getSW() == 0x6982) {
      throw new SecurityStatusNotSatisfiedException();
    } else if (resp.getSW() == 0x6983) {
      throw new LockedException();
    } else if (resp.getSW() != 0x9000) {
      throw new SignatureCardException(
          "PSO: COMPUTE DIGITAL SIGNATRE failed: SW="
              + Integer.toHexString(resp.getSW()));
    } else {
      return resp.getData();
    }
  }



    
}