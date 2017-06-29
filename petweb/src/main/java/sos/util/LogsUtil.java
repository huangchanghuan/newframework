package sos.util;

import com.sunstar.sos.pojo.SysLogs;

public class LogsUtil {
	public static String getSaveString(Object obj){
		StringBuffer context = new StringBuffer("�ѱ�������:\r\n");
		return context.append(obj.toString()).toString();
	}
	
	public static String getBatchSaveString(Object[] obj){
		StringBuffer context = new StringBuffer("�ѱ�������:\r\n");
		for(int i=0; i<obj.length; ++i){
			context.append(obj[i].toString());
			context.append("\r\n");
		}
		return context.toString();
	}
	
	public static SysLogs getLogginObject(String table,String action,Object[] obj){
		SysLogs logs = new SysLogs();
		StringBuffer context = new StringBuffer("�ѱ�������:\r\n");
		for(int i=0; i<obj.length; ++i){
			context.append(obj[i].toString());
			context.append("\r\n");
		}
		logs.setContext(context.toString());
		logs.setTableName(table);
		logs.setActionType(action);
		return logs;
	}
	
	public static String getBatchDeleteString(Object[] obj){
		StringBuffer context = new StringBuffer("��ɾ������:\r\n");
		for(int i=0; i<obj.length; ++i){
			context.append(obj[i].toString());
			context.append("\r\n");
		}
		return context.toString();
	}
	
	public static String getUpdateString(Object beforeObj,Object obj){
		StringBuffer context = new StringBuffer("�޸�ǰ����:");
		context.append(beforeObj.toString());
		context.append("\r\n�޸ĺ�����:");
		context.append(obj.toString());
		return context.toString();
	}
	
	public static String getDeleteString(Object obj){
		StringBuffer context = new StringBuffer("��ɾ������:\r\n");
		return context.append(obj.toString()).toString();
	}
}
