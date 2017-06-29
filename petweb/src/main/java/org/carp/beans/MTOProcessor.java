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
package org.carp.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.carp.annotation.Column;
import org.carp.annotation.ManyToOne;
import org.carp.exception.CarpException;

public class MTOProcessor  implements AnnotationProcessor{
	private static final Logger logger = Logger.getLogger(MTOProcessor.class);
	public void parse(CarpBean bean, Class<?> cls) throws CarpException{
		Field[] fields = cls.getDeclaredFields();
		TableMetadata table = bean.getTableInfo();
		for(Field field:fields){
			Annotation[] annos = field.getAnnotations();
			for(Annotation anno:annos){
				if(anno instanceof ManyToOne){
					ManyToOne mto = (ManyToOne)anno;
					MTOMetadata mm = new MTOMetadata();
					table.addMTOMetadata(mm);
					mm.setFieldName(mto.foreignField());
					try {
						Field f = cls.getDeclaredField(mto.foreignField());
						mm.setFieldClass(f.getType());
						mm.setColName(f.getAnnotation(Column.class).name().toUpperCase());
					} catch (Exception e) {
						throw new CarpException("ManyToOneע�������foreignFieldֵ��"+mto.foreignField()+",��ע���ࣺ"+cls.getName()+" ��û�ж�Ӧ��field��");
					}
					mm.setField(field);
					mm.setParentField(field.getName());
					mm.setMasteClass(field.getType());
					mm.setLazy(mto.lazy());
					if(logger.isDebugEnabled()){
						StringBuilder builder = new StringBuilder();
						builder.append("Many To One ###  Many Field/Class: ");
						builder.append(mm.getFieldName());
						builder.append(" / "+mm.getFieldClass().getName());
						builder.append(" ; One Field/Class: "+field.getName());
						builder.append(" / "+field.getType().getName());
						logger.debug(builder.toString());
					}
				}
			}
		}
	}
}
