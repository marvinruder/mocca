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
package at.gv.egiz.bku.online.webapp;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.gv.egiz.bku.binding.BindingProcessorManager;
import at.gv.egiz.bku.binding.IdFactory;

/**
 * Session listener to trigger the removal of the BindingProcessor
 *
 */
public class SessionTimeout implements HttpSessionListener {
  
  private static Log log = LogFactory.getLog(SessionTimeout.class);

  @Override
  public void sessionCreated(HttpSessionEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
    BindingProcessorManager manager = (BindingProcessorManager) event.getSession().getServletContext().getAttribute(SpringBKUServlet.BEAN_NAME);
    log.info("Removing session: "+event.getSession().getId());
    manager.removeBindingProcessor(IdFactory.getInstance().createId(event.getSession().getId()));
  }

}
