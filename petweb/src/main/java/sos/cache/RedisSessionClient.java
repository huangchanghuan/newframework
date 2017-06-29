package sos.cache;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisSessionClient {
	private static JedisPool pool;
	static{
		Properties p = new Properties();
		try {
			p.load(RedisSessionClient.class.getResourceAsStream("/redis.properties"));
//			
//			#������Ķ�����  
//			redis.pool.maxActive=1024  
//			#����ܹ�����idel״̬�Ķ�����  
//			redis.pool.maxIdle=200  
//			#������û�з��ض���ʱ�����ȴ�ʱ��  
//			redis.pool.maxWait=1000  
//			#������borrow Object����ʱ���Ƿ������Ч�Լ��  
//			redis.pool.testOnBorrow=true  
//			#������return Object����ʱ���Ƿ������Ч�Լ��  
//			redis.pool.testOnReturn=true  
//			#IP  
//			redis.ip=10.11.20.140  
//			#Port  
//			redis.port=6379  
			String ip = p.getProperty("redis.ip","127.0.0.1");
			int port = Integer.parseInt(p.getProperty("redis.port","6379"));
			int maxActive = Integer.parseInt(p.getProperty("redis.pool.maxActive","4000"));
			int maxIdle = Integer.parseInt(p.getProperty("redis.pool.maxIdle","1000"));
			int maxWait = Integer.parseInt(p.getProperty("redis.pool.maxWait","1000"));
			boolean borrow = Boolean.parseBoolean(p.getProperty("redis.pool.testOnBorrow","true"));
			boolean ret = Boolean.parseBoolean(p.getProperty("redis.pool.testOnReturn","true"));
			
			JedisPoolConfig config = new JedisPoolConfig();  
			config.setMaxTotal(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWait);
			config.setTestOnBorrow(borrow);
			config.setTestOnReturn(ret);
			pool = new JedisPool(config, ip,port);  
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	public static void main(String[] strs){
		Jedis j = getJedis();
		j.set("abc", "dddddddddddddd");
		System.out.println(j.get("abc"));
		System.out.println(j.get("aa"));
		returnJedis(j);
		
	}
	
	public static void returnJedis(Jedis jedis){
		pool.returnResource(jedis);
	}
}
