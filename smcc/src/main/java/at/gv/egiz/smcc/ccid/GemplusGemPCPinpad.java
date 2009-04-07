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
package at.gv.egiz.smcc.ccid;

import javax.smartcardio.Card;
import javax.smartcardio.CardTerminal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * bTimeOut = 15sec (too short, leave value from DefaultRearder),
 * however, max is something near 40sec
 * @author Clemens Orthacker <clemens.orthacker@iaik.tugraz.at>
 */
public class GemplusGemPCPinpad extends DefaultReader {

  protected final static Log log = LogFactory.getLog(GemplusGemPCPinpad.class);

  public GemplusGemPCPinpad(Card icc, CardTerminal ct) {
    super(icc, ct);
  }

  @Override
  public byte getwPINMaxExtraDigitL() {
    return (byte) 0x08; 
  }

  @Override
  public byte getwPINMaxExtraDigitH() {
    return (byte) 0x04;
  }
}