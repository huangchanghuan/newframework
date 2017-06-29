package sos.util;

import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.permission.LoginUser;

/**
 * �������߳���Ӧ��
 * @author zhou
 */
public class LongLinkThread extends Thread {
	private int process = 0;
	private BaseBpo bpo;
	private Object obj;
	private String type;
	private LoginUser usr;
	public LongLinkThread(BaseBpo _bpo,LoginUser user , Object o, String type){
		this.bpo = _bpo;
		this.obj = o;
		this.type = type;
		this.usr = user;
	}
	public void run(){
		System.out.println("��ǰ��¼�û�  === "+LoginUser.userThread.get());
		try {
			Object id = bpo.save(obj);
			if(id != null)
				process = 1;
			else
				process = -1;
		} catch (Exception e) {
			e.printStackTrace();
			process = -1;
		}
	}
	/**
	 * ȡ�ô������
	 * @return 0 ���ڴ����У� 1 ����ɹ��� -1  ����ʧ��.
	 */
	public int getProcess(){
		return process;
	}
}
