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
package org.carp.engine.event;

import java.sql.Blob;
import java.sql.Clob;
import java.util.List;

import org.apache.log4j.Logger;
import org.carp.beans.CarpBean;
import org.carp.beans.ColumnsMetadata;
import org.carp.beans.PrimarysMetadata;
import org.carp.engine.ParametersProcessor;
import org.carp.engine.sql.Sql;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;
import org.carp.impl.AbstractCarpSession;
import org.carp.sql.OracleCarpSql;

/**
 * ���ݲ��������¼���
 * @author zhou
 * @since 0.2
 */
public abstract class Event{
	private static final Logger logger = Logger.getLogger(Event.class);
	private AbstractCarpSession session;
	private Object entity; //ʵ�����
	private String sql; //sql���
	private int index = 0;  //sql���Ĳ�������
	private CarpBean bean;  //ʵ�����Ӧ��Bean����
	private String optType;
	
	public Event(AbstractCarpSession session,Object entity,String _optType) throws CarpException{
		this.session = session;
		this.entity = entity;
		bean = BeansFactory.getBean(this.entity.getClass());
		this.optType = _optType;
	}
	
	public Event(AbstractCarpSession session,Class<?> cls,Object key,String _optType) throws CarpException{
		this.session = session;
		bean = BeansFactory.getBean(cls);
		this.optType = _optType;
	}
	
	/**
	 * ȡ��ʵ����������ֵ
	 * @return
	 */
	public java.io.Serializable getPrimaryValue(){
		return null;
	}
	/**
	 * ����Insert/update/delete/find sql���
	 * @throws CarpException
	 */
	protected void buildSql() throws Exception{
		Sql _sql = new Sql(this.session);
		if(this.entity != null)
			sql = _sql.buildeSql(this.entity.getClass(), optType);
		else
			sql = _sql.buildeSql(bean.getTableClass(), optType);
		_sql.showSql(sql);
	}
	
	
	/**
	 * ������Ҫ��Statement����
	 * @throws Exception
	 */
	protected void buildStatement()throws Exception{
		if(!sql.equals(this.session.getSql())){
			this.session.setPs(this.session.getConnection().prepareStatement(sql));
			this.session.setSql(sql);
		}
	}
	
	/**
	 * ִ��Update����
	 * @throws Exception
	 */
	private void executeStatement()throws Exception{
		this.session.getPs().executeUpdate();
	}
	
	/**
	 * ������¼�������
	 * @throws Exception
	 */
	public void execute()throws Exception{
		cascadeBeforeOperator(); //statement����ǰ�ļ�������
		buildSql(); //����sql���
		executeBefore();
		buildStatement(); //����statement����
		processStatmentParameters(new ParametersProcessor(this.session.getPs())); //����statement����
		executeStatement(); //ִ��statement
		executeAfter();
		cascadeAfterOperator(); //statement������ļ�������
	}
	
	/**
	 * ����Statement��Ҫ�Ĳ���
	 * @param psProcess
	 * @throws Exception
	 */
	protected abstract void processStatmentParameters(ParametersProcessor psProcess) throws Exception;
	
	/**
	 * statement����ǰ�ļ�������
	 * @throws Exception
	 */
	protected abstract void cascadeBeforeOperator() throws Exception;
	
	/**
	 * statement������ļ�������
	 * @throws Exception
	 */
	protected abstract void cascadeAfterOperator() throws Exception;
	/**
	 * ִ��ǰ�Ĵ���
	 * @throws Exception
	 */
	protected abstract void executeBefore() throws Exception;
	/**
	 * ִ�к�Ĵ���
	 * @throws Exception
	 */
	protected abstract void executeAfter() throws Exception;

	/**
	 * ����ʵ���column��ֵ
	 * @param psProcess
	 * @throws Exception
	 */
	protected void processFieldValues(ParametersProcessor psProcess) throws Exception{
		List<ColumnsMetadata> columns = bean.getColumns();
		boolean b = false;
		if(session.getJdbcContext().getContext().getCarpSqlClass().equals(OracleCarpSql.class))b=true;
		for(int i = 0, count = columns.size(); i < count; ++i){
			ColumnsMetadata column = columns.get(i);
			Class<?> ft = column.getFieldType();
			if(b && (ft.equals(Blob.class) || ft.equals(Clob.class)))
				continue;
			Object value = column.getValue(entity);
			int _index = this.getNextIndex();
			if(logger.isDebugEnabled()){
				logger.debug("����������"+ _index +" , ColumnName:"+column.getColName()+" , FieldName:"+column.getFieldName()+",FieldType:"+column.getFieldType().getName()+",FieldValue: "+value);
				if(value != null)
					logger.debug("ValueType: "+value.getClass().getName());
			}
			psProcess.setStatementParameters(value, ft, _index);
		}
	}
	
	/**
	 * ����ʵ���id��ֵ
	 * @param psProcess
	 * @throws Exception
	 */
	protected void processPrimaryValues(ParametersProcessor psProcess) throws Exception{
		List<PrimarysMetadata> pms = bean.getPrimarys();
		for(int i = 0, count = pms.size(); i < count; ++i){
			PrimarysMetadata pk = pms.get(i);
			Class<?> ft = pk.getFieldType();
			Object value = pk.getValue(entity);
			int _index = this.getNextIndex();
			if(logger.isDebugEnabled()){
				logger.debug("����������"+_index+" , ��������:"+pk.getColName()+" , FieldName:"+pk.getFieldName()+" , FieldType:"+pk.getFieldType().getName()+" ,FieldValue: "+value);
				if(value != null)
					logger.debug(value.getClass().getName());
			}
			psProcess.setStatementParameters(value, ft, _index);
		}
	}
	
	protected AbstractCarpSession getSession() {
		return session;
	}

	public Object getEntity() {
		return entity;
	}

	protected void setEntity(Object entity) {
		this.entity = entity;
	}
	
	protected String getSql() {
		return sql;
	}

	protected void setSql(String sql) {
		this.sql = sql;
	}

	protected CarpBean getBean() {
		return bean;
	}
	protected int getNextIndex(){
		return ++index;
	}
}
