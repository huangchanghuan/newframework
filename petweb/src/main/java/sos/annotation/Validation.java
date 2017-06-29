package sos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �ֶ�ע����
 * ��Ǹ��ֶ��Ƿ�����Ψһ��Լ��
 * @author zhou
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Validation {
	/**
	 * ����Ψһ��Լ��
	 * @return true/false  ����/������
	 */
	boolean unique() default true;
}
