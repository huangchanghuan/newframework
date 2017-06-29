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

import org.apache.log4j.Logger;
import org.carp.cfg.CarpSetting;
import org.carp.exception.CarpException;
/**
 * DataSource��ʽ��Connenction�ṩ��
 * @author zhou
 *
 */
public class DataSourceConnectionProvider extends AbstractConnectionProvider {
	private static final Logger logger = Logger.getLogger(DataSourceConnectionProvider.class);
	private CarpSetting carp;
	//private String databaseVersion;
	public DataSourceConnectionProvider(CarpSetting carp) throws CarpException{
		this.carp = carp;
		//this.setDataSource(new CarpDataSource(carp));
		loadDataSourceFromAppServer();
		databaseProducename();
		dialect();
		if(logger.isDebugEnabled())
			logger.debug("database dialect : "+this.getCarpSqlClass());
	}
	public CarpSetting getCarpSetting() {
		return this.carp;
	}
	
	/**
	 * �ӷ���������Դ�����м�������Դ
	 * @throws CarpException
	 */
	private void loadDataSourceFromAppServer()throws CarpException{
		try{
			javax.naming.InitialContext context = new javax.naming.InitialContext();
			this.setDataSource((javax.sql.DataSource)context.lookup(carp.getDataSource()));
		}catch(Exception ex){
			throw new CarpException(ex);
		}
	}
}
