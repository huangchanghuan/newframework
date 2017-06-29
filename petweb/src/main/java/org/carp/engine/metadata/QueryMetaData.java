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
package org.carp.engine.metadata;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.carp.assemble.Assemble;
import org.carp.type.TypesMapping;
import org.carp.util.EntityUtil;

/**
 * Ԫ������
 * ����ִ�в�ѯʱ������ResultSetԪ���ݣ�������Ӧ��cls��Ԫ���ݣ�������صĹ�ϵ
 * @author zhou
 * @since 0.1
 */
public class QueryMetaData extends MetaData{
	private List<String> nameList = new ArrayList<String>();
	private List<Field> fieldList = new ArrayList<Field>();
	private Map<String,Field> fields = new HashMap<String,Field>();
	
	public QueryMetaData(Class<?> cls, ResultSet rs)throws Exception{
		super(rs);
		processMetadata(cls);
	}
	
	/**
	 * ����cls��Ԫ���ݣ���֮��col��Ϣ���й���
	 * @param cls
	 * @throws Exception
	 */
	private void processMetadata(Class<?> cls)throws Exception{
		List<String> cols = this.getColumns();
		this.getAssembles().clear();
		this.getColumnJavaType().clear();
		this.getColumnsJavaType().clear();
		for(String col : cols){
			if("ROW_NUM".equals(col))
				continue;
			String name = EntityUtil.getFieldName(col);//this.getFieldName(col.toLowerCase());
			nameList.add(name);
			Field f = cls.getDeclaredField(name);
			fieldList.add(f);
			fields.put(col, f);
			this.getColumnJavaType().add(f.getType());
			this.getColumnsJavaType().put(col, f.getType());
			this.getAssembles().put(col,(Assemble)TypesMapping.getAssembleClass(f.getType()).newInstance());
			if(logger.isDebugEnabled())
				logger.debug("Field : "+name +", FieldType : "+ f.getType());
		}
	}

	
	public Field getField(int index){
		return this.fieldList.get(index);
	}
	
	public Field getField(String colname){
		return this.fields.get(colname);
	}
}
