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
package org.carp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @category
 * table	���ݿ��<br>
 * schema   Schema��<br>
 * remark	�� - �� ������ 
 * @author Z.Bin
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Table {
	/**
	 * ���ݱ���
	 * @return
	 */
	String name();//����
	/**
	 * ���ݿ��schema����
	 * @return
	 */
	String schema() default "";//���ݿ�Schema
	/**
	 * ���ݿ���ע��
	 * @return
	 */
	String remark() default "";//˵��
}
