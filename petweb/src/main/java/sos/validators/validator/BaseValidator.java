package sos.validators.validator;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * ��֤��
 * @author Administrator
 *
 */
public abstract class BaseValidator {
	private static Map<String,Class<BaseValidator>> map = new HashMap<String,Class<BaseValidator>>();
	static{
		try{
			registerValidator();
		}catch(Exception ex){ex.printStackTrace();}
	}
	//����map
	private Map<String, String> msgMap = new HashMap<String, String>();
	private String fieldname; //�Զ�����
	private String type;//У��������
	private String retType;//�������� �� josn/html.
	private String message;
	private ValueStack stack;
	
	/**
	 * ���У��������
	 * @param key
	 * @param message
	 * @param value
	 * @throws Exception
	 */
	public void addParamter(String key, String message, String value) throws Exception{
		Field f = this.getClass().getDeclaredField(key);
		boolean b = f.isAccessible();
		f.setAccessible(true);
		if(f.getType().equals(int.class))
			f.setInt(this, Integer.valueOf(value));
		else if(f.getType().equals(float.class))
			f.setFloat(this, Float.valueOf(value));
		else if(f.getType().equals(boolean.class)){
			f.setBoolean(this, Boolean.valueOf(value));
		}else if(f.getType().equals(byte.class))
			f.setByte(this, Byte.valueOf(value));
		else if(f.getType().equals(double.class))
			f.setDouble(this, Double.valueOf(value));
		else if(f.getType().equals(long.class))
			f.setLong(this, Long.valueOf(value));
		else if(f.getType().equals(short.class))
			f.setShort(this, Short.valueOf(value));
		else if(f.getType().equals(Boolean.class))
			f.set(this, Boolean.valueOf(value));
		else if(f.getType().equals(Integer.class))
			f.set(this, Integer.valueOf(value));
		else
			f.set(this, value);
		f.setAccessible(b);
		this.msgMap.put(key, message);
	}
	
	protected Map<String, String> getMSGMap(){
		return this.msgMap;
	}
	
	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * ִ��У��
	 * @param stack ValueStackֵջ����
	 * @return У��ͨ�� ����true �� ���򷵻�false
	 */
	public abstract boolean validator(ValueStack stack);
	
	public void setValueStack(ValueStack stack){
		this.stack = stack;
	}
	public ValueStack getStack(){
		return this.stack;
	}
	
	public void setErrorMessage(String errmsg){
		if(this.retType.equals("json")){
			setJSONErrorMessage(-1,errmsg);
		}else
			setHtmlErrorMessage(errmsg);
	}
	/**
	 * ����JSON��ʽ�Ĵ�����Ϣ
	 * @param pid
	 * @param e
	 */
	private void setJSONErrorMessage(int pid,String e){
		getStack().setValue("msg", "{\"processId\":\""+pid+"\",\"msg\":\""+e+"\"}");
	}
	
	/**
	 * ����HTML��ʽ�Ĵ�����Ϣ
	 * @param e
	 */
	private void setHtmlErrorMessage(String e){
		getStack().setValue("msg", e);
	}
	
	/**
	 * ����У�������ͣ�ȡ��У��������
	 * @param type
	 * @return
	 */
	public static BaseValidator getValidator(String type){
		try {
			return map.get(type).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ����У����ע���ļ�
	 * @throws Exception
	 */
	private static void registerValidator() throws Exception{
		InputStream stream = BaseValidator.class.getClassLoader().getResourceAsStream("com/sunstar/sos/validators/Validators-Register.xml");
		SAXReader reader = new SAXReader();
		Document doc = reader.read(stream);
		List<Element> eles = doc.getRootElement().elements();
		for(Element e:eles){
			String name = e.attributeValue("type");
			String cls = e.attributeValue("class");
			map.put(name, (Class<BaseValidator>) Class.forName(cls));
		}
		stream.close();
	}

	public void setRetType(String retType) {
		this.retType = retType;
	}
}
