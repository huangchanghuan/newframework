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

import org.carp.DataSet;
import org.carp.engine.metadata.MetaData;
import org.carp.impl.AbstractCarpQuery;
import org.carp.impl.DataSetImpl;

/**
 * ִ�в�ѯ�࣬û��pojoʵ�壬��DataSet����װ�ز�ѯ���ļ�¼.
 * @author Administrator
 *
 */
public class DatasetQueryExecutor extends QueryExecutor{
	/**
	 * ���캯��
	 * @param query
	 * @throws Exception
	 */
	public DatasetQueryExecutor(AbstractCarpQuery query)throws Exception{
		super(query);
		this.setMetadata(new MetaData(this.getResultSet()));
	}
	
	/**
	 * ���ݲ�ѯsql������DataSet����װ�صļ�¼.
	 * @return
	 * @throws Exception
	 */
	public DataSet dataSet() throws Exception{
		DataSet dataSet = new DataSetImpl(this.getQuery(),this.getMetadata(),this.getResultSet());
		return dataSet;
	}
}
