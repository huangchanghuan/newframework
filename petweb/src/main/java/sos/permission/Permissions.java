package sos.permission;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.carp.DataSet;

import com.sunstar.adms.usermanage.pojo.SysUsers;
import com.sunstar.sos.cfg.SqlConfig;
import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.factory.BpoFactory;

/**
 * �û�Ȩ��
 * @author zhou
 */
public class Permissions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Permissions.class);

	//ģ��Ȩ��
	private Set<String> moduleSet = new HashSet<String>();
	//ģ��-����-������ӳ��map
	private Map<String,Set<String>> permsMap = new HashMap<String,Set<String>>();
	//����-������ӳ��map
	private Set<String> permSet = new HashSet<String>();
	
	/**
	 * ȡ���û���ӵ�е�ģ��Ȩ��
	 * @param user
	 * @throws Exception 
	 */
	public Permissions(SysUsers vo) throws Exception {
		DataSet ds = BpoFactory.getBpo().searchDataSet(SqlConfig.getSQL("UsersManage/queryPermission", vo, null));
		while(ds.next()){
			String moduleId = ds.getData("module_id").toString();
			String funcName = ds.getData("func_name").toString();
			String ns = ds.getData("name_space").toString();
			String action = ds.getData("action_name").toString();
			String method = ds.getData("method_name").toString();
			String url = ns+"/"+action+"/"+method;
			
			if(permsMap.get(funcName) == null)//ģ�鹦��δ����
				permsMap.put(funcName, new HashSet<String>());
			if(!permsMap.get(funcName).contains(url))//ģ��URLδ����
				permsMap.get(funcName).add(url);
			if(SystemConfig.isPermissionlog())
				logger.info("ģ�鹦������: "+funcName+"; ����URL: "+url);
			permSet.add(url);
			if(!moduleSet.contains(moduleId)){
				moduleSet.add(moduleId);
			}
		}
	}
	
	/**
	 * �ж��û��Ƿ���в���Ȩ�ޣ���ĳ��·���ķ��ʡ�
	 * @param namespace �����ռ�
	 * @param action    action
	 * @param cmd       action����
	 * @return true/false  ��/��
	 */
	public boolean hasRight(String funcName){
		if("all".equals(LoginUser.userThread.get().getUserName()))
				return true;
		if(SystemConfig.isPermissionlog())
			logger.info("ҵ��������: "+funcName);
		return permsMap.containsKey(funcName);
	}
	
	/**
	 * �ж��û��Ƿ���в���Ȩ�ޣ���ĳ��·���ķ��ʡ�
	 * @param namespace �����ռ�
	 * @param action    action
	 * @param cmd       action����
	 * @return true/false  ��/��
	 */
	public boolean hasRight(String namespace,String action,String cmd){
		if("modules".equals(action) && "menu".equals(cmd))
			return true;
		boolean b = permSet.contains(namespace+"/"+action+"/"+cmd);
		if(!b){
			logger.info("������Action:"+namespace+"/"+action+"�ķ�����"+cmd+" ��Ӧ��ģ��ҵ���ܡ�");
			return false;
		}
		return b;
	}
	
	/**
	 * �ж��û��Ƿ���в���Ȩ�ޣ���ĳ��·���ķ��ʡ�
	 * @param namespace �����ռ�
	 * @param action    action
	 * @param cmd       action����
	 * @return true/false  ��/��
	 */
	public boolean hasRight(String funcName, String namespace,String action,String cmd){
		if("modules".equals(action) && "menu".equals(cmd))
			return true;
		Set<String> set = permsMap.get(funcName);
		if(set == null){
			logger.info("������Action:"+namespace+"/"+action+"�ķ�����"+cmd+" ��Ӧ��ģ��ҵ���ܡ�");
			return false;
		}
		if(SystemConfig.isPermissionlog())
			logger.info("ҵ��������: "+funcName+", ·����"+namespace+"/"+action+"  ���� : "+cmd+" ,  �ɷ���·������:"+set);
		return set.contains(namespace+"/"+action+"/"+cmd);
	}
	
	
	public boolean hasModuleRight(String moduleId){
		return moduleSet.contains(moduleId);
	}
//	/**
//	 * �Ƿ��и�ģ��( ModuleId) ��Ȩ�� (right)
//	 * @param moduleId ģ��ID
//	 * @param right  ������
//	 * @return
//	 */
//	public boolean hasOptRight(String moduleId, String funcName){
//		Set<String> map = permsMap.get(moduleId);
//		if(SystemConfig.isPermissionlog())
//			logger.info("ģ��Id: "+moduleId+", ���� : "+funcName+" ,  Ȩ�޼���: "+map);
//		if(map==null)
//			return false;
//		return map.contains(funcName);
//	}
}
