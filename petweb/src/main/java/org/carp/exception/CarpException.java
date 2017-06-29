/**
 * Copyright 2009-2011 the original author or authors.
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
package org.carp.exception;

/**
 * �쳣��
 * @author zhou
 * @since 0.1
 */
public class CarpException extends Exception {
	private static final long serialVersionUID = 8570854601466679595L;
	
	public CarpException(){
		super();
	}
	
	public CarpException(Throwable root) {
		super(root);
	}

	public CarpException(String string, Throwable root) {
		super(string, root);
	}

	public CarpException(String s) {
		super(s);
	}
}
