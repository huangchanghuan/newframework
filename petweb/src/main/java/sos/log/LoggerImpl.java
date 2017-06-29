package sos.log;

import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.dao.SysLoggingDao;
import com.sunstar.sos.pojo.SysLogs;
import com.sunstar.sos.util.FileUtil;

/**
 * ADMS��־�ӿ�ʵ������
 * ��¼Webϵͳ�Ĳ�����������־�����ݲ������ã�����־��¼��DB�����̨�С�
 * @author zhou
 *
 */
public class LoggerImpl implements ADMSLogger{
	
	public void log(String table, String action, String result, String content) {
		if(SystemConfig.logLocation().equals("db")){
			SysLogs logs = SysLoggingDao.processUserLogs(table, action, result);
			logs.setLogContext(FileUtil.compressString(content));
			SysLoggingDao.log(logs);
		}else{
			logger.info("��"+table);
			logger.info("������"+action);
			logger.info("�����"+result);
			logger.info("���ݣ�"+content);
		}
	}

	public void log(String result) {
		logger.info(result);
	}
}
