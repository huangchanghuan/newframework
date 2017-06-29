package sos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����ע����
 * ��Ǹ÷���Ϊsession��������Ҫ�������ݿ����
 * @author zhou
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Name {
	/**
	 * pojo�ֶε���������
	 * @return
	 */
	String name();
}
