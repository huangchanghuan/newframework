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
 * Session创建器类，用于创建数据库会话Session对象.
 * @author zhou
 * @since 0.1
 */
public class CarpSessionBuilder {
	private ConnectionProvider provider;
	public CarpSessionBuilder(CarpSetting carp) throws CarpException{
		provider = new JDBCConnectManage().getDataSourceConfig(carp);
	}
	
	/**
	 * 取得数据库连接会话Session对象
	 * @return
	 * @throws CarpException
	 */
	public CarpSession getSession() throws CarpException{
		return new CarpSessionImpl(provider);
	}
	/**
	 * 取得数据库连接会话Session对象，带有拦截器对象。
	 * @param interceptor 拦截器对象
	 * @return
	 * @throws CarpException
	 */
	public CarpSession getSession(Interceptor interceptor) throws CarpException{
		return new CarpSessionImpl(provider,interceptor);
	}
	
	/**
	 * 取得Carp的配置信息
	 * @return
	 */
	public CarpSetting getCarpConfig(){
		return provider.getCarpSetting();
	}
	
	/**
	 * 取得数据库的sql特征
	 * @param cls
	 * @return
	 */
	public CarpSql getCarpSql(Class<?> cls){
		return provider.getCarpSql(cls);
	}
	
	/**
	 * 取得cls对应的bean信息对象
	 * @param cls
	 * @return
	 * @throws CarpException
	 */
	public CarpBean getBean(Class<?> cls) throws CarpException{
		return BeansFactory.getBean(cls);
	}
}
