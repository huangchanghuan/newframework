package sos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����ע����
 * ��Ǹ÷���Ϊ���񷽷�����Ҫ�������ݿ��������
 * @author zhou
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface ATransaction {
}
