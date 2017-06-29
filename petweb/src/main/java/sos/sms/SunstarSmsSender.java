package sos.sms;

import java.util.UUID;

import com.sunstar.sms.GsmsResponse;
import com.sunstar.sms.MTPacks;
import com.sunstar.sms.MessageData;
import com.sunstar.sms.WebServiceLocator;

public class SunstarSmsSender {

	public static final String SMS_USER = "hfx@sunstar";
	public static final String SMS_PASS = "Oe3pgUYi";

	public static String send(String phone,String content){
		String ret = "";
		try{
			WebServiceLocator webServiceLocator = new WebServiceLocator();
			String strArgs[] = new String[2];
			strArgs[0] = SMS_USER;		//�˺�
			strArgs[1] = SMS_PASS;		//����
			
			MessageData[] messagedatas = new MessageData[1];   //��������
			for(int i = 0; i < messagedatas.length; i++){
				messagedatas[i] = new MessageData();
				messagedatas[i].setContent(content);//��Ϣ����
				messagedatas[i].setPhone(phone);
				messagedatas[i].setVipFlag(true);
			}
			
			MTPacks pack = new MTPacks();
			pack.setUuid(UUID.randomUUID().toString());
			pack.setBatchID(UUID.randomUUID().toString());
			pack.setBatchName("ss_"+System.currentTimeMillis());
			pack.setMsgs(messagedatas);
			pack.setMsgType(1);//��Ϣ���ͣ�����1������2
			pack.setCustomNum("13801");//��չ��
			pack.setSendType(0);//�������ͣ�Ⱥ��0���鷢1
			pack.setDistinctFlag(true);//�Ƿ�����ظ�����
			//���������������ţ�ע�ʹ˴���
			GsmsResponse resp = webServiceLocator.getWebServiceSoap().post(strArgs[0], strArgs[1], pack);
			ret = resp.getMessage();
		}catch(Exception e){
			ret = "ʧ��";
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String sendCode(String phone,String code){
		String ret = "";
		try{
			String content = "��֤�룺"+code+"��������֤���֪���˲�ȷ�ϸ������������˲�����  ";
			WebServiceLocator webServiceLocator = new WebServiceLocator();
			String strArgs[] = new String[2];
			strArgs[0] = SMS_USER;		//�˺�
			strArgs[1] = SMS_PASS;		//����
			
			MessageData[] messagedatas = new MessageData[1];   //��������
			for(int i = 0; i < messagedatas.length; i++){
				messagedatas[i] = new MessageData();
				messagedatas[i].setContent(content);//��Ϣ����
				messagedatas[i].setPhone(phone);
				messagedatas[i].setVipFlag(true);
			}
			
			MTPacks pack = new MTPacks();
			pack.setUuid(UUID.randomUUID().toString());
			pack.setBatchID(UUID.randomUUID().toString());
			pack.setBatchName("ss_"+System.currentTimeMillis());
			pack.setMsgs(messagedatas);
			pack.setMsgType(1);//��Ϣ���ͣ�����1������2
			pack.setCustomNum("13801");//��չ��
			pack.setSendType(0);//�������ͣ�Ⱥ��0���鷢1
			pack.setDistinctFlag(true);//�Ƿ�����ظ�����
			GsmsResponse resp = webServiceLocator.getWebServiceSoap().post(strArgs[0], strArgs[1], pack);
			ret = resp.getMessage();
		}catch(Exception e){
			ret = "ʧ��";
			e.printStackTrace();
		}
		return ret;
	}
}
