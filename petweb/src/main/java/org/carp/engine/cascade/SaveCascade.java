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
package org.carp.engine.cascade;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.carp.assemble.Assemble;
import org.carp.beans.CarpBean;
import org.carp.beans.ColumnsMetadata;
import org.carp.beans.OTMMetadata;
import org.carp.beans.OTOMetadata;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;
import org.carp.impl.AbstractCarpSession;
import org.carp.type.TypesMapping;
import org.carp.util.ObjectUtil;

/**
 * �������������
 * @author zhou
 * @since 0.2
 */
public class SaveCascade implements Cascade{
	private AbstractCarpSession _session;
	private CarpBean _bean;
	private Object _data;
	private Object _key;
	public SaveCascade(AbstractCarpSession session, CarpBean bean, Object data, Object key){
		this._session = session;
		this._bean = bean;
		this._data = data;
		this._key = key;
	}
	
	/**
	 * �ֵ伶������
	 * @throws CarpException
	 */
	public Cascade cascadeDICOperator() throws CarpException{
		return this;
	}
	
	/**
	 * һ�Զ༶������
	 * @throws CarpException
	 * @throws SQLException
	 */
	public Cascade cascadeOTMOperator() throws Exception{
		List<OTMMetadata> otms = _bean.getOtms();
		if(otms != null)
			for(OTMMetadata otm : otms){
				if(this.isCascadeSave(otm.getCascade())){
					java.util.Collection<?> collection = (java.util.Collection<?>)otm.getValue(_data);
					this.processCascade(collection, otm);
				}
			}
		
		return this;
	}
	
	private void processCascade(java.util.Collection<?> list, OTMMetadata otm) throws Exception{
		if(list != null && !list.isEmpty()){
			Object id = _key;
			CarpBean childBean = BeansFactory.getBean(otm.getChildClass());
			if(childBean == null)
				throw new CarpException("��"+otm.getChildClass()+"û��������Ӧ�����ݿ�ע����Ϣ");
			List<ColumnsMetadata> childCols = childBean.getColumns();
			Field f = null;
			for(ColumnsMetadata child : childCols){
				if(child.getColName().equals(otm.getFkey())){
					f = child.getField();
					break;
				}
			}
			if(f == null)
				throw new CarpException("����"+_bean.getTable()+" ��Ӧ�Ĵӱ� "+childBean.getTable()+" û�ж�Ӧ������ֶΣ�"+otm.getFkey());
			Assemble assemble = (Assemble)TypesMapping.getAssembleClass(f.getType()).newInstance();
			for(Iterator<?> it = list.iterator(); it.hasNext();){
				Object childObject = it.next();
				if(childObject == null)
					continue;
				if(ObjectUtil.getValue(childObject, f) == null)
					assemble.setFieldValue(childObject, f, id);
				_session.save(childObject);
			}
		}
	}
	/**
	 * һ��һ��������
	 * @throws CarpException
	 */
	public Cascade cascadeOTOOperator() throws Exception{
		List<OTOMetadata> otos = _bean.getOtos();
		if(otos != null && !otos.isEmpty())
			for(OTOMetadata oto : otos){
				if(this.isCascadeSave(oto.getCascade())){
					Object obj = oto.getValue(_data);
					if(obj == null)
						continue;
					CarpBean bean = BeansFactory.getBean(oto.getFieldType());
					if(bean.getPrimaryValue(obj)==null){
						Assemble assemble = (Assemble)TypesMapping.getAssembleClass(bean.getPrimarys().get(0).getFieldType()).newInstance();
						assemble.setFieldValue(obj, bean.getPrimarys().get(0).getField(), _key);
					}
					_session.save(obj);
				}
			}
		return this;
	}
	
	/**
	 * ���һ��������
	 */
	public Cascade cascadeMTOOperator(){
		return this;
	}
	
	private boolean isCascadeSave(org.carp.annotation.CarpAnnotation.Cascade _cas){
		return _cas == org.carp.annotation.CarpAnnotation.Cascade.All
				|| _cas == org.carp.annotation.CarpAnnotation.Cascade.Save
				|| _cas == org.carp.annotation.CarpAnnotation.Cascade.SaveUpdate;
	}
}
