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

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.carp.beans.CarpBean;
import org.carp.beans.OTMMetadata;
import org.carp.beans.OTOMetadata;
import org.carp.exception.CarpException;
import org.carp.impl.AbstractCarpSession;
import org.carp.util.ObjectUtil;

/**
 * �������²�����
 * @author zhou
 * @since 0.2
 */
public class UpdateCascade implements Cascade{
	private AbstractCarpSession _session;
	private CarpBean _bean;
	private Object _data;
	private Object _key;
	public UpdateCascade(AbstractCarpSession session, CarpBean bean, Object data, Object key){
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
				if(this.isCascadeUpdate(otm.getCascade())){
					java.util.Collection<?> collection = (java.util.Collection<?>)otm.getValue(_data);
					if(collection !=null && !collection.isEmpty()){
						for(Iterator<?> it = collection.iterator(); it.hasNext();){
							Object childObject = it.next();
							if(childObject != null){
								Class<?> cls = ObjectUtil.getField(childObject.getClass(), otm.getForeignName()).getType();
								ObjectUtil.setValue(childObject, _key, otm.getForeignName(), cls);
								_session.update(childObject);
							}
						}
					}
				}
			}
		return this;
	}
	
	/**
	 * һ��һ��������
	 * @throws CarpException
	 */
	public Cascade cascadeOTOOperator() throws CarpException{
		List<OTOMetadata> otos = _bean.getOtos();
		if(otos != null)
			for(OTOMetadata oto : otos){
				if(this.isCascadeUpdate(oto.getCascade())){
					Object obj = oto.getValue(_data);
					if(obj != null)
						_session.update(obj);
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
	private boolean isCascadeUpdate(org.carp.annotation.CarpAnnotation.Cascade _cas){
		return _cas == org.carp.annotation.CarpAnnotation.Cascade.All
			|| _cas == org.carp.annotation.CarpAnnotation.Cascade.Update
			|| _cas == org.carp.annotation.CarpAnnotation.Cascade.SaveUpdate;
	}
}
