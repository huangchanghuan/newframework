/**
 * Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carp.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.carp.CarpSessionBuilder;
import org.carp.exception.CarpException;
import org.carp.intercept.Interceptor;
import org.dom4j.Document;
import org.dom4j.DocumentException;

/**
 * ��ȡCarp�������ļ�
 * @author zhou
 */
public final class CarpConfig {
	private static final Logger logger = Logger.getLogger(CarpConfig.class);
	//���ݿ����������ļ�: carp.conf.xml or carp.properties ��Ĭ��carp.conf.xml
	public final static String CONNECT_CONFIG = "carp.conf.xml";
	private Document doc;
	private Properties prop;
	private Interceptor interceptor;
	
	/**
	 * ��ʼ��Carp����
	 * @throws CarpException
	 */
	public CarpConfig() throws CarpException{
		this(CONNECT_CONFIG);
	}
	
	/**
	 * ����carp�����ļ�����ʼ��carp����
	 * @param conf carp�����ļ���
	 * @throws CarpException
	 * @throws IOException 
	 */
	public CarpConfig(String conf) throws CarpException{
		if(conf == null)
			throw new CarpException("conf is null��");
		URL url = this.getClass().getClassLoader().getResource(conf);
		if(conf.toLowerCase().endsWith(".xml") && url == null){
			conf = "carp.properties";
			url = this.getClass().getClassLoader().getResource(conf);
		}
		if(url == null)
			throw new CarpException("Carp �����ļ������ڣ�"+conf);
		logger.info("Carp �����ļ���"+conf);
		if(conf.toLowerCase().endsWith(".xml")){ //xml
			this.parser(this.getClass().getClassLoader().getResourceAsStream(conf));
		}else{
			prop = new Properties();
			try{
				prop.load(this.getClass().getClassLoader().getResourceAsStream(conf));
			}catch(Exception e){
				throw new CarpException("����Carp�����ļ�ʧ�ܣ�"+conf,e);
			}
		}
	}
	
	/**
	 * ���������ļ�
	 * @param p
	 */
	public CarpConfig(Properties p){
		this.prop = p;
	}
	/**
	 * ����carp�����ļ�����������ʼ��Carp����
	 * @param stream
	 * @throws CarpException
	 */
	public CarpConfig(InputStream stream) throws CarpException{
		if(stream == null)
			throw new CarpException("stream is null��");
		this.parser(stream);
	}
	
	/**
	 * ����carp�ļ�������
	 * @param resName carp�ļ�������
	 * @throws CarpException
	 */
	private void parser(InputStream resName) throws CarpException{
		org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader(); 
		try {
			doc = reader.read(resName);
		} catch (DocumentException e) {
			throw new CarpException("���ݿ����������ļ���������",e);
		}
	}
	
	/**
	 * ����Carp���ã������Ự����������
	 * @return CarpSessionBuilder  �Ự����������
	 * @throws CarpException
	 */
	public CarpSessionBuilder getSessionBuilder() throws CarpException{
		CarpSetting setting = null;
		if(doc != null)
			setting = new CarpSetting(doc);
		else
			setting =  new CarpSetting(prop);
		CarpSessionBuilder builder = new CarpSessionBuilder(setting);
		logger.info("����SessionBuilder����");
		logger.info("ΪSessionBuilder����������������"+this.interceptor);
		builder.getCarpConfig().setInterceptor(this.interceptor);
		return builder;
	}
	
	/**
	 * ��������������
	 * @param interceptor ����������
	 * @return
	 */
	public CarpConfig setInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
		return this;
	}
}
