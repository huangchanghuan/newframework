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

import java.sql.Connection;
import java.sql.SQLException;

import org.carp.transaction.JDBCTransaction;
import org.carp.transaction.JTATransaction;
import org.carp.transaction.Transaction;
/**
 * JDBC������
 * �����������ݿ⣬�ر����ݿ�����
 * ȡ���������
 * @author zhou
 * @since 0.1
 */
public class JDBCContext {
	private Connection conn;
	private ConnectionProvider context;
	private boolean isClose = false;
	public JDBCContext(ConnectionProvider context){
		this.context = context;
	}
	
	/**
	 * ȡ�����ݿ�����
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException  {
		if(conn !=null && !conn.isClosed())
			return conn;
		conn = context.getDataSource().getConnection();
		return conn;
	}
	
	public void setConnection(Connection conn){
		this.conn = conn;
	}
	
	/**
	 * �ر����ݿ�����
	 * @throws SQLException
	 */
	public void close() throws SQLException{
		if(context.getCarpSetting().getCarpTransaction().equalsIgnoreCase("jdbc")){
			if(conn != null && !conn.isClosed() && !conn.getAutoCommit())
				conn.setAutoCommit(true);
		}
		if(conn != null && !conn.isClosed())
			try{
				conn.close();				
			}catch(Exception e){throw new SQLException("�ر�����ʧ�ܣ�");}
		conn = null;
		this.isClose = true;
	}
	
	public boolean isClose(){
		return this.isClose;
	}
	
	/**
	 * �ж������Ƿ��Ѿ��ύ��
	 * @throws SQLException
	 */
	public void isCommit() throws SQLException{
		if(context.getCarpSetting().getCarpTransaction().equalsIgnoreCase("jdbc")){
			this.getConnection();
			if(conn.getAutoCommit())
				this.conn.setAutoCommit(false);
		}
	}
	
	/**
	 * ��ȡ����
	 * @return
	 * @throws Exception
	 */
	public Transaction getTransaction() throws Exception{
		if(this.context.getCarpSetting().getCarpTransaction().equalsIgnoreCase("jdbc"))
			return new JDBCTransaction(this);
		else
			return new JTATransaction(this);
	}
	
	/**
	 * ȡ��������
	 * @return
	 */
	public ConnectionProvider getContext() {
		return context;
	}
}
