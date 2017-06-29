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

import java.lang.reflect.Field;



public class MappingMetadata extends Metadata implements Cloneable{
	/**
	 * ע�����field����
	 */
	private String fieldName;
	/**
	 * ע�����field����
	 */
	private Class<?> fieldType;
	/**
	 * ע��������Ӧ��ӳ�����schema
	 */
	private String mapSchema;
	/**
	 * ע��������Ӧ��ӳ�����Table����
	 */
	private String mapTable;
	/**
	 * ע��������Ӧ��ӳ����mapClass��ӳ���ֶ�����
	 */
	private String mapColumn;
	/**
	 * ע��������Ӧ��ӳ����mapClass��ӳ��field����
	 */
	private String masterField;
	/**
	 * ע��������Ӧ��ӳ����mapClass��ӳ���ֶεı�������ע��field��������ӳ��mapClass��field���Ʋ�һ��ʱʹ��
	 */
	private String masterAlias;
	/**
	 * ��ע�����������field���ƣ��������ӳ���������field�໥��Ӧ
	 */
	private String fkName;
	/**
	 * ��ע�����������field���ͣ��������ӳ���������field�໥��Ӧ
	 */
	private Class<?> fkFieldType;
	/**
	 * ��ע�����������field���������ӳ���������field�໥��Ӧ
	 */
	private Field fkField;
	/**
	 * ��ע�����������field��ӳ��column���ƣ��������ӳ���������field��ӳ��primary key column�໥��Ӧ
	 */
	private String fkColumn;
	/**
	 * ��ע��������Ӧ��ӳ���������field��ӳ��primary key column����
	 */
	private String pkColumm;
	/**
	 * �Ƿ�ѱ�ע��������Ӧ��ӳ����ĵ�field��ӳ��column������ӵ�select�б��У�������ѯ
	 */
	private boolean isRelation;
	
	public boolean isRelation() {
		return isRelation;
	}

	public void setRelation(boolean isRelation) {
		this.isRelation = isRelation;
	}

	public String getFkColumn() {
		return fkColumn;
	}

	public void setFkColumn(String fkColumn) {
		this.fkColumn = fkColumn;
	}

	@Override
	protected MappingMetadata clone() throws CloneNotSupportedException {
		return (MappingMetadata)super.clone();
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

	public String getMapTable() {
		return mapTable;
	}

	public void setMapTable(String mapTable) {
		this.mapTable = mapTable;
	}

	public String getMapColumn() {
		return mapColumn;
	}

	public void setMapColumn(String mapColumn) {
		this.mapColumn = mapColumn;
	}

	public String getFkName() {
		return fkName;
	}

	public void setFkName(String fkName) {
		this.fkName = fkName;
	}

	public Class<?> getFkFieldType() {
		return fkFieldType;
	}

	public void setFkFieldType(Class<?> fkFieldType) {
		this.fkFieldType = fkFieldType;
	}

	public Field getFkField() {
		return fkField;
	}

	public void setFkField(Field fkField) {
		this.fkField = fkField;
	}

	public String getMapSchema() {
		return mapSchema;
	}

	public void setMapSchema(String mapSchema) {
		this.mapSchema = mapSchema;
	}

	public String getPkColumm() {
		return pkColumm;
	}

	public void setPkColumm(String pkColumm) {
		this.pkColumm = pkColumm;
	}

	public String getMasterField() {
		return masterField;
	}

	public void setMasterField(String masterField) {
		this.masterField = masterField;
	}

	public String getMasterAlias() {
		return masterAlias;
	}

	public void setMasterAlias(String masterAlias) {
		this.masterAlias = masterAlias;
	}
}
