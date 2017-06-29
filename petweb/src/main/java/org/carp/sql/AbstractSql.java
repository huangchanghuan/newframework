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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.carp.annotation.CarpAnnotation.Generate;
import org.carp.beans.CarpBean;
import org.carp.beans.ColumnsMetadata;
import org.carp.beans.MappingMetadata;
import org.carp.beans.PrimarysMetadata;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;

public abstract class AbstractSql implements CarpSql{
	private final static Map<String,String> insertMap = new ConcurrentHashMap<String, String>();
	private final static Map<String,String> updateMap = new ConcurrentHashMap<String, String>();
	private final static Map<String,String> deleteMap = new ConcurrentHashMap<String, String>();
	private final static Map<String,String> loadMap = new ConcurrentHashMap<String, String>();
	private final static Map<String,String> selectMap = new ConcurrentHashMap<String, String>();
	private final static Map<String,String> selectPageMap = new ConcurrentHashMap<String, String>();
	
	private CarpBean bean;
	private String _schema;

	
	public String getSchema() {
		return this._schema;
	}

	public void setSchema(String schema) {
		this._schema = schema;
	}

	/**
	 * ȡ��pojo���Bean��Ϣ
	 */
	public CarpBean getBeanInfo(){
		return this.bean;
	}
	
	public void setClass(Class<?> cls) throws CarpException{
		if(cls != null)
//			throw new CarpException("����ֵ����Ϊ��ֵ�� cls = "+cls);
		this.bean = BeansFactory.getBean(cls);
	}
	
	/**
	 * Ĭ��֧�ַ�ҳ���ܣ����ǲ�֧�ֻ򲿷�֧�ַ�ҳ���ܵ����ݿ⣬��Ҫ�ڸ��Ե�CarpSql�ӿڵ�ʵ���������ظ÷�����������Ӧ��ֵ
	 */
	public PageSupport pageMode() {
		return PageSupport.COMPLETE;
	}
	/**
	 * Ĭ�ϲ�֧�ֹ����������������ݿⱾ��֧�ֻ򲿷�֧�ַ�ҳ������Ҫ���ظ÷������Ա���ʹ�ù���ģʽ
	 */
	public boolean enableScrollableResultSet() {
		return false;
	}

	/**
	 * ���ز�ѯ��ҳsql
	 */
	public String getPageSql() throws Exception {
		String queryPageSql = selectPageMap.get(bean.getTable());
		if(queryPageSql == null)
			queryPageSql = this.getPageSql(this.getQuerySql()); 
		return queryPageSql; 
	}
	
	/**
	 * ����������ѯ��¼��sql���
	 * @throws CarpException 
	 */
	public String getLoadSql() throws CarpException{
		String loadSql = loadMap.get(bean.getTable());
		if(loadSql == null){
			List<PrimarysMetadata> pks = bean.getPrimarys();
			if(pks==null)
				throw new CarpException("û��primary key������find by key��");
			StringBuilder sql = new StringBuilder("select * from  ");
			if(bean.getSchema() !=null && !bean.getSchema().trim().equals(""))
				sql.append(bean.getSchema()).append(".");
			else if(this._schema !=null && !this._schema.trim().equals(""))
				sql.append(this._schema).append(".");
			sql.append(bean.getTable());
			sql.append(" where ");
			for(int i = 0, count = pks.size(); i < count; ++i){
				if(i!=0)
					sql.append(" and ");
				sql.append(pks.get(i).getColName());
				sql.append("=?");
			}
			loadSql = sql.toString();
			loadMap.put(bean.getTable(), loadSql);
		}
		return loadSql;
	}
	
	/**
	 * ȡ��ִ�з�ҳ��ѯʱ��select��ѯ�ķ�ҳsql���
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public abstract String getPageSql(String sql) throws Exception;
	
	/**
	 * 
	 */
	public String getQuerySql(){
		String querySql = selectMap.get(bean.getTable());
		if(querySql == null){
			StringBuilder sql = new StringBuilder("select ");
			List<PrimarysMetadata> pks = bean.getPrimarys();
			if(pks != null)
				for(int i = 0, count = pks.size(); i < count; ++i){
					PrimarysMetadata pk = pks.get(i);
					if(i != 0){
						sql.append(",");
					}
					sql.append(bean.getTable().toLowerCase()+"_.");
					sql.append(pk.getColName());
				}
			List<ColumnsMetadata> cms = bean.getColumns();
			if(cms != null)
				for(int i=0,count = cms.size(); i<count; ++i){
					if(i!=0 || pks != null )
						sql.append(",");
					sql.append(bean.getTable().toLowerCase()+"_.");
					sql.append(cms.get(i).getColName());
				}
			List<MappingMetadata> maps = bean.getMaps();
			if(maps != null)
				for(MappingMetadata mm : maps){
					sql.append(",");
					sql.append(mm.getMapTable().toLowerCase()+"_.");
					sql.append(mm.getMapColumn()).append(" ").append(mm.getMasterAlias());
				}
			sql.append(" from ");
			if(bean.getSchema() !=null && !bean.getSchema().trim().equals(""))
				sql.append(bean.getSchema()).append(".");
			else if(this._schema !=null && !this._schema.trim().equals(""))
				sql.append(this._schema).append(".");
			sql.append(bean.getTable());
			sql.append(" "+bean.getTable().toLowerCase()+"_ ");
			if(maps != null)
				for(MappingMetadata mm : maps){
					sql.append(", ");
					if(mm.getMapSchema() !=null && !mm.getMapSchema().equals(""))
						sql.append(mm.getMapSchema()).append(".");
					else if(this._schema !=null && !this._schema.trim().equals(""))
						sql.append(this._schema).append(".");
					sql.append(mm.getMapTable());
					sql.append(" "+mm.getMapTable().toLowerCase() + "_ ");
				}
			if(maps!=null && !maps.isEmpty()){
				sql.append(" where ");
				for(int i=0,count = maps.size(); i<count; ++i){
					MappingMetadata mm = maps.get(i);
					if(i!=0)
						sql.append(" and ");
					sql.append("").append(bean.getTable().toLowerCase()).append("_.");
					sql.append(mm.getFkColumn()).append(" = ");
					sql.append("").append(mm.getMapTable().toLowerCase()).append("_.");
					sql.append(mm.getPkColumm());
				}
			}
			querySql = sql.toString();
			selectMap.put(bean.getTable(), querySql);
		}
		return querySql;
	}
	
	/**
	 * ȡ��ִ��insert����ʱ��insert sql���
	 * @param table
	 * @return
	 */
	public String getInsertSql(){
		String insertSql = insertMap.get(bean.getTable());
		if(insertSql == null){
			List<ColumnsMetadata> cms = bean.getColumns();
			List<PrimarysMetadata> pks = bean.getPrimarys();
			StringBuilder sql = new StringBuilder("insert into ");
			if(bean.getSchema() !=null && !bean.getSchema().trim().equals(""))
				sql.append(bean.getSchema()).append(".");
			else if(this._schema !=null && !this._schema.trim().equals(""))
				sql.append(this._schema).append(".");
			sql.append(bean.getTable()).append("(");
			for(int i=0,count = cms.size(); i<count; ++i){
				if(i!=0)
					sql.append(",");
				sql.append(cms.get(i).getColName());
			}
			for(PrimarysMetadata pk:pks){
				if(pk.getBuild() != Generate.auto){
					sql.append(",");
					sql.append(pk.getColName());
				}
			}
			sql.append(") values(");
			for(int i=0,count = cms.size(); i<count; ++i){
				if(i!=0)
					sql.append(",");
				sql.append("?");
			}
			
			for(int i=0, count = pks.size(); i < count; ++i){
				PrimarysMetadata pk = pks.get(i);
				if(pk.getBuild() != Generate.auto){
					sql.append(",");
					sql.append("?");
				}
			}
			sql.append(")");
			insertSql = sql.toString();
			insertMap.put(bean.getTable(), insertSql);
		}
		return insertSql;
	}
	
	/**
	 * ȡ��ִ��updateʱ��update sql���
	 * @param cls
	 * @param table
	 * @return
	 */
	public String getUpdateSql(){
		String updateSql = updateMap.get(bean.getTable());
		if(updateSql == null){
			List<ColumnsMetadata> cms = bean.getColumns();
			List<PrimarysMetadata> pks = bean.getPrimarys();
			StringBuilder sql = new StringBuilder("update ");
			if(bean.getSchema() !=null && !bean.getSchema().trim().equals(""))
				sql.append(bean.getSchema()).append(".");
			else if(this._schema !=null && !this._schema.trim().equals(""))
				sql.append(this._schema).append(".");
			sql.append(bean.getTable()).append(" set ");
			for(int i = 0, count = cms.size(); i < count; ++i){
				if(i!=0)
					sql.append(",");
				sql.append(cms.get(i).getColName());
				sql.append("=?");
			}
			sql.append(" where ");
			for(int i = 0, count = pks.size(); i < count; ++i){
				if(i!=0)
					sql.append(" and ");
				sql.append(pks.get(i).getColName());
				sql.append("=?");
			}
			updateSql = sql.toString();
			updateMap.put(bean.getTable(), updateSql);
		}
		return updateSql;
	}
	
	/**
	 * ȡ��ִ��update��delete���,��������ɾ����delete���
	 */
	public String getDeleteSql(){
		String deleteSql = deleteMap.get(bean.getTable());
		if(deleteSql == null){
			List<PrimarysMetadata> pks = bean.getPrimarys();
			StringBuilder sql = new StringBuilder("delete from  ");
			if(bean.getSchema() !=null && !bean.getSchema().trim().equals(""))
				sql.append(bean.getSchema()).append(".");
			else if(this._schema !=null && !this._schema.trim().equals(""))
				sql.append(this._schema).append(".");
			sql.append(bean.getTable()).append(" where ");
			for(int i = 0, count = pks.size(); i < count; ++i){
				if(i!=0)
					sql.append(" and ");
				sql.append(pks.get(i).getColName());
				sql.append("=?");
			}
			deleteSql =sql.toString();
			deleteMap.put(bean.getTable(), deleteSql);
		}
		return deleteSql;
	}
	/**
	 * ����schema���ֵ�sql�����
	 * @param sql
	 * @return
	 */
	protected String appendSchema(StringBuilder sql){
		if(bean.getSchema() !=null && !bean.getSchema().trim().equals(""))
			sql.append(bean.getSchema()).append(".");
		else if(this._schema !=null && !this._schema.trim().equals(""))
			sql.append(this._schema).append(".");
		return "";
	}
	
	protected static Map<String, String> getInsertmap() {
		return insertMap;
	}
	
	protected static Map<String, String> getUpdatemap() {
		return updateMap;
	}
	
	protected static Map<String, String> getDeletemap() {
		return deleteMap;
	}
	
	protected static Map<String, String> getLoadmap() {
		return loadMap;
	}
	
	protected static Map<String, String> getSelectmap() {
		return selectMap;
	}
	
	protected static Map<String, String> getSelectpagemap() {
		return selectPageMap;
	}
}
