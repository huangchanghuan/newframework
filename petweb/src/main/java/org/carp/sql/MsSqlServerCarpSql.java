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

import java.sql.PreparedStatement;

import org.carp.exception.CarpException;
import org.carp.sql.CarpSql.PageSupport;

public class MsSqlServerCarpSql extends AbstractSql {
	/**
	 * ȡ��ִ�з�ҳ��ѯʱ��select��ѯ�ķ�ҳsql���
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getPageSql(String sql) throws Exception{
		StringBuffer carpSQL = new StringBuffer("select top ? ");
		carpSQL.append(sql.substring(6));
		return carpSQL.toString();
	}

	public int position() {
		return 1;
	}

	public String getSequenceSql(String seq) throws CarpException {
		throw new CarpException("Sql Server �ݲ�֧�ֵ����ݿ�����!");
	}

	public void setQueryParameters(PreparedStatement ps, int firstIndex,
			int maxIndex, int paramsCount) throws Exception {
		ps.setInt(1, firstIndex+maxIndex);
	}

	/**
	 * ����֧�ַ�ҳ
	 */
	@Override
	public PageSupport pageMode(){
		return PageSupport.PARTIAL;
	}

	/**
	 * ֧�ֹ��������
	 */
	@Override
	public boolean enableScrollableResultSet() {
		return true;
	}
}
