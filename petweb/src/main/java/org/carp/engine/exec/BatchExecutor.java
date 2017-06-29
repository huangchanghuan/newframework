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
package org.carp.engine.exec;

import java.sql.SQLException;

import org.carp.impl.AbstractCarpQuery;

/**
 * ����ִ������
 * @author zhou
 * @since 0.2
 */
public class BatchExecutor extends Executor{
	public BatchExecutor(AbstractCarpQuery query) throws Exception{
		super(query);
	}

	@Override
	protected void executeStatement() throws Exception {
	}

	/**
	 * ������Ӱ�������
	 * @return
	 * @throws SQLException 
	 */
	public void addBatch() throws SQLException {
		this.getQuery().getPreparedStatement().addBatch();
		this.getQuery().clearParameters();
	}
}
