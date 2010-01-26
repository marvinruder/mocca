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
package at.gv.egiz.bku.accesscontroller;

import at.gv.egiz.bku.slcommands.SLCommand;

public class AccessCheckerContext {
	private SLCommand command;
	private AuthenticationClass authenticationClass;
	private String peerUrl;

	public AccessCheckerContext(SLCommand cmd, AuthenticationClass ac, String url) {
		this.command = cmd;
		this.authenticationClass = ac;
		this.peerUrl = url;
	}

	public SLCommand getCommand() {
		return command;
	}

	public AuthenticationClass getAuthenticationClass() {
		return authenticationClass;
	}

	public String getPeerUrl() {
		return peerUrl;
	}
}
