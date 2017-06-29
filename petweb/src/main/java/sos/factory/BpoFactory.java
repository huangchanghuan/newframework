package sos.factory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunstar.sos.action.BaseAction;
import com.sunstar.sos.annotation.AConnection;
import com.sunstar.sos.annotation.ATransaction;
import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.dao.BaseDao;


/**
 * ����ģʽ��ҵ����󹹽�������
 * 
 * @author zhou
 * 
 */
public class BpoFactory{
	private static final Logger logger = Logger.getLogger(BpoFactory.class);
	private static final Map<Class<?>,Class<?>>  bpoMap = new HashMap<Class<?>,Class<?>>();
	
	public static boolean isIgnoreMethod(Method m){
		return !isOpenSession(m) && !isTransactionMethod(m);
	}
	
	public static boolean isOpenSession(Method m){
		if(m.getAnnotation(AConnection.class) != null)
			return true;
		return false;
	}
	
	/**
	 * �Ƿ����񷽷�
	 * @param name
	 * @return
	 */
	protected static boolean isTransactionMethod(Method m){
		if(m.getAnnotation(ATransaction.class) != null)
			return true;
		return false;
	}
	
	/**
	 * ȡ��BaseBpo����
	 * @return
	 */
	public static BaseBpo getBpo(){
	    return getBpo(null,null);
	}
	
	/**
	 * ȡ��BaseBpo����
	 * @return
	 */
	public static BaseBpo getBpo(BaseAction action){
		return getBpo(null,action);
	}
	
	/**
	 * ȡ��pojo���Ӧ��bpo�������pojo���Ӧ��bpo���󲻴��ڣ��򷵻�BaseBpo����
	 * @param cls pojo��
	 * @return bpo����
	 */
	public static BaseBpo getBpo(Class<?> cls,BaseAction action){
		BpoProxy proxy = new BpoProxy();
		proxy.initSessionOpenCount();
		BaseBpo bpo = (BaseBpo)proxy.createProxy(getBpoClass(cls));
		BaseDao dao = (BaseDao)DaoFactory.getDao(cls);
		bpo.setDao(dao);
		bpo.setPojoClass(cls);
		bpo.setBaseAction(action);
		return bpo;
	}
	
	/**
	 * ȡ��pojo���Ӧ��bpo�������pojo���Ӧ��bpo���󲻴��ڣ��򷵻�BaseBpo����
	 * @param cls pojo��
	 * @return bpo����
	 */
	public static BaseBpo getBpo(Class<?> cls){
		BpoProxy proxy = new BpoProxy();
		proxy.initSessionOpenCount();
		BaseBpo bpo = (BaseBpo)proxy.createProxy(getBpoClass(cls));
		BaseDao dao = (BaseDao)DaoFactory.getDao(cls);
		bpo.setDao(dao);
		bpo.setPojoClass(cls);
//		bpo.setBaseAction(action);
		return bpo;
	}
	
	/**
	 * ȡ��pojo���Ӧ��bpo��
	 * @param cls
	 * @return
	 */
	private static Class<?> getBpoClass(Class<?> cls){
		if(cls==null){
			return BaseBpo.class;
		}
		Class<?> bpoCls = bpoMap.get(cls);
		if(bpoCls != null)
			return bpoCls; 
		String clsName = cls.getName();
		int index = clsName.indexOf(".pojo.");
		String bpoStr = clsName.substring(0,index)+".bpo."+clsName.substring(clsName.lastIndexOf(".")+1)+"Bpo";
		try {
			bpoCls = Class.forName(bpoStr);
		} catch (Exception e) {
			bpoCls = BaseBpo.class;
		}
		logger.debug(" Pojo : "+cls.getName()+" ; Bpo : "+bpoCls);
		bpoMap.put(cls, bpoCls);
		return bpoCls;
	}
}
