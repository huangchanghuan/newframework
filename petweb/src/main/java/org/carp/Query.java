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

import java.math.BigDecimal;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.carp.exception.CarpException;

/**
 * ��ѯ�ӿڶ�����
 * @author zhou
 * @since 0.1
 */
public interface Query {
	/**
	 * ��ѯ�ַ���
	 * @return
	 */
	String getQueryString();
	/**
	 * ��ѯsql��select�б��ֶε���������
	 * @return
	 */
	Class<?>[] getReturnTypes();
	
	/**
	 * ��ѯsql��select�б��ֶε���������
	 * @return
	 */
	String[] getReturnNames();
	/**
	 * ֱ�ӷ��ز�ѯsql���Ľ���������صĽ��������ȡ�������ݿ��Ƿ�֧�ַ�ҳ���ܣ������֧���򷵻�ȫ������
	 * @return
	 * @throws CarpException
	 */
	ResultSet resultSet()throws CarpException;
	/**
	 * ���ò�ѯsql����ʼ����
	 * @param first
	 * @return
	 */
	Query setFirstIndex(int first);
	/**
	 * ���ò�ѯ�Ľ���������Է��ض�������¼��
	 * @param last
	 * @return
	 */
	Query setMaxCount(int last);
	/**
	 * ���ò�ѯ��ʱ
	 * @param timeout
	 * @return
	 */
	Query setTimeout(int timeout);
	/**
	 * ����Statement�����fetch��С
	 * @param fetchSize
	 * @return
	 */
	Query setFetchSize(int fetchSize);
	/**
	 * ִ��sql(update,delete,insert)���
	 * @return
	 * @throws CarpException
	 */
    int executeUpdate() throws CarpException;
    /**
     * ��������ʽִ��Sql(update,delete,insert)���
     * @throws CarpException
     */
    void	executeBatch()throws CarpException;
    /**
     * ��sql(update,delete,insert)��䣬��ӵ���������
     * @return
     * @throws CarpException
     */
    Query addBatch() throws CarpException;
    /**
     * ����session�еĲ�ѯ����
     * @return
     * @throws SQLException
     */
    Query clearParameters() throws SQLException;
    /**
     * 
     * @param index
     * @param sqlType
     * @return
     * @throws SQLException
     */
    Query setNull(int index, int sqlType) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setBoolean(int index, boolean x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setByte(int index, byte x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setShort(int index, short x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setInt(int index, int x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setLong(int index, long x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setFloat(int index, float x) throws SQLException;
    Query setDouble(int index, double x) throws SQLException;
    Query setBigDecimal(int index, BigDecimal x) throws SQLException;
    Query setString(int index, String x) throws SQLException;
    Query setBytes(int index, byte x[]) throws SQLException;
    Query setDate(int index, Date value) throws SQLException;
    Query setDate(int index, java.sql.Date value) throws SQLException;
    Query setTime(int index, Date value)  throws SQLException;
    Query setTime(int index, java.sql.Time value)  throws SQLException;
    Query setTimestamp(int index, Date value)  throws SQLException;
    Query setAsciiStream(int index, java.io.InputStream x)  throws SQLException;
    Query setAsciiStream(int index, java.io.Reader reader)  throws SQLException;
    Query setBinaryStream(int index, java.io.InputStream x) throws SQLException;
    Query setObject(int index, Object x) throws SQLException;
    Query setCharacterStream(int index, java.io.Reader reader) throws SQLException;
    Query setRef(int index, Ref x) throws SQLException;
    Query setURL(int index, java.net.URL x) throws SQLException;
}
