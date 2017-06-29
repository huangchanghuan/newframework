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
package org.carp.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.carp.exception.CarpException;
import org.carp.intercept.Interceptor;
import org.carp.sql.CarpSql;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Carp������
 * @author zhou
 * @since 0.1
 */
public final class CarpSetting {
	private static final Logger logger = Logger.getLogger("���ã�");//Logger.getLogger(CarpSetting.class);
	private Map<String,String> elemMap = new HashMap<String,String>();
	/** ���ݿ������û�	 */
	private String userName;
	/** ���ݿ���������	 */
	private String password;
	/** ���ݿ�����������	 */
	private String driverClass;
	/** ���ݿ������ַ���URL	 */
	private String url;
	/** ���ݿ����ӵ�����Դ��	 */
	private String dataSource;
	/** ���ݿ��ض�sql������*/
	private Class<CarpSql> databaseDialect;//
	/** ���ӳ�	 */
	private String pool;
	/** ���ݿ�����Ĭ��ʹ��JDBC����	 */
	private String carpTransaction = "JDBC";
	/** ������뼶��	 */
	private int transIsoLationLevel = -1;
	/** �Ƿ���ʾ��ִ�е�Sql�ڿ���̨	 */
	private boolean showSql;
	/** ���񹤳�	 */
	private String transFactory;
	/** Carpʹ�õ�Cache	 */
	private String cache;
	/** �������С��Ĭ��ֵ��100	 */
	private int batchSize = 100;
	/** ��ѯ����ʱ��Fetch��С��Ĭ�ϲ�����	 */
	private int fetchSize = 0;
	/** �Ƿ���������������Ĭ�ϣ�false	 */
	private boolean enableScrollableResultSet = false;
	/** ���ݿ⵱ǰ��catalog	 */
	private String catalog;
	/** ���ݿ�ĵ�ǰschema	 */
	private String schema;
	/** ���ݿ����������	 */
	private int maxActive;
	/** ���ݿ��������ʵ����	 */
	private int maxIdle;
	/** ���ݿ����ӵ����ȴ�ʱ��	 */
	private long maxWait;
	/** ���ݿ����ӵ�����ʵ����	 */
	private int minIdle;
	/** ���ݿ����ӵ�����������	 */
	private Interceptor interceptor;
	
	@SuppressWarnings("unchecked")
	public CarpSetting(Document doc) throws CarpException{
		parserProperty(doc.getRootElement());
		String value = getNodeValue(Constant.USER_NAME);
		if(value != null)
			this.userName = value;
		value = getNodeValue(Constant.USER_PASSWORD);
		if(value != null)
			this.password = value;
		value = getNodeValue(Constant.DRIVER_CLASS);
		if(value != null)
			this.driverClass = value;
		value = getNodeValue(Constant.CONNECTIION_URL);
		if(value != null)
			this.url = value;
		value = getNodeValue(Constant.DATASOURCE);
		if(value != null)
			this.dataSource = value;
		value = getNodeValue(Constant.DIALECT);
		if(value != null){
			try {
				this.databaseDialect = (Class<CarpSql>) Class.forName(value);
			} catch (ClassNotFoundException e) {
				throw new CarpException("����֧�ֵ����ݿ�sql������:"+value,e);
			}
		}
		value = getNodeValue(Constant.CONNECT_POOL);
		if(value != null)
			this.pool = value;
		value = getNodeValue(Constant.CARP_TRANSACTION);
		if(value != null)
			this.carpTransaction = value;
		value = getNodeValue(Constant.CARP_TRANS_ISOLATIONLEVEL);
		if(value != null)
			this.transIsoLationLevel = Integer.parseInt(value);
		value = getNodeValue(Constant.SHOW_SQL);
		if(value != null)
			this.showSql = value.equalsIgnoreCase("true")?true:false;
		value = getNodeValue(Constant.CARP_JDBC_BATCH_SIZE);
		if(value != null)
			this.batchSize = Integer.parseInt(value);
		value = getNodeValue(Constant.CARP_JDBC_FETCH_SIZE);
		if(value != null)
			this.fetchSize = Integer.parseInt(value);
		value = getNodeValue(Constant.ENABLED_SCROLL_RESULTSET);
		if(value != null)
			this.enableScrollableResultSet = Boolean.parseBoolean(value);
		value = getNodeValue(Constant.CARP_CATALOG);
		if(value != null)
			this.catalog = value;
		value = getNodeValue(Constant.CARP_SCHEMA);
		if(value != null)
			this.schema = value;
		value = getNodeValue(Constant.CONNECT_MAX_ACTIVE);
		if(value != null)
			this.maxActive = Integer.parseInt(value);
		value = getNodeValue(Constant.CONNECT_MAX_IDLE);
		if(value != null)
			this.maxIdle = Integer.parseInt(value);
		value = getNodeValue(Constant.CONNECT_MAX_WAIT);
		if(value != null)
			this.maxWait = Integer.parseInt(value);
		value = getNodeValue(Constant.CONNECT_MIN_IDLE);
		if(value != null)
			this.minIdle = Integer.parseInt(value);
	}
	
	@SuppressWarnings("unchecked")
	public CarpSetting(Properties prop) throws CarpException{
		String value = prop.getProperty(Constant.USER_NAME);
		if(value != null)
			this.userName = value;
		value = prop.getProperty(Constant.USER_PASSWORD);
		if(value != null)
			this.password = value;
		value = prop.getProperty(Constant.DRIVER_CLASS);
		if(value != null)
			this.driverClass = value;
		value = prop.getProperty(Constant.CONNECTIION_URL);
		if(value != null)
			this.url = value;
		value = prop.getProperty(Constant.DATASOURCE);
		if(value != null)
			this.dataSource = value;
		value = prop.getProperty(Constant.DIALECT);
		if(value != null){
			try {
				this.databaseDialect = (Class<CarpSql>) Class.forName(value);
			} catch (ClassNotFoundException e) {
				throw new CarpException("����֧�ֵ����ݿ�sql������:"+value,e);
			}
		}
		value = prop.getProperty(Constant.CONNECT_POOL);
		if(value != null)
			this.pool = value;
		value = prop.getProperty(Constant.CARP_TRANSACTION);
		if(value != null)
			this.carpTransaction = value;
		value = prop.getProperty(Constant.CARP_TRANS_ISOLATIONLEVEL);
		if(value != null)
			this.transIsoLationLevel = Integer.parseInt(value);
		value = prop.getProperty(Constant.SHOW_SQL);
		if(value != null)
			this.showSql = value.equalsIgnoreCase("true")?true:false;
		value = prop.getProperty(Constant.CARP_JDBC_BATCH_SIZE);
		if(value != null)
			this.batchSize = Integer.parseInt(value);
		value = prop.getProperty(Constant.CARP_JDBC_FETCH_SIZE);
		if(value != null)
			this.fetchSize = Integer.parseInt(value);
		value = prop.getProperty(Constant.ENABLED_SCROLL_RESULTSET);
		if(value != null)
			this.enableScrollableResultSet = Boolean.parseBoolean(value);
		value = prop.getProperty(Constant.CARP_CATALOG);
		if(value != null)
			this.catalog = value;
		value = prop.getProperty(Constant.CARP_SCHEMA);
		if(value != null)
			this.schema = value;
		value = prop.getProperty(Constant.CONNECT_MAX_ACTIVE);
		if(value != null)
			this.maxActive = Integer.parseInt(value);
		value = prop.getProperty(Constant.CONNECT_MAX_IDLE);
		if(value != null)
			this.maxIdle = Integer.parseInt(value);
		value = prop.getProperty(Constant.CONNECT_MAX_WAIT);
		if(value != null)
			this.maxWait = Integer.parseInt(value);
		value = prop.getProperty(Constant.CONNECT_MIN_IDLE);
		if(value != null)
			this.minIdle = Integer.parseInt(value);
	}
	

	
	/**
	 * parser carp configuration file
	 * @param root carp configuration xml root element
	 */
	private void parserProperty(Element root){
		List<?> elems = root.elements();
		for(int i= 0, count = elems.size(); i < count; ++i){
			Element elem = (Element)elems.get(i);
			String key = elem.attributeValue("name").toLowerCase();
			String value = elem.getText().trim();
			if(value == null || value.equals(""))
				continue;
			elemMap.put(key, value);
			if(logger.isInfoEnabled()){
				logger.info(key+" : "+value);
			}
		}
	}
	
	private String getNodeValue(String name){
		return elemMap.get(name.toLowerCase());
	}
	
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public String getUrl() {
		return url;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getPool() {
		return pool;
	}

	public String getCarpTransaction() {
		return carpTransaction;
	}

	public int getTransIsoLationLevel() {
		return transIsoLationLevel;
	}

	public boolean isShowSql() {
		return showSql;
	}

	public String getTransFactory() {
		return transFactory;
	}

	public String getCache() {
		return cache;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getSchema() {
		return schema;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public Interceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	public boolean isEnableScrollableResultSet() {
		return enableScrollableResultSet;
	}

	public Class<CarpSql> getDatabaseDialect() {
		return databaseDialect;
	}	
}
