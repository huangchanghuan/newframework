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
import org.carp.annotation.Id;
import org.carp.annotation.CarpAnnotation.Generate;
import org.carp.exception.CarpException;
import org.carp.id.Generator;

public class PrimarysProcessor  implements AnnotationProcessor{
	private static final Logger logger = Logger.getLogger(PrimarysProcessor.class);
	public void parse(CarpBean bean, Class<?> cls) throws CarpException {
		TableMetadata table = bean.getTableInfo();
		Field[] fields = cls.getDeclaredFields();
		for(Field field:fields){
			Annotation[] annos = field.getAnnotations();
			for(Annotation anno:annos){
				if(anno instanceof Id){
					Id pka = (Id)anno;
					PrimarysMetadata pkm = new PrimarysMetadata();
					pkm.setColName(pka.name().toUpperCase());
					pkm.setFieldName(field.getName());
					pkm.setField(field);
					pkm.setFieldType(field.getType());
					pkm.setBuilder(pka.builder());
					pkm.setBuild(pka.build());
					pkm.setRemark(pka.remark());
					pkm.setSequence(pka.sequence());
					table.addPrimarysMetadata(pkm);
					if(logger.isDebugEnabled())
						logger.debug("Table Primarys, column:"+pkm.getColName()+
								", field:"+pkm.getFieldName()+
								", Generator:"+pka.build());
					if(pka.build() == Generate.sequences){
						if(pka.sequence().trim().equals(""))
							throw new CarpException("������sequence��ʽ���ɣ�����û������ Sequence�������ã�");
					}else if(pka.build() == Generate.custom){
						if(pka.builder().getName().equals(Generator.class.getName()))
							throw new CarpException("������ custom��ʽ���ɣ���Ҫ����������ʵ���࣡����û�����ã������ã�");
						else{
							try {
								
								pka.builder().newInstance();
							} catch (Exception e) {
								throw new CarpException("������ custom��ʽ���ɣ���Ҫ����������ʵ���࣡���Ǹ�������ʵ���಻��ʵ���������������ã�");
							}
						}
					}
				}
			}
		}
	}
}
