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
package org.carp.engine.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.carp.engine.metadata.MetaData;
import org.carp.exception.CarpException;
import org.carp.impl.AbstractCarpQuery;
import org.carp.sql.CarpSql;
import org.carp.sql.CarpSql.PageSupport;

/**
 * java.sql.ResultSet �������¼�Ķ�����װ��
 * �ѽ�����ļ�¼ת����װ�ɶ���(class object)��װ��list�����з���
 * @author zhou
 *
 */
public class ResultSetProcessor {
	private AbstractCarpQuery query;
	private Class<?> cls;
	private ResultSet rs;
	private MetaData cqmd;
	
	/**
	 * ���캯��
	 * @param query �����ѯ����
	 * @param cqmd  �����Ԫ����
	 * @param rs  �����
	 * @throws CarpException
	 */
	public ResultSetProcessor(AbstractCarpQuery query,MetaData cqmd,ResultSet rs) throws CarpException{
		this(query.getCls(),cqmd,rs);
		this.query = query;
	}
	
	/**
	 * ���캯��
	 * @param cls ��װ������
	 * @param cqmd  �����Ԫ����
	 * @param rs  �����
	 * @throws CarpException
	 */
	public ResultSetProcessor(Class<?> cls,MetaData cqmd,ResultSet rs) throws CarpException{
		this.cls = cls;
		this.rs = rs;
		this.cqmd = cqmd;
	}
	
	/**
	 * ȡ�ü�¼ת���ɶ���ļ���
	 * @return ���󼯺�
	 * @throws Exception
	 */
	public Object get()throws Exception{
		Object o = null;
		while(rs.next()){
			o = processResultSet();
			break;
		}
		return o;
	}
	
	/**
	 * �����صĽ������¼ת��ΪDataSet����
	 * @param row
	 * @throws Exception
	 */
	public void createDataSet(List<List<Object>> row)throws Exception{
		PageSupport mode = this.query.getCarpSql().pageMode();
		if(mode == PageSupport.COMPLETE){// ֧�ַ�ҳ
			while(rs.next())
				processResultSetToDataSet(row);
		}else{
			scrollResutSetCursor();
			if(mode == PageSupport.PARTIAL)//  ���ַ�ҳ
				while(rs.next())
					processResultSetToDataSet(row);
			else{ // ��֧�ַ�ҳ
				int c = 0;
				while(rs.next() && c<this.query.getMaxCount()){
					processResultSetToDataSet(row);
					++c;
				}
			}
		}
	}
	
	/**
	 * ��һ����¼�洢��list�б���
	 * @param row
	 * @throws Exception
	 */
	private void processResultSetToDataSet(List<List<Object>> row) throws Exception{
		List<Object> data = new ArrayList<Object>();
		List<String> cols = cqmd.getColumns();
		for(String col : cols){
			cqmd.getAssemble(col).setValue(rs, data, col);
		}
		row.add(data);
	}
	
	/**
	 * ȡ�ü�¼ת���ɶ���ļ���
	 * @return ���󼯺�
	 * @throws Exception
	 */
	public List<Object> list()throws Exception{
		List<Object> list = new ArrayList<Object>();
		PageSupport mode = this.query.getCarpSql().pageMode();
		if(mode == PageSupport.COMPLETE){// mode = 0 ֧�ַ�ҳ
			while(rs.next())
				list.add(processResultSet());
		}else{
			scrollResutSetCursor();
			if(mode == PageSupport.PARTIAL)// mode = 1 ���ַ�ҳ
				while(rs.next())
					list.add(processResultSet());
			else{ //mode = 2 //��֧�ַ�ҳ
				int c = 0;
				while(rs.next() && c<this.query.getMaxCount()){
					list.add(processResultSet());
					++c;
				}
			}
		}
		return list;
	}
	
	/**
	 * �Ѽ�¼ת����װ�ɶ���
	 * @return ��װ�Ķ���
	 * @throws Exception
	 */
	private Object processResultSet() throws Exception{
		Object vo = this.cls.newInstance();
		for(int i=0; i<cqmd.getColumnCount(); ++i){
			String col = cqmd.getColumnName(i);
			if(col.equals("ROW_NUM")){
				continue;
			}
			cqmd.getAssemble(col).setFieldValue(rs, vo, cqmd.getField(col), col);
		}
		return vo;
	}
	
	/**
	 * �����֧�ַ�ҳ�Ͳ���֧�֣������Ϊ��������������ƶ����
	 * @throws SQLException
	 */
	private void scrollResutSetCursor() throws SQLException{
		if(this.query.getFirstIndex() != -1){
			if(this.query.getCarpSql().enableScrollableResultSet())
				rs.absolute(this.query.getFirstIndex());
			else{
				int c = 0;
				while(rs.next()){
					if((c++)>=this.query.getFirstIndex())
						break;
				}
			}
		}
	}
}
