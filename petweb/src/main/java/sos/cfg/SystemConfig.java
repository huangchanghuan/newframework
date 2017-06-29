package sos.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunstar.adms.sysmanage.pojo.SysParameters;
import com.sunstar.sos.factory.BpoFactory;

/**
 * �Ƿ�����Ȩ�ޡ����ݡ�������sql��ʾ��BPOProxy����־��¼
 * @author zhou
 */
public class SystemConfig {
	private static final Logger logger = Logger.getLogger(SystemConfig.class);
	private static boolean permissionlog = true;
	private static boolean datalogs = true;
	private static boolean sqlconfig = true;
	private static boolean bpoproxy = true;
	private static String  logLocation = "db";
	
	static{
		refresh();
	}
	
	public static void refresh(){
		try {
			String sql = "select * from ss_sys_parameters where ";
			sql += "parameter in ('DATA_LOG','SQL_CONFIG_LOG','PERMISSION_LOG','BPO_PROXY_LOG','LOG_OUTPUT_LOCATION')";
			List<?> list = BpoFactory.getBpo().search(SysParameters.class, sql);
			Map<String,String> map = new HashMap<String,String>();
			for(Object o : list){
				SysParameters p = (SysParameters)o;
				map.put(p.getParameter(),p.getValue());
			}
			permissionlog = new Boolean(map.get("PERMISSION_LOG")!=null?map.get("PERMISSION_LOG"):"true");
			datalogs = new Boolean(map.get("DATA_LOG")!=null?map.get("DATA_LOG"):"true");
			sqlconfig = new Boolean(map.get("SQL_CONFIG_LOG")!=null?map.get("SQL_CONFIG_LOG"):"true");
			bpoproxy = new Boolean(map.get("BPO_PROXY_LOG")!=null?map.get("BPO_PROXY_LOG"):"true");
			logLocation = (map.get("LOG_OUTPUT_LOCATION")!=null?map.get("LOG_OUTPUT_LOCATION"):"DB").toLowerCase();
			logger.info("Permission log : " + permissionlog);
			logger.info("data logs recode : " + datalogs);
			logger.info("sql config : " + sqlconfig);
			logger.info("bpo proxy : " + bpoproxy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �Ƿ���Ҫ������־��ָ���б䶯�����ݴ洢����
	 * @return
	 */
	public static boolean isDatalogs() {
		return datalogs;
	}
	
	/**
	 * ������Ȩ�޿���ʱ���Ƿ���ʾȨ��log
	 * @return
	 */
	public static boolean isPermissionlog() {
		return permissionlog;
	}

	/**
	 * �Ƿ���Ҫ��ʾsql
	 * @return
	 */
	public static boolean isSqlconfig() {
		return sqlconfig;
	}
	
	/**
	 * �Ƿ���Ҫ��ʾbpo proxy ����ĵ�����־��¼
	 * @return
	 */
	public static boolean isBpoproxy() {
		return bpoproxy;
	}
	
	public static String logLocation(){
		return logLocation;
	}
}
