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

import org.carp.annotation.CarpAnnotation.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * �ֶ�ע����
 * 
 * @author zhou
 * @since 0.1
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Column{
	/**
	 * ��������Ӧ�����ݱ��ֶ����ơ�
	 */
	String name();
	
	/** 
	 * �Ƿ��ֵ ��Ĭ��Ϊ��ֵ 
	 */
	Nullable isNull() default Nullable.Yes;
	/** 
	 * �ֶ�˵��,��Ӧ�����ݱ��ֶε�ע�� 
	 */
	String remark()default "";//�ֶ�˵��
	/** 
	 * ���ݳ��ȣ�����Ǹ����ͣ���ô��ֵΪ�������ֵĳ��� 
	 */
	int length() default 0;//����
	/** 
	 * ���ȡ����Ը�������Ч��ΪС��λ�ĳ��� 
	 * */
	int precision() default 0;//����
}
