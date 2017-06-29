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
package org.carp.beans;

import org.carp.exception.CarpException;

/**
 * ע��ӿڽ�����
 * @author zhou
 * @since 0.1
 */
public interface AnnotationProcessor {
	
	/**
	 * ����ע��Ԫ���ݣ�����ע���Ԫ���ݶ���
	 * @param bean
	 * @param cls
	 * @throws CarpException
	 */
	void parse(CarpBean bean, Class<?> cls) throws CarpException;
}
