/*
* Copyright 2009 Federal Chancellery Austria and
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

package at.gv.egiz.bku.slcommands.impl;

import at.gv.egiz.idlink.IdentityLinkTransformer;

public class IdentityLinkInfoboxFactory extends AbstractInfoboxFactory {

  private IdentityLinkTransformer identityLinkTransformer;
  
  @Override
  public Infobox createInfobox() {
    IdentityLinkInfoboxImpl infoboxImpl = new IdentityLinkInfoboxImpl();
    infoboxImpl.setIdentityLinkTransformer(identityLinkTransformer);
    return infoboxImpl;
  }

  /**
   * @return the identityLinkTransformer
   */
  public IdentityLinkTransformer getIdentityLinkTransformer() {
    return identityLinkTransformer;
  }

  /**
   * @param identityLinkTransformer the identityLinkTransformer to set
   */
  public void setIdentityLinkTransformer(
      IdentityLinkTransformer identityLinkTransformer) {
    this.identityLinkTransformer = identityLinkTransformer;
  }
  
}