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

import org.carp.annotation.CarpAnnotation.Cascade;

public class OTOMetadata extends Metadata implements Cloneable{
	private String fieldName;
	private Class<?> fieldType;
	private Cascade cascade;
	
	public Cascade getCascade() {
		return cascade;
	}
	public void setCascade(Cascade cascade) {
		this.cascade = cascade;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Class<?> getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
	@Override
	protected OTOMetadata clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (OTOMetadata)super.clone();
	}
	
}
