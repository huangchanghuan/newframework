package sos.permission;


import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunstar.sos.cache.CachedClient;
import com.sunstar.sos.util.EncryptUtil;

/**
 * �û���Ϣ����
 * ��д�û�����
 * ��дCookie,���û�keyд��Cookie���´η���ʱ����Cookie�ж�ȡkey������key�ӻ�����ȡ���û���Ϣ�����Ա��ڴﵽ���ؾ����Ŀ¼��sesssion���⣩.
 * @author Administrator
 *
 */
public class UserHelper {
	/**
	 * ����½�û���Ϣд�뻺���У��Ա����´η��ʵ�ʱ�򣬲���Ҫ���µ�½��
	 * @param key
	 * @throws Exception
	 */
	public static void saveToCache(String key,LoginUser user) throws Exception{
		CachedClient.getClient().set(key,user,new Date(60*60*1000));
	}
	
	/**
	 * ����½�û���Ϣд�뻺���У��Ա����´η��ʵ�ʱ�򣬲���Ҫ���µ�½��
	 * @param key
	 * @throws Exception
	 */
	public static void saveToCache(LoginUser user,HttpServletRequest req, HttpServletResponse res) throws Exception{
		String key = createKey(user,req,res);
		saveToCache(key,user);
	}
	
	/**
	 * ���ݼ�ֵ key�� �ӻ����л�ȡ��½�û���Ϣ
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static LoginUser getUserFromCache(String key)throws Exception{
		if(key==null)return null;
		LoginUser user = (LoginUser)CachedClient.getClient().get(key);
		return user;
	}
	
	/**
	 * ���ݼ�ֵ key�� �ӻ�����ɾ���û���Ϣ
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static void deleteUserFromCache(String key){
		CachedClient.getClient().delete(key);
	}
	
	/**
	 * дCookie
	 * @param key
	 * @param req
	 * @param res
	 */
	public static void writeKeyToCookie(String key, HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(getCookieName(req,res),key);
		c.setMaxAge(50*365*24*60*60);//Cookie 50��ʧЧ
//		c.setDomain(req.getServerName());
		c.setPath(req.getContextPath()); //���Ŀ¼�ڵ�ǰ�����ĸ�
		res.addCookie(c);
	}
	
	/**
	 * дCookie
	 * @param key
	 * @param value
	 * @param req
	 * @param res
	 */
	public static void writeKeyValueToCookie(String key, String value, HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(key,value);
		c.setMaxAge(50*365*24*60*60);//Cookie 50��ʧЧ
//		c.setDomain(req.getServerName());
		c.setPath(req.getContextPath()); //���Ŀ¼�ڵ�ǰ�����ĸ�
		res.addCookie(c);
	}
	
	/**
	 * ɾ��cookie
	 * @param key
	 * @param req
	 * @param res
	 */
	public static void deleteKeyFromCookie(HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(getCookieName(req,res),null);
		c.setPath(req.getContextPath()); //���Ŀ¼�ڵ�ǰ�����ĸ�
		c.setMaxAge(0);// ��Ϊ -1 ʱ�� ����������ر�ʱɾ��cookie.
		res.addCookie(c);
	}
	
	/**
	 * ɾ��cookie
	 * @param key
	 * @param req
	 * @param res
	 */
	public static void deleteKeyValueFromCookie(String key,HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(key,null);
		c.setPath(req.getContextPath()); //���Ŀ¼�ڵ�ǰ�����ĸ�
		c.setMaxAge(0);// ��Ϊ -1 ʱ�� ����������ر�ʱɾ��cookie.
		res.addCookie(c);
	}
	
	/**
	 * ����д�뻺��ʱ�ļ�ֵ key
	 * @param user
	 * @param req
	 * @param res
	 * @return
	 */
	public static String createKey(LoginUser user, HttpServletRequest req, HttpServletResponse res){
		String orgKey = user.getUserName()+req.getSession().getId()+System.currentTimeMillis()+req.getContextPath();
		return EncryptUtil.bytes2Hex(EncryptUtil.encryptAES(orgKey));
	}

	/**
	 * ��cookie�ж�ȡ�û��ļ�ֵkey
	 * @param req
	 * @param res
	 * @return
	 */
	public static String readKeyFromCookie(HttpServletRequest req, HttpServletResponse res){
		Cookie[] cookies = req.getCookies();
		if(cookies != null && cookies.length > 0){
			int count = cookies.length;
			String cookieName = getCookieName(req,res);
			for(int i=0; i<count; i++){
				Cookie c = cookies[i];
				if(c.getName().equals(cookieName)){
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * ����Key��cookie�л�ȡ��Ӧ��Value
	 * @param req
	 * @param res
	 * @return
	 */
	public static String readValueFromCookie(String key , HttpServletRequest req){
		Cookie[] cookies = req.getCookies();
		if(cookies != null && cookies.length > 0){
			int count = cookies.length;
			for(int i=0; i<count; i++){
				Cookie c = cookies[i];
				if(c.getName().equals(key)){
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * ����cookie��name����������ʹ����
	 * @param req
	 * @param res
	 * @return
	 */
	public static String getCookieName(HttpServletRequest req, HttpServletResponse res){
		return "user-"+req.getContextPath().substring(1)+"-key";
	}
}
