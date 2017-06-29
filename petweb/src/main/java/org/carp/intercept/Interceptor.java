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
package org.carp.intercept;

import org.carp.impl.AbstractCarpSession;

/**
 * �������ӿ���
 *
 * @author zhou
 * @since 0.1
 */
public interface Interceptor {
	
	/**
	 * ����ǰ�¼�
	 * @param entity �����л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onBeforeSave(Object entity, AbstractCarpSession session)throws Exception;
	
	/**
	 * ������¼�
	 * @param entity ���л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onAfterSave(Object entity, AbstractCarpSession session)throws Exception;
	
	/**
	 * װ��ǰ�¼�
	 * @param entity �����л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onBeforeLoad(Object entity, AbstractCarpSession session)throws Exception;
	
	/**
	 * װ�غ��¼�
	 * @param entity ���л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onAfterLoad(Object entity, AbstractCarpSession session)throws Exception;
	
	/**
	 * ����ǰ�¼�
	 * @param entity �����л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onBeforeUpdate(Object entity, AbstractCarpSession session)throws Exception;
	
	/**
	 * ���º��¼�
	 * @param entity ���л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onAfterUpdate(Object entity, AbstractCarpSession session)throws Exception;
	
	/**
	 * ɾ��ǰ�¼�
	 * @param entity �����л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onBeforeDelete(Object entity, AbstractCarpSession session)throws Exception;
	
	/**
	 * ɾ�����¼�
	 * @param entity ���л�����
	 * @param session ���ݿ����Ӷ���
	 * @throws Exception
	 */
	public void onAfterDelete(Object entity, AbstractCarpSession session)throws Exception;
}
