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
package org.carp.util;

/**
 * ʵ����󹤾���
 * �����ṩ�˶�ʵ��Ĳ�������
 * @author zhou
 * @since 0.1
 */
public class EntityUtil {
	
	/**
	 * �����ݱ��������ת�������field����
	 * @param colname ����
	 * @return field��
	 */
	public static String getFieldName(String colname){
		String[] cols = colname.toLowerCase().split("_");
		String field = "";
		for(String s : cols){
			if(s.trim().equals(""))
				continue;
			if(field.equals(""))
				field = s;
			else{
				if(s.length()==1)
					field += s.toUpperCase();
				else
					field += s.substring(0, 1).toUpperCase()+s.substring(1);
			}
		}
		return field;
	}
}
