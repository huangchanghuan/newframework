package sos.cache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import java.io.IOException;
import java.util.*;

/**
 * memcached clientʵ������
 * ���� memcached client ʵ������
 * @author Administrator
 *
 */
public class CachedClient {
	// MemCachedClient ʵ������
	protected static MemCachedClient mcc = new MemCachedClient();
	
	// �����ʱ��ʼ��memcached�Ĳ�������
	static{
		Properties p = new Properties();
		try {
			SockIOPool pool = SockIOPool.getInstance();//���ӳ�ʵ��
			p.load(CachedClient.class.getResourceAsStream("/memcached.properties"));
			int count = Integer.parseInt(p.getProperty("memcached.count"),10);
			List<String> servers = new ArrayList<String>();
			for(int i = 1; i<=count; ++i){
				String ip = p.getProperty("memcached"+i+".ip","192.168.0.254");
				String port = p.getProperty("memcached"+i+".port","11211");
				servers.add(ip+":"+port);
			}
			pool.setServers(servers.toArray(new String[0]));
//			pool.setServers(new String[]{p.getProperty("memcached.ip", "192.168.0.254")+":"+p.getProperty("memcached.port", "11211")});
			pool.setNagle(false);
			pool.setSocketTO(3000);
			pool.setSocketConnectTO(0);
			pool.initialize();
			
//			failover��ʾ���ڷ�������������ʱ���Զ��޸���
//			initConn��ʼ��ʱ����������
//			minConn��ʾ��С������������
//			maxConn�����������
//			maintSleep��ʾ�Ƿ���Ҫ��ʱ����
//			nagle��TCP����socket�������㷨��
//			socketTO��socket���ӳ�ʱʱ�䣬
//			aliveCheck��ʾ������飬ȷ����������״̬��
//			Servers��memcached����˿��ĵ�ַ��ip�б��ַ�����
//			weights�������������Ȩ�أ���������һ�£�����Ȩ����Ч
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ȡ��memcachedʵ������
	 * @return
	 */
	public static MemCachedClient getClient(){
		return mcc;
	}
	
	public static void main(String[] str)throws Exception{
		//testSet();
		testGet();
	}
	
	public static void testSet(){
		Map m= new HashMap();
		m.put("date", new Date());
		m.put("name", "��ʱ����");
		mcc.set("key1", m);
	}
	
	public static void testGet(){

		System.out.println(CachedClient.getClient().delete("CITYLIST"));
	}
}
