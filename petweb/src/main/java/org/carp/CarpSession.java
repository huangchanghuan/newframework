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

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.carp.exception.CarpException;
import org.carp.transaction.Transaction;
/**
 * 数据库连接会话类
 * @author zhou
 * @version 0.1
 */
public interface CarpSession {
	/**
	 * 持久化对象到数据库
	 * @param obj 需要持久化的对象
	 * @return 持久化对象的主键值
	 * @throws CarpException
	 */
	Serializable save(Object obj)throws CarpException;
	/**
	 * 持久化数据到数据库
	 * @param table 需要insert数据的数据表
	 * @param map 列与列值的Map对象（列值对）,列为table的列名，如果不存在，则抛出异常
	 * @throws CarpException
	 */
	void save(String table, Map<String, Object> map)throws CarpException;
	/**
	 * 删除对象
	 * @param obj 对象
	 * @throws CarpException
	 */
	void delete(Object obj)throws CarpException ;
	/**
	 * 删除对象
	 * @param cls 数据表对应的pojo类
	 * @param id 主键值
	 * @throws CarpException
	 */
	void delete(Class<?> cls, Serializable id)throws CarpException;
	/**
	 * 删除对象
	 * @param cls 数据表对于的pojo类
	 * @param ids 联合主键值
	 * @throws CarpException
	 */
	void delete(Class<?> cls, Map<String, Object> ids)throws CarpException;
	/**
	 * 更新一个对象
	 * @param obj 对象
	 * @throws CarpException
	 */
	void update(Object obj)throws CarpException;
	/**
	 * 根据标识查找一个对象
	 * @param cls 对象类
	 * @param id 主键值
	 * @return cls对象
	 * @throws CarpException
	 */
	Object get(Class<?> cls, Serializable id)throws CarpException;
	
	/**
	 * 根据key查找一个cls类型的对象。
	 * @param cls 对象类
	 * @param key 主键值，map
	 * @return cls对象
	 * @throws CarpException
	 */
	Object get(Class<?> cls, Map<String, Object> key)throws CarpException;
	/**
	 * 创建数据集查询对象，
	 * @param sql 查询sql
	 * @return 数据集查询对象
	 * @throws CarpException
	 */
	CarpDataSetQuery creatDataSetQuery(String sql)throws CarpException;
	/**
	 * 创建可执行查询对象
	 * @param sql 可执行sql(delete，update，insert)
	 * @return 可执行查询对象
	 * @throws CarpException
	 */
	CarpQuery creatUpdateQuery(String sql)throws CarpException;
	/**
	 * 创建查询对象
	 * @param cls 查询类
	 * @return 查询对象
	 * @throws CarpException
	 */
	CarpQuery creatQuery(Class<?> cls)throws CarpException;
	/**
	 * 创建查询对象
	 * @param cls 查询类
	 * @param sql 查询sql
	 * @return 查询对象
	 * @throws CarpException
	 */
	CarpQuery creatQuery(Class<?> cls, String sql)throws CarpException;
	//void flush() throws CarpException;
	/**
	 * 启动事务
	 */
	Transaction beginTransaction() throws CarpException;
	/**
	 * 判断数据库连接是否打开
	 * @return
	 */
	boolean isOpen();
	/**
	 * 关闭会话
	 * @throws CarpException
	 */
	void close()throws CarpException;
	/**
	 * 取得被更新的对象列表（原始对象列表）
	 * @return
	 */
	List<Object> getUpdateObjects();
	/**
	 * 清理被更新的对象列表
	 */
	void clearUpdateObjects();
	/**
	 * 从会话中取得数据库连接
	 * @return
	 * @throws CarpException
	 */
	Connection getConnection() throws CarpException;
}
