package sos.conn;

import com.sunstar.adms.jdmanage.pojo.JdCategory;
import org.apache.log4j.Logger;
import org.carp.CarpSession;
import org.carp.CarpSessionBuilder;
import org.carp.cfg.CarpConfig;
import org.carp.exception.CarpException;

import java.sql.SQLException;

/**
 * Carp Session����������
 */
public class SessionUtil
{
	private static final Logger log = Logger.getLogger("SessionUtil: ");
	private static String CONFIG_FILE_LOCATION = "carp.conf.xml";
	private static ThreadLocal<CarpSession> sessionLocal = new ThreadLocal<CarpSession>();
	
	
	private static CarpConfig conf = null;
	private static CarpSessionBuilder builder = null;

	static{
		try{
			conf = new CarpConfig(CONFIG_FILE_LOCATION);
			builder = conf.getSessionBuilder();
		}catch (Exception e){
			log.debug("����Session��������");
			e.printStackTrace();
		}
	}

	/**
	 * ��ֹ�ⲿʵ����
	 */
	private SessionUtil(){}

	/**
	 * ȡ��Session��������
	 * @return
	 */
	public static CarpSessionBuilder getSessionBuilder(){
		return builder;
	}
	
	/**
	 * ȡ��Session����
	 * @return Session
	 * @throws CarpException 
	 */
	public static CarpSession getSession() throws CarpException{
//		return builder.getSession();
		try {
			CarpSession s = sessionLocal.get();
			if(s == null || !s.isOpen()){
				s = builder.getSession();
			}
			sessionLocal.set(s);
			return s;
		} catch (CarpException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * �ر�Session
	 * @throws CarpException 
	 */
	public static void closeSession(){
		CarpSession s = sessionLocal.get();
		sessionLocal.remove();
		if (s != null && s.isOpen()){
			try { s.close(); } catch (CarpException e) { e.printStackTrace();}
			s = null;
		}
	}
	
	/**
	 * �ر�Session
	 * @throws CarpException 
	 */
	public static void closeSession(CarpSession _session){
		CarpSession s = sessionLocal.get();
		sessionLocal.remove();
		if (s != null && s.isOpen()){
			try { s.close(); } catch (CarpException e) {e.printStackTrace(); }
			s = null;
		}
		if(_session != null && _session.isOpen())
			try {
				_session.close();
			} catch (CarpException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) throws CarpException, SQLException {
		CarpSession session = SessionUtil.getSession();
		JdCategory category = new JdCategory();
		category.setClsName("22");
		category.setParentId(7654);
		category.setType("1");
		category.setClsLevel("1");
		category.setSts("1");
		category.setJdClsId(1782);
		session.beginTransaction();
		session.save(category);
		session.getConnection().setAutoCommit(false);
	}
}