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
package at.gv.egiz.bku.slxhtml.css;

import org.w3c.css.properties.css1.CssMarginRight;
import org.w3c.css.properties.css1.CssMarginSide;
import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;

public class CssMarginRightSLXHTML extends CssMarginRight {

  public CssMarginRightSLXHTML() {
  }

  public CssMarginRightSLXHTML(CssMarginSide another) {
    super(another);
  }

  public CssMarginRightSLXHTML(ApplContext ac, CssExpression expression)
      throws InvalidParamException {
    this(ac, expression, false);
  }

  public CssMarginRightSLXHTML(ApplContext ac, CssExpression expression,
      boolean check) throws InvalidParamException {
    super(ac, expression, check);

    // The margin-top, margin-bottom, margin-left and margin-right properties
    // must be supported by a Citizen Card Environment. Values specified as
    // percentages (cf. section 3.5.1.2) should be supported.

    // The margin property may be supported by a Citizen Card Environment.

    // An instance document must not contain a negative value in the properties
    // mentioned above. Otherwise it must be rejected by the Citizen Card
    // Environment.

    if (CssMarginSLXHTML.isDisallowedMargin(getValue())) {
      throw new SLXHTMLInvalidParamException("margin", getValue(),
          getPropertyName(), ac);
    }

  }

}