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
package org.carp.jdbc;

import javax.sql.DataSource;

import org.carp.cfg.CarpSetting;
import org.carp.sql.CarpSql;

/**
 * ���ݿ������ṩ�ӿ���
 * @author zhou
 * @since 0.1
 */
public interface ConnectionProvider {
	/**
	 * ȡ������Դ
	 * @return
	 */
	DataSource getDataSource();
	/**
	 * ȡ��Carp��������
	 * @return
	 */
	CarpSetting getCarpSetting();
	/**
	 * ȡ�����ݿ��Ʒ����,�磺Oracle��DB2��MSSqlServer�ȵȡ�
	 * @return
	 */
	String getDatabaseProductName();
	/**
	 * ȡ��sql������
	 * @param cls
	 * @return
	 */
	CarpSql getCarpSql(Class<?> cls);
	
	/**
	 * ȡ��sql��
	 * @return
	 */
	Class<?> getCarpSqlClass();
}
