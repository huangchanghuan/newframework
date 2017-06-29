package sos.cfg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.carp.DataSet;

import com.sunstar.sos.factory.BpoFactory;

/**
 * ģ��-����-����������
 * @author zhou
 *
 */
public class ModulesConfig {
	private static final Logger logger = Logger.getLogger(ModulesConfig.class);
	//����·����ģ��ID
	private static final Map<String,String> map = new java.util.concurrent.ConcurrentHashMap<String, String>();
	//ģ��ID��ģ������
	private static final Map<String,String> moduleMap = new java.util.concurrent.ConcurrentHashMap<String, String>();
	//����·����action����
	private static final Map<String,String> urlMap = new java.util.concurrent.ConcurrentHashMap<String, String>();
	//action��action�����빦��
	private static final Map<String,String> funcMap = new java.util.concurrent.ConcurrentHashMap<String, String>();

	/**
	 * ��ʼ������
	 */
	static{
		loading();
	}
	
	/**
	 * ����ģ��-����-����
	 */
	public static void loading(){
		try{
			map.clear();
			urlMap.clear();
			moduleMap.clear();
			funcMap.clear();
			DataSet	ds = BpoFactory.getBpo().searchDataSet(SqlConfig.getSQL("SYSTEM/moduleFuncsConfig").getSql());
			while(ds.next()){
				String id = ""+ds.getData("module_id");
				//����·��������action����
				String url = ""+ds.getData("name_space")+"/"+ds.getData("action_name");
				String func = ""+ds.getData("func_name");
				String name = ds.getData("module_name")+"["+ds.getData("module_cname")+"] -- "+func;
				//����·�� ����actin����������·����Ϣ
				String method = ds.getData("name_space")+"/"+ds.getData("action_name")+"/"+ds.getData("method_name");
				urlMap.put(method, func);//������·������urlMap�У�������֤url����ȷ��
				moduleMap.put(method, name);//������·������moduleMap�У����ڻ�ȡurl��Ӧ��ģ������
				if(funcMap.get(func) == null){
					funcMap.put(func, url);//ҵ�������ƣ����ڻ�ȡ���ʵ�url
				}
				if(map.get(url) == null)
					map.put(url, id);//������·��(����action����)����map�У�������֤url����ȷ��
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * �ж�ģ���Ƿ��Ѿ�Ϊҵ���ܶ��巽��ӳ��
	 * @param url ��������
	 * @param method ��������
	 * @return
	 */
	public static boolean isDefineFuncMap(String namespace,String action,String method){
		if("login".equals(action))
			return true;
		if("modules".equals(action) && "menu".equals(method))
			return true;
		if(SystemConfig.isPermissionlog())
			logger.info("url = "+namespace+"/"+action+"/"+method);
		return urlMap.containsKey(namespace+"/"+action+"/"+method);
	}
	
	/**
	 * ·���Ƿ����
	 * @param url
	 * @return
	 */
	public static boolean existUrl(String namespace,String action){
		if(action.equals("login") || action.equals("modules"))
			return true;
		if(SystemConfig.isPermissionlog()){
			logger.info("���η���·�� URL = "+namespace+"/"+action+"  \r\n��ǰ�û��Ŀɷ���·��MAP��"+map);
		}
		return map.containsKey(namespace+"/"+action);
	}
	
//	/**
//	 * ����urlȡģ��ID
//	 * @param url
//	 * @return
//	 */
//	public static String getModuleId(String namespace,String action){
//		return map.get(namespace+"/"+action);
//	}
//	/**
//	 * ����urlȡģ��ID
//	 * @param url
//	 * @return
//	 */
//	public static String getModuleFunc(String namespace,String action,String method){
//		return url.get(namespace+"/"+action+"/"+method);
//	}
}
