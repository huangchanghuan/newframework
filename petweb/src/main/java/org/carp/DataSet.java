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
package org.carp;

import java.util.List;
/**
 * ���ز�ѯ���Ľ�������ݡ�
 * 
 * @author zhou
 * @version 0.1
 */
public interface DataSet {
	/**
	 * �Ƿ�����һ����¼
	 * @return
	 */
	public boolean next();
	/**
	 * �����ֶ����ƣ���ǰ���ָ����еĶ�Ӧ���ֶ�ֵ��
	 * @param name �ֶ�����
	 * @return
	 */
	public Object getData(String name);
	/**
	 * ��������λ�ã���ǰ���ָ����еĶ�Ӧ���ֶ�ֵ��
	 * @param name �ֶ�����
	 * @return
	 */
	public Object getData(int index);
	/**
	 * ���ز�ѯ�������ݼ���
	 * @return
	 */
	public List<List<Object>> getData();
	/**
	 * ���ز�ѯ�������ݵ�title�������б���
	 * @return
	 */
	public List<String> getTitle();
	/**
	 * �ӷ��صĽ�����У����������ţ����ظ�����ָ��ļ�¼��
	 * @param index
	 * @return
	 */
	public List<Object> getRowData(int index) ;
	/**
	 * ���ز�ѯ���Ľ�����е����ͼ��ϣ�����seclect�б���˳������
	 * @return
	 */
	public List<Class<?>> getColumnType();
	/**
	 * ���صļ�¼����
	 * @return
	 */
	public int count();
}