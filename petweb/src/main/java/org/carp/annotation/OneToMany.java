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

import org.carp.annotation.CarpAnnotation.Cascade;
import org.carp.annotation.CarpAnnotation.Lazy;

/**
 * һ�Զ��ϵ��ע�⣬����ע������ΪList��field��<br>
 * <li>foreignKey</li> ����ֶ�
 * <li>foreignName</li> �������
 * <li>childClass</li> ������
 * <li>cascade</li> ��������
 * <li>lazy</li> �Ƿ��ӳټ��أ���δ֧��
 * <li>remark</li> ��ע
 * @author zhou
 * @category annotation
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface OneToMany {
	/**
	 * ����ֶ�����
	 */
	String foreignKey() default "";//�ֶ�����
	
	/**
	 * ����ֶζ�Ӧ��Field����
	 */
	String foreignName();//�ֶ�����
	/**
	 * ��Ӧ������
	 */
	Class<?> childClass();//��Ӧ������
	/**
	 * ����������Ĭ���޼���������
	 */
	Cascade cascade() default Cascade.None;
	/**
	 * �ӳټ�������
	 * @return
	 */
	Lazy	lazy() default Lazy.Yes;
	/**
	 * �ֶ�˵��
	 * @return
	 */
	String remark() default "";
	
}
