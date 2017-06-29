package sos.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.factory.BpoFactory;

/**
 * ��ȡ�Ƶ�ӳ������
 * @author linminghang
 *
 */
public class HotelPara {
	
	private static Map<String, String> bedTypeNameMap=new HashMap<String, String>();//��������
	
	private static Map<String, String> netTypeNameMap=new HashMap<String, String>();//������������
	
	private static Map<String, String> breakNameMap=new HashMap<String, String>();//�������
	
	private static Map<String, String> starNameMap=new HashMap<String, String>();//�Ǽ�����
	
	
	
	static{
		initBedType();
		initNetType();
		initBreak();
		initStarType();
	}
	
	
	/**
	 * ���ݴ�λӢ�Ļ�ȡ��������
	 * @param bedTypeName
	 * @return
	 */
	public static String getBedTypeNameCN(String bedTypeName){
		return bedTypeNameMap.get(bedTypeName);
	}
	
	/**
	 * ����������ʽӢ�Ļ�ȡ��������
	 * @param bedTypeName
	 * @return
	 */
	public static String getNettypeCN(String netType){
		return netTypeNameMap.get(netType);
	}
	
	/**
	 * ����������ʽӢ�Ļ�ȡ��������
	 * @param bedTypeName
	 * @return
	 */
	public static String getBreakCN(String breakType){
		return breakNameMap.get(breakType);
	}
	
	/**
	 * �����Ǽ�id��ȡ��������
	 * @param bedTypeName
	 * @return
	 */
	public static String getStarCN(String starId){
		return starNameMap.get(starId);
	}
	
	
	private static void initStarType(){
		starNameMap.put("0", "��������");
		starNameMap.put("1", "����");
		starNameMap.put("2", "����");
		starNameMap.put("3", "����");
	}
	
	private static void initBedType(){
		bedTypeNameMap.put("single", "����");
		bedTypeNameMap.put("double", "˫��");
		bedTypeNameMap.put("big", "��");
		bedTypeNameMap.put("cir", "Բ��");
		bedTypeNameMap.put("sindou", "����/˫��");
		bedTypeNameMap.put("bigdou", "��/˫��");
		bedTypeNameMap.put("bigsing", "��/����");
	}
	
	private static void initNetType(){
		netTypeNameMap.put("1", "���");
		netTypeNameMap.put("2", "�κ�");
		netTypeNameMap.put("3", "wi-fi");
		netTypeNameMap.put("4", "������");
	}
	
	private static void initBreak(){
		breakNameMap.put("7", "����");
		breakNameMap.put("8", "һ�����");
		breakNameMap.put("9", "�������");
		breakNameMap.put("10", "��λ���");
		breakNameMap.put("11", "��λ�в�");
		breakNameMap.put("15785", "�������");
		breakNameMap.put("15786", "�ķ����");
		breakNameMap.put("15789", "�߷����");
		breakNameMap.put("15787", "������");
		breakNameMap.put("15788", "�������");
		breakNameMap.put("21923", "2��1С���");
		breakNameMap.put("21925", "˫��˫��");
		breakNameMap.put("15790", "�˷����");
		breakNameMap.put("15791", "�ŷ����");
		breakNameMap.put("15792", "ʮ�����");
		breakNameMap.put("12", "��λ���");
	}
	
	

}
