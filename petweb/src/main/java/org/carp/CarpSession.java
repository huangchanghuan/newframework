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

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.carp.exception.CarpException;
import org.carp.transaction.Transaction;
/**
 * ���ݿ����ӻỰ��
 * @author zhou
 * @version 0.1
 */
public interface CarpSession {
	/**
	 * �־û��������ݿ�
	 * @param obj ��Ҫ�־û��Ķ���
	 * @return �־û����������ֵ
	 * @throws CarpException
	 */
	Serializable save(Object obj)throws CarpException;
	/**
	 * �־û����ݵ����ݿ�
	 * @param table ��Ҫinsert���ݵ����ݱ�
	 * @param map ������ֵ��Map������ֵ�ԣ�,��Ϊtable����������������ڣ����׳��쳣
	 * @throws CarpException
	 */
	void save(String table, Map<String, Object> map)throws CarpException;
	/**
	 * ɾ������
	 * @param obj ����
	 * @throws CarpException
	 */
	void delete(Object obj)throws CarpException ;
	/**
	 * ɾ������
	 * @param cls ���ݱ��Ӧ��pojo��
	 * @param id ����ֵ
	 * @throws CarpException
	 */
	void delete(Class<?> cls, Serializable id)throws CarpException;
	/**
	 * ɾ������
	 * @param cls ���ݱ���ڵ�pojo��
	 * @param ids ��������ֵ
	 * @throws CarpException
	 */
	void delete(Class<?> cls, Map<String, Object> ids)throws CarpException;
	/**
	 * ����һ������
	 * @param obj ����
	 * @throws CarpException
	 */
	void update(Object obj)throws CarpException;
	/**
	 * ���ݱ�ʶ����һ������
	 * @param cls ������
	 * @param id ����ֵ
	 * @return cls����
	 * @throws CarpException
	 */
	Object get(Class<?> cls, Serializable id)throws CarpException;
	
	/**
	 * ����key����һ��cls���͵Ķ���
	 * @param cls ������
	 * @param key ����ֵ��map
	 * @return cls����
	 * @throws CarpException
	 */
	Object get(Class<?> cls, Map<String, Object> key)throws CarpException;
	/**
	 * �������ݼ���ѯ����
	 * @param sql ��ѯsql
	 * @return ���ݼ���ѯ����
	 * @throws CarpException
	 */
	CarpDataSetQuery creatDataSetQuery(String sql)throws CarpException;
	/**
	 * ������ִ�в�ѯ����
	 * @param sql ��ִ��sql(delete��update��insert)
	 * @return ��ִ�в�ѯ����
	 * @throws CarpException
	 */
	CarpQuery creatUpdateQuery(String sql)throws CarpException;
	/**
	 * ������ѯ����
	 * @param cls ��ѯ��
	 * @return ��ѯ����
	 * @throws CarpException
	 */
	CarpQuery creatQuery(Class<?> cls)throws CarpException;
	/**
	 * ������ѯ����
	 * @param cls ��ѯ��
	 * @param sql ��ѯsql
	 * @return ��ѯ����
	 * @throws CarpException
	 */
	CarpQuery creatQuery(Class<?> cls, String sql)throws CarpException;
	//void flush() throws CarpException;
	/**
	 * ��������
	 */
	Transaction beginTransaction() throws CarpException;
	/**
	 * �ж����ݿ������Ƿ��
	 * @return
	 */
	boolean isOpen();
	/**
	 * �رջỰ
	 * @throws CarpException
	 */
	void close()throws CarpException;
	/**
	 * ȡ�ñ����µĶ����б�ԭʼ�����б�
	 * @return
	 */
	List<Object> getUpdateObjects();
	/**
	 * �������µĶ����б�
	 */
	void clearUpdateObjects();
	/**
	 * �ӻỰ��ȡ�����ݿ�����
	 * @return
	 * @throws CarpException
	 */
	Connection getConnection() throws CarpException;
}
