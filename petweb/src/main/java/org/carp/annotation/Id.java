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
package org.carp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.carp.annotation.CarpAnnotation.Generate;
import org.carp.id.Generator;

/**
 * �����ֶ�ע����
 * <br/>
 * <b>���ڶ����ݱ������ֶζ�Ӧ��pojo������Խ���ע�⣬��ʶ������Ϊ��������</b>
 * <table>
 * 	<tr>
 * 		<td>name</td>
 * 		<td>���ݿ�column����</td>
 * 	</tr>
 * 	<tr>
 * 		<td>build</td>
 * 		<td>�������ɷ�ʽ����ѡ���� Enum Build(assigned,sequences,auto,custom),Ĭ��Ϊ Build.assigned</td>
 * 	</tr>
 * 	<tr>
 * 		<td>builder</td>
 * 		<td>�����������࣬Ĭ�Ͻӿڣ������Ҫʵ���Զ�����������ɷ�ʽ����Ҫʵ�ָýӿ�</td>
 * 	</tr>
 * 	<tr>
 * 		<td>sequence</td>
 * 		<td>�����������������ɷ�ʽbuildΪsequencesʱ���������������ã���Ҫ����sequence��ֵ</td>
 * 	</tr>
 * 	<tr>
 * 		<td>remark</td>
 * 		<td>Field - Column ������Ϣ</td>
 * 	</tr>
 * </table>
 * @author zhou
 * @since 0.1
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Id {
	/**
	 * �ֶ����ƣ����ڱ��������
	 * @return
	 */
	String name(); //��Ӧ���ݿ��ֶ���
	/**
	 * �������е���ֵ�����ɷ�ʽ
	 * @return
	 */
	Generate build() default Generate.assigned;//�������������ɷ�ʽ
	/**
	 * ����ֵ�������࣬������buildΪGenerate.sequences��ʱ��������
	 * @return
	 */
	Class<? extends Generator> builder() default Generator.class;//������������
	/**
	 * ��������
	 * ���������ɷ�ʽbuildΪsequencesʱ���������Ʒ������ã���Ҫ����sequence��ֵ
	 * @return
	 */
	String sequence() default "";//���������ɷ�ʽbuildΪsequencesʱ���������Ʒ������ã���Ҫ����sequence��ֵ��
	/**
	 * ��ע��Ϣ
	 * @return
	 */
	String remark() default ""; //�ֶ�˵��
	
}
