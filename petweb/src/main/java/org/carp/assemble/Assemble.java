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
package org.carp.assemble;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

/**
 * �����ݱ���ֶ�ֵ����Ϊ��Ӧ�Ķ�������ֵ
 * @author Zhou
 *
 */
public interface Assemble {	
	/**
	 * ������Ϊindex�е�ֵ��ӵ�������
	 * @param rs �����
	 * @param data ���ݼ���
	 * @param index ������
	 * @throws Exception
	 */
	void setValue(ResultSet rs, List<Object> data, int index)throws Exception;

	/**
	 * ��colname�е�ֵ��ӵ�������
	 * @param rs ��ѯ�����
	 * @param data ���ݼ���
	 * @param colname ����ȡ������
	 * @throws Exception
	 */
	public void setValue(ResultSet rs, List<Object> data, String colname)throws Exception;
	
	/**
	 * ����ʵ���Fieldֵ
	 * @param rs ��ѯ�����
	 * @param entity  ʵ�����
	 * @param f  �����������
	 * @param index ������ֶ��������ӵ�ǰ��¼���У���Ҫ��ȡ���ֶ�����
	 * @return  ��ȡ���ֶ�ֵ
	 * @throws Exception
	 */
	Object setFieldValue(ResultSet rs, Object entity, Field f, int index) throws Exception;
	
	/**
	 * ����ʵ���Fieldֵ
	 * @param rs ��ѯ�����
	 * @param entity  ʵ�����
	 * @param f  �����������
	 * @param colname ����
	 * @return  ��ȡ���ֶ�ֵ
	 * @throws Exception
	 */
	Object setFieldValue(ResultSet rs, Object entity, Field f, String colname) throws Exception;
	/**
	 * ����ʵ������fieldֵ 
	 * @param entity ʵ�����
	 * @param f ʵ�����Զ���
	 * @param value �����õ�fieldֵ
	 * @return fieldֵ
	 * @throws Exception
	 */
	Object setFieldValue(Object entity, Field f, Object value) throws Exception;
}
