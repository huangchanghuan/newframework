package sos.validators.validator;

import java.util.regex.Pattern;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * �ַ�У������
 * @author Administrator
 *
 */
public class RequiredStringValidator extends BaseValidator {
	private boolean trim = false;
	private Integer minLength;
	private Integer maxLength;
	private String expression;
	private String regex;
	
	
	/**
	 * ִ��У��
	 */
	@Override
	public boolean validator(ValueStack stack) {
		Object o = stack.findValue(this.getFieldname());
		//�����ж�
		if(o == null || ((String)o).trim().length() == 0){
			this.setErrorMessage(this.getMessage());
			return false;
		}
		//ȥ��ǰ�󲻿ɼ��ַ���
		String value = trim?((String)o).trim():(String)o;
		//�ж���С����
		if(minLength != null && value.length() < minLength){
			this.setErrorMessage(this.getMSGMap().get("minLength"));
			return false;
		}
		//�ж���󳤶�
		if(maxLength != null && value.length() > maxLength){
			this.setErrorMessage(this.getMSGMap().get("maxLength"));
			return false;
		}
		//����OGNL���ʽ
		if(expression != null &&! (Boolean)stack.findValue(expression, true)){
			this.setErrorMessage(this.getMSGMap().get("expression"));
			return false;
		}
		//������ʽ�ж�
		if(regex != null && !Pattern.matches(regex, value)){
			this.setErrorMessage(this.getMSGMap().get("regex"));
			return false;
		}
		return true;
	}
}
