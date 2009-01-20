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
package at.gv.egiz.bku.slcommands.impl;

import at.buergerkarte.namespaces.securitylayer._1.InfoboxReadRequestType;
import at.buergerkarte.namespaces.securitylayer._1.InfoboxUpdateRequestType;
import at.gv.egiz.bku.slcommands.InfoboxReadResult;
import at.gv.egiz.bku.slcommands.InfoboxUpdateResult;
import at.gv.egiz.bku.slcommands.SLCommandContext;
import at.gv.egiz.bku.slexceptions.SLCommandException;

/**
 * An abstract base class for {@link Infobox} implementations.
 * 
 * @author mcentner
 */
public abstract class AbstractInfoboxImpl implements Infobox {

  @Override
  public InfoboxReadResult read(InfoboxReadRequestType request,
      SLCommandContext cmdCtx) throws SLCommandException {
    throw new SLCommandException(4011);
  }

  @Override
  public InfoboxUpdateResult update(InfoboxUpdateRequestType request,
      SLCommandContext cmdCtx) throws SLCommandException {
    throw new SLCommandException(4011);
  }
  
}
