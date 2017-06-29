package sos.log;

import org.apache.log4j.Logger;

public interface ADMSLogger {
	static Logger logger = Logger.getLogger(Logger.class);
	/**
	 * ��¼������־
	 * @param tableName ���������ݱ�
	 * @param actionType �������ͣ�WRITE��UPDATE�� DELETE�� LOGIN�� LOGON
	 * @param result ����������ɹ�/ʧ��
	 * @param content �������ݣ�pojo����
	 */
	public void log(String table, String actionType, String result, String content);
	
	/**
	 * ��¼������־
	 * ������¼��ѯ�����������
	 * @param result �������,�������������Ϊ�쳣��Ϣ
	 */
	public void log(String result);

}
