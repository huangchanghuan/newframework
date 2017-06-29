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


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.carp.exception.CarpException;
import org.carp.jdbc.JDBCContext;

/**
 * JTA����ӿ�ʵ����
 * @author zhou
 * @since 0.1
 */
public class JTATransaction implements Transaction {
	private static final Logger logger = Logger.getLogger(JTATransaction.class);
	private UserTransaction userTransaction;
	private Connection connection;
	private boolean begin = false;
	private boolean failedCommit = false;
	private boolean commmitted = false;
	private boolean newTransaction = false;
	private boolean rollbacked = false;
	private JDBCContext context;
	private int	srcTransIsolation = -1;
	
	/**
	 * 
	 * @param context
	 * @throws CarpException
	 */
	public JTATransaction(JDBCContext context) throws CarpException {
		InitialContext initCtx;
		try {
			initCtx = new InitialContext();
			userTransaction = (UserTransaction) initCtx.lookup("java:comp/UserTransaction");
			connection = context.getContext().getDataSource().getConnection();
			context.setConnection(connection);
		} catch (Exception e) {
			throw new CarpException("JtaTransaction initialization failed.  not found JTANaming : java:comp/UserTransaction");
		}
	    if (userTransaction == null) {
	    	throw new CarpException("JtaTransaction initialization failed.  UserTransaction was null.");
	    }
	    if (connection == null) {
	    	throw new CarpException("JtaTransaction initialization failed.  Connection was null.");
	    }
	    this.context = context;
	}

	/**
	 * �ύ����
	 */
	public void commit() throws CarpException {
		if(begin)
			throw new CarpException("JTATransaction could not commit. Cause: transaction has already been committed.");
		if (connection != null) {
			if (commmitted) {
				throw new CarpException("JTATransaction could not commit. Cause: transaction has already been committed.");
			}
			try {
				if (newTransaction) 
					userTransaction.commit();
			} catch (Exception e) {
				failedCommit = true;
				throw new CarpException("JtaTransaction could not commit.  Cause: ", e);
			}
			commmitted = true;
		}
	}

	/**
	 * �ύ����
	 */
	public void rollback() throws CarpException {
		if (connection != null) {
			if (!commmitted) {
				try {
					if (userTransaction != null) {
						if (newTransaction) {
							userTransaction.rollback();
						} else {
							userTransaction.setRollbackOnly();
						}
						rollbacked = true;
					}
				} catch (Exception e) {
					throw new CarpException("JtaTransaction could not rollback.  Cause: ", e);
				}
			}
		}
	}

	/**
	 * ��������
	 */
	public void begin() throws  CarpException {
		if(begin)
			return;
		if(failedCommit)
			throw new CarpException("could not re-start transaction.  Cause: failed commit");
		try {
			newTransaction = userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION;
			if (newTransaction) {
				userTransaction.begin();
				if(logger.isDebugEnabled())
					logger.debug("JTATransaction starting...");
			}
		} catch (Exception e) {
			throw new CarpException("JTATransaction could not start transaction.  Cause: ", e);
		}
	}

	public void restoreTransactionIsolationLevel() throws SQLException {
		if(this.srcTransIsolation != -1)
			connection.setTransactionIsolation(this.srcTransIsolation);
	}
	
	public void setTransactionIsolationLevel(int isolationLevel) throws SQLException{
		srcTransIsolation = connection.getTransactionIsolation();//����ԭ����������뼶��,�Ա��ں����ָ�
		int defIsolationLevel = context.getContext().getCarpSetting().getTransIsoLationLevel();
		if(isolationLevel != -1 && isolationLevel != defIsolationLevel)
			connection.setTransactionIsolation(isolationLevel);
		else if(defIsolationLevel !=-1)
			connection.setTransactionIsolation(defIsolationLevel);
	}
	
	public int getTransactionIsolationLevel() throws SQLException{
		return connection.getTransactionIsolation();
	}
	
	public boolean isCommited() throws CarpException {
		return commmitted;
	}

	public boolean isRollbacked() {
		return rollbacked;
	}
}
