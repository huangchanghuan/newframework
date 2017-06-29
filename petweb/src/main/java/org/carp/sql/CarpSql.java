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
package org.carp.sql;

import org.carp.beans.CarpBean;
import org.carp.exception.CarpException;
import org.carp.impl.AbstractCarpSession;

public interface CarpSql {
	/**
	 * ȡSchema
	 * @return
	 */
	String getSchema();
	/**
	 * ����Schema
	 * @param schema
	 */
	void setSchema(String schema);
	/**
	 * ����Classȡ�ö�Ӧ��Class Info
	 * @return
	 */
	CarpBean getBeanInfo();
		
	/**
	 * ����pojo Class
	 * @param cls
	 */
	void setClass(Class<?> cls)throws CarpException;
	
	/**
	 * �����������ƣ�ȡ���������ɵ�sql
	 * @param seq
	 * @return
	 */
	String getSequenceSql(String seq) throws CarpException;
	
	/**
	 * ȡ��ִ�з�ҳ��ѯʱ��select��ѯ�ķ�ҳsql��� 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	String getPageSql(String sql) throws Exception;
	
	/**
	 * ȡ��ִ�з�ҳ��ѯʱ��select��ѯ�ķ�ҳsql��� 
	 * @return
	 * @throws Exception
	 */
	String getPageSql() throws Exception;
	
	/**
	 * ȷ���󶨲�ѯ��ҳ����λ����Ϣ
	 * @throws Exception
	 */
	int position();
	
	/**
	 * COMPLETE  ��ȫ֧�� ,��������Ҫ��ļ�¼
	 * PARTIAL  ����֧�֣�����ǰN����¼����Ҫʹ�ù�������������˵�ǰN-m��������ʹ�ú����m����¼
	 * NONE  ��֧�֣�����ȫ����¼��ʹ�ù������������λ��n-m����¼���ٽ���ȷ��m����¼���ɡ�
	 */
	enum PageSupport{COMPLETE,PARTIAL,NONE};
	
	/**
	 * ��ҳģʽ
	 * ����ȷ�����ݿ��Ƿ�֧�ַ�ҳ�������֧�֣�������jdbc�Ĺ������������ѯ����ļ�¼<br/>
	 * 0:֧�ַ�ҳ,��������Ҫ��ļ�¼
	 * 1:����֧�ַ�ҳ������ǰN����¼����Ҫʹ�ù�������������˵�ǰN-m��������ʹ�ú����m����¼
	 * 2:��֧�ַ�ҳ������ȫ����¼��ʹ�ù������������λ��n-m����¼���ٽ���ȷ��m����¼���ɡ�
	 * @return
	 */
	PageSupport pageMode();

	/**
	 * �Ƿ�֧�ֹ��������
	 * @return true/false ֧��/��֧��
	 */
	boolean enableScrollableResultSet();
	/**
	 * ����Class��ȡ��class��Ӧ�����ݱ�Ĳ�ѯsql�������з�ҳ���ܣ�
	 * @return
	 */
	String getQuerySql();
	
	/**
	 * ����������ȡ�õ��������sql
	 * @return
	 */
	String getLoadSql()throws CarpException;
	
	/**
	 * ȡ��ִ��insert����ʱ��insert sql���
	 * @return
	 */
	public String getInsertSql();
	
	/**
	 * ȡ��ִ��updateʱ��update sql���
	 * @return
	 */
	public String getUpdateSql();
	
	/**
	 * ȡ��ִ��delete����ʱ��delete sql���
	 * @return
	 */
	public String getDeleteSql();
	
	/**
	 * ���ò�ѯ����
	 * ��Ϊ��ͬ�����ݿ⣬��ҳ��ʽ��ͬ���еķ�ҳ������ǰ���е��ں��棬������Ҫ֪����sql�Ĳ�������������ҳ�����⣩��
	 * @param ps PreparedStatement����
	 * @param firstIndex ��ʼ����
	 * @param maxIndex	����¼��
	 * @param paramsCount ��ѯsql�еĲ�������
	 * @throws Exception
	 */
	public void setQueryParameters(java.sql.PreparedStatement ps, int firstIndex, int maxIndex, int paramsCount)throws Exception;
}
