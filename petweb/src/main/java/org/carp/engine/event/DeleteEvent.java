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
package org.carp.engine.event;

import org.carp.engine.ParametersProcessor;
import org.carp.engine.cascade.Cascade;
import org.carp.engine.cascade.DeleteCascade;
import org.carp.exception.CarpException;
import org.carp.impl.AbstractCarpSession;

public class DeleteEvent extends Event{
	public DeleteEvent(AbstractCarpSession session, Object entity) throws CarpException{
		super(session,entity,"delete");
	}

	/**
	 * ����Statement��������������ֵ
	 */
	@Override
	public void processStatmentParameters(ParametersProcessor psProcess)throws Exception {
		this.processPrimaryValues(psProcess);
	}

	@Override
	public void cascadeBeforeOperator() throws Exception {
		Object value = this.getBean().getPrimaryValue(this.getEntity());
		Cascade cascade = new DeleteCascade(this.getSession(),this.getBean(),this.getEntity(),value); 
		cascade.cascadeOTMOperator().cascadeOTOOperator();
	}
	

	@Override
	public void cascadeAfterOperator() throws Exception {
		
	}

	@Override
	protected void executeBefore() throws Exception {
		if(this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor() != null)
			this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor().onBeforeDelete(getEntity(), getSession());
		if(this.getSession().getInterceptor() != null)
			this.getSession().getInterceptor().onBeforeDelete(getEntity(), getSession());
	}

	@Override
	protected void executeAfter() throws Exception {
		if(this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor() != null)
			this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor().onAfterDelete(getEntity(), getSession());
		if(this.getSession().getInterceptor() != null)
			this.getSession().getInterceptor().onAfterDelete(getEntity(), getSession());
	}
}
