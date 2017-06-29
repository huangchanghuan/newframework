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
package org.carp.transaction;

import java.sql.SQLException;

import org.carp.exception.CarpException;

/**
 * JDBC��JTA����ӿ�
 * @author zhou
 * @since 0.1
 */
public interface Transaction {
	/**
	 * ��������
	 * @throws CarpException
	 */
	public void begin() throws CarpException;
	
	/**
	 * �ύ����
	 * @throws CarpException
	 */
	public void commit() throws CarpException;
	
	/**
	 * �ع�����
	 * @throws CarpException
	 */
	public void rollback() throws  CarpException;
	
	/**
	 * close transaction , should rollback it and close if transaction isn't commited 
	 * @throws CarpException
	 */
	//public void close()throws  CarpException;
	public boolean isCommited()throws CarpException;
	
	/**
	 * ִ���˻ع�����
	 * @return
	 */
	public boolean isRollbacked();
	
	
	/**
	 * ����������뼶��
	 */
	public void restoreTransactionIsolationLevel() throws SQLException;
	
	/**
	 * ����������뼶��
	 * @param isolationLevel ���񼶱�
	 */
	public void setTransactionIsolationLevel(int isolationLevel) throws SQLException;
	
	/**
	 * ��ȡ������뼶��
	 * @return ���񼶱�
	 */
	public int getTransactionIsolationLevel() throws SQLException;
}
