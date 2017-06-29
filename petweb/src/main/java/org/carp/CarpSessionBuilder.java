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

import org.carp.beans.CarpBean;
import org.carp.cfg.CarpSetting;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;
import org.carp.impl.CarpSessionImpl;
import org.carp.intercept.Interceptor;
import org.carp.jdbc.ConnectionProvider;
import org.carp.jdbc.JDBCConnectManage;
import org.carp.sql.CarpSql;

/**
 * Session�������࣬���ڴ������ݿ�ỰSession����.
 * @author zhou
 * @since 0.1
 */
public class CarpSessionBuilder {
	private ConnectionProvider provider;
	public CarpSessionBuilder(CarpSetting carp) throws CarpException{
		provider = new JDBCConnectManage().getDataSourceConfig(carp);
	}
	
	/**
	 * ȡ�����ݿ����ӻỰSession����
	 * @return
	 * @throws CarpException
	 */
	public CarpSession getSession() throws CarpException{
		return new CarpSessionImpl(provider);
	}
	/**
	 * ȡ�����ݿ����ӻỰSession���󣬴�������������
	 * @param interceptor ����������
	 * @return
	 * @throws CarpException
	 */
	public CarpSession getSession(Interceptor interceptor) throws CarpException{
		return new CarpSessionImpl(provider,interceptor);
	}
	
	/**
	 * ȡ��Carp��������Ϣ
	 * @return
	 */
	public CarpSetting getCarpConfig(){
		return provider.getCarpSetting();
	}
	
	/**
	 * ȡ�����ݿ��sql����
	 * @param cls
	 * @return
	 */
	public CarpSql getCarpSql(Class<?> cls){
		return provider.getCarpSql(cls);
	}
	
	/**
	 * ȡ��cls��Ӧ��bean��Ϣ����
	 * @param cls
	 * @return
	 * @throws CarpException
	 */
	public CarpBean getBean(Class<?> cls) throws CarpException{
		return BeansFactory.getBean(cls);
	}
}
