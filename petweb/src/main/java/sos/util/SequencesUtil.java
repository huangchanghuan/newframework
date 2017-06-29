package sos.util;

import com.sunstar.sos.dao.SequencesDao;
import com.sunstar.sos.pojo.SysSequences;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * ����ֵ������
 * @author zhou
 */
public class SequencesUtil 
{
	private static final Map<String,Long[]> map = new HashMap<String,Long[]>(); 
	private final static SequencesUtil _instance = new SequencesUtil();
	
	private SequencesUtil(){
	}
	
	public static SequencesUtil getInstance(){
		return _instance;
	}
	
	/**
	 * ȡ��cls���Ӧ��table����һ������ֵ
	 * @param cls
	 * @return
	 */
	public Long getNextPk(Class<?> cls){
		Long currValue = null;
		String tableName = this.getTableName(cls);
		currValue = getNextPk(tableName);
		return currValue;
	}
	
	/**
	 * ��ȡtable����һ������ֵ
	 * @param tableName
	 * @return
	 */
	public Long getNextPk(String tableName){
		Long currValue = null;
		if(tableName==null || tableName.trim().equals(""))
			return currValue;
		try{
			tableName = tableName.toUpperCase();
			Long[] value = (Long[])map.get(tableName);
			//���ڿ���ֵ
			if(value!=null && value[2].longValue()< value[1].longValue()){
				currValue = new Long(value[0].longValue());
				value[0] = new Long(value[0].longValue()+1);
				value[2] = new Long(value[2].longValue()+1);
			}else{//�����ڿ���ֵ�����´����б���ȡ���µ�����ֵ
				SequencesDao dao = new SequencesDao();
				SysSequences obj = dao.findByPk(tableName);//dao.getSeqs(tableName);
				currValue = new Long(obj.getCurrValue().longValue());
				value = new Long[3];
				value[0] = obj.getCurrValue().longValue()+1;//currValue;//obj.getCurrValue();
				value[1] = obj.getIncrementvalue().longValue();
				value[2] = new Long(1);
			}
			map.put(tableName, value);
		}catch(Exception e){
			e.printStackTrace();
		}
		return currValue;
	}
	
	/**
	 * ��ȡtable����һ������ֵ--ȯ��ר�ã���ʽΪO+yyMMdd001
	 * @param tableName
	 * @return
	 */
	public Long getNextOrderPk(String tableName,String type){
		Long currValue = null;		
		if(tableName==null || tableName.trim().equals(""))
			return currValue;
		try{
			tableName = tableName.toUpperCase();
			Long[] value = (Long[])map.get(tableName);
			//���ڿ���ֵ
			if(value!=null && value[2].longValue()< value[1].longValue()){
				currValue = new Long(value[0].longValue());
				value[0] = new Long(value[0].longValue()+1);
				value[2] = new Long(value[2].longValue()+1);
			}else{//�����ڿ���ֵ�����´����б���ȡ���µ�����ֵ
				SequencesDao dao = new SequencesDao();
				//ȯ��ר�����ɷ�������ʽΪyyMMdd001
				SysSequences obj = null;
				System.out.println("ȯ��ר�����ɷ���======");
					obj = dao.findByVoucherPk(tableName);//dao.getSeqs(tableName);
					System.out.println("ȯ��ר�����ɷ���obj======"+obj.toString());
				currValue = new Long(obj.getCurrValue().longValue());
				value = new Long[3];
				value[0] = obj.getCurrValue().longValue()+1;//currValue;//obj.getCurrValue();
				value[1] = obj.getIncrementvalue().longValue();
				value[2] = new Long(1);
			}
			map.put(tableName, value);
		}catch(Exception e){
			e.printStackTrace();
		}
		return currValue;
	}
	
	/**
	 * ����clsȡ��table����
	 * @param cls
	 * @return
	 */
	private String getTableName(Class<?> cls){
		try {
			return BeansFactory.getBean(cls).getTable();
		} catch (CarpException e) {}
		return null;
	}
}
