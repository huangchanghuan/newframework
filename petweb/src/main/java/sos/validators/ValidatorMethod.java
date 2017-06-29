package sos.validators;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.util.ValueStack;
import com.sunstar.sos.validators.validator.BaseValidator;

/**
 * У��������ִ��У���������ִ��ĳ��������У��
 * @author Administrator
 *
 */
public class ValidatorMethod {
	private String methodName;
	private String retType;
	private String retString;
	
	/**
	 * У��������
	 */
	private List<BaseValidator> fvList = new ArrayList<BaseValidator>();
	
	/**
	 * ���캯��
	 * @param method action������
	 * @param retType  ���У��ʧ�ܺ󣬷������ݵĸ�ʽ: html json
	 * @param retString ���У��ʧ�ܺ� ����result�����ַ�����
	 */
	public ValidatorMethod(String method,String retType,String retString){
		this.methodName = method;
		this.retString = retString;
		this.retType = retType;
	}
	
	/**
	 * ����У������ִ��У��
	 * @param stack
	 * @return
	 */
	public boolean validator(ValueStack stack){
		for(BaseValidator valid : fvList){
			valid.setValueStack(stack);
			if(!valid.validator(stack))
				return false;
		}
		return true;
	}
	
	/**
	 * ���У��������
	 * @param bv
	 */
	public void addFieldValidator(BaseValidator bv){
		fvList.add(bv);
	}
	
	public String getResult(){
		return retString;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getRetType() {
		return retType;
	}
}
