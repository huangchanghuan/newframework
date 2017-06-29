package sos.cfg;

import com.sunstar.sos.parameter.Parameter;
import com.sunstar.sos.permission.LoginUser;
import com.sunstar.sos.util.ObjectUtil;
import org.apache.log4j.Logger;
import org.carp.Query;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * sql���Ԥ������
 * @author Administrator
 *
 */
public class Sql implements Cloneable{
	private static final Logger logger = Logger.getLogger(Sql.class);
	private List<Parameter> params; //��ѯ����
	private String[] tables; //�ɱ������������
	private String tableNames; //�ɱ����,���������ǩֵ
	private String sql;	//xml��Ԥ�����sql���
	private String orderbySql; //xml��Ԥ���������sql���������ӵ�sql�ĺ��档
	private Object po;  //����ֵ���ڵ�po����
	private Map<String,Object> map;//����ֵ���ڵ�Map����
	private boolean condition; //�Ƿ���ݲ����б�����sql���������ӵ�sql�����档
	private boolean where;    // �Ƿ���Ҫ��sql���������where�ַ�����
	
	public Sql(String sql, String orderby, List<Parameter> params, boolean condition,boolean where){
		this.params = params;
		this.sql = sql;
		this.orderbySql = orderby!=null?" "+orderby:" ";
		this.condition = condition;
		this.where = where;
	}
	public List<Parameter> getParams(){
		return this.params;
	}
	
	public Sql setPoAndMap(Object _po, Map<String,Object> _map){
		this.po = _po;
		this.map = _map;
		return this;
	}
	
	public void setPo(Object _po){
		this.po = _po;
	}

	public void setMap(Map<String,Object> _map){
		this.map = _map;
	}
	
	public Sql setSql(String _sql){
		this.sql = _sql;
		return this;
	}
	
	public void setTables(String[] _table){
		this.tables = _table;
	}
	
	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}
	/**
	 * ȡ�ò�ѯSql���
	 * @return
	 * @throws Exception 
	 */
	public String getSql() throws Exception{
		return getSelectSql()+this.orderbySql;
	}
	
	public String getSqlBlock(){
		return this.sql;
	}
	/*
	 * ȡ�ò�ѯ��sql���
	 */
	public String getSelectSql() throws Exception{
		processVarTables();//����ɱ����ݱ�����
		if(params == null || params.isEmpty())
			return sql;
		if(!this.condition)
			return sql;
		StringBuilder s = new StringBuilder();
		boolean flag = true; //
		for(int i=0; i<params.size(); ++i){
			Parameter p = params.get(i);
			if(!isAppandSql(p))
				continue;
			if(this.where && flag){
				s.append(" where ");
				flag = false;
			}else
				s.append(p.getRelation());
			if(p.isLeftBracket())s.append("(");
			
			//�Ƿ����switchvalueֵ����������ʹ��switchcol���ֶ�����Ϊ�������ӵ�sql����У�
			//���򣬻���ʹ��col���ֶ�����Ϊ���� ���ӵ�sql�����档
			boolean b = p.getSwitchValue()!=null && p.getSwitchValue().equals(""+this.getValue(p));
			s.append(" ").append(b?p.getSwitchCol():p.getCol());
			if(p.getOp().equals("between"))
				s.append(" between ? and ? ");
			else if(p.getOp().equals("in") || p.getOp().equals("not in")){
				s.append(" ").append(p.getOp()).append(" (");
				Object o = this.getFieldValue(p, p.getField1());
				if(!(o instanceof List)){
					throw new Exception("������"+p.getField1()+" ����һ������!");
				}
				List<?> list = (List<?>)o;
				for(int m = 0, count = list.size(); m < count; ++m){
					if(m != 0)
						s.append(",");
					s.append("?");
				}
				s.append(") ");
			}else
				s.append(" ").append(p.getOp()).append(" ? ");
			if(p.isRightBracket())s.append(")");
		}
		return sql+s.toString();
	}
	
	/**
	 * ����ɱ����ݱ�����
	 */
	private void processVarTables(){
		if(this.tableNames != null && this.tables != null && this.tables.length > 0){
			String[] ts = this.tableNames.split(",");
			for(int i=0, count=ts.length; i<count; ++i){
				this.sql = this.sql.replaceAll(ts[i].trim(), this.tables[i]);
			}
		}
	}
	
	/**
	 * �Ƿ���Ҫ����������sql����Ƭ�θ��ӵ�sql���ĺ��档
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private boolean isAppandSql(Parameter p)throws Exception{
		if(!p.isCondition())return false;
		return isNeeded(p);
	}
	
	
	
	/**
	 * �ж��Ƿ���Ҫ����ò��������������Ӧ�Ĳ���ֵΪ������ӵ�sql���ĺ��档
	 * @param p
	 * @return
	 * @throws Exception 
	 */
	private boolean isNeeded(Parameter p) throws Exception{
		if(p.getObj().equals("po")){
			Object v = ObjectUtil.getFieldValue(po, p.getField1());
			if(SystemConfig.isBpoproxy())
				logger.info(p);
			if(v == null)return false;
			if((v+"").trim().equals(""))return false;
			if((v+"").equals(""+p.getExcValue()))return false;
		}else if(p.getObj().equals("map")){
			Object[] v = (Object[])map.get(p.getField1());
			if(v == null)return false;//����ǿ�,���������ֶ�
			//if((v[0]+"").trim().equals(""))return false;//����� �ո� ���� '' �����ֶ�
			//if((v[0]+"").equals(""+p.getExcValue()))return false;//���������ֵ�����ֶ�


			if(p.getExcValue()==null){
				if((v[0]+"").trim().equals(""))return false;//����� �ո� ���� '' �����ֶ�
			}else {
				if(!p.getExcValue().contains("|")){//���������"|",�ֶ�Ϊ"" ���� �ո�,���ӵ�sql
					if((v[0]+"").trim().equals(""))return false;//����� �ո� ���� '' �����ֶ�
					if((v[0]+"").equals(""+p.getExcValue()))return false;//���������ֵ�����ֶ�
				}else{//�������"|",�ֶ�Ϊ"" ���� �ո� ҲҪ����sql���
					if(!p.getExcValue().substring(0,p.getExcValue().indexOf("|")).equals("")){
					if((v[0]+"").equals(p.getExcValue().substring(0,p.getExcValue().indexOf("|"))))return false;//���������ֵ�����ֶ�
					}
				}
			}


		}else{ //obj = user
			LoginUser user = LoginUser.userThread.get();
			if(user == null)
				return false;
			Object v = ObjectUtil.getFieldValue(user, p.getField1());
			if(v == null)return false;
			if((v+"").trim().equals(""))return false;
			if((v+"").equals(""+p.getExcValue()))return false;
		}
		return true;
	}
	
	/**
	 * ȡ���ֶβ������ֶ�ֵ
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private Object getValue(Parameter p)throws Exception{
		if(p.getObj().equals("po")){
			return ObjectUtil.getFieldValue(po, p.getField1());
		}else if(p.getObj().equals("map")){
			Object[] v = (Object[])map.get(p.getField1());
			return v[0];
		}else{ //obj = user
			LoginUser user = LoginUser.userThread.get();
			if(user == null)
				return false;
			return ObjectUtil.getFieldValue(user, p.getField1());
		}
	}
	
	/**
	 * ע��sql���Ĳ�ѯ����
	 * @param query  ��ѯ����
	 * @throws SQLException
	 */
	public void processParameters(Query query) throws Exception{
		int idx = 1;
		for(int i=0; i<params.size(); ++i){
			Parameter p = params.get(i);
			if(!isNeeded(p))
				continue;
			Object value = getFieldValue(p,p.getField1());
			if(p.getOp() != null && (p.getOp().equals("in") || p.getOp().equals("not in"))){
				List<?> list = (List<?>)value;
				for(int m=0,count=list.size(); m<count; ++m){
					setParameterValue(query,idx,p.getType(),list.get(m));
					++idx;
				}
				--idx;
			}else{
				setParameterValue(query,idx,p.getType(),value);
				if(SystemConfig.isBpoproxy())
					logger.info("value = "+value+" , index = "+idx+", type = "+p.getType());
				//�����Ҫƴ��������䣬���ж�between������
				if(this.condition && p.isCondition() && p.getOp().equals("between")){
					++idx;
					Object v = getFieldValue(p,p.getField2());
					setParameterValue(query,idx,p.getType(),v);
				}
			}
			++idx;
		}
	}
	
	/**
	 * ��ȡָ��field��ֵ��
	 * @param p
	 * @param field
	 * @return
	 * @throws Exception 
	 */
	private Object getFieldValue(Parameter p, String field) throws Exception{
		if(p.getObj().equals("po")){
			return ObjectUtil.getFieldValue(po, field);
		}else if(p.getObj().equals("map")){
			Object[] v = (Object[])map.get(field);
			return v[0];
		}else{ //obj = user
			LoginUser user = LoginUser.userThread.get();
			if(user == null)
				return false;
			Object v = ObjectUtil.getFieldValue(user, field);
			return v;
		}
	}
	
	/**
	 * ���ò�ѯsql�Ĳ�����������ֵע�롣
	 * @param query ��ѯ����
	 * @param index ��������
	 * @param type  ��������
	 * @param value ����ֵ
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	private void setParameterValue(Query query, int index, String type, Object value) throws NumberFormatException, SQLException{
//		if(value== null)
//			query.setNull(index, Types.NULL);
		if("int".equals(type)){
			query.setInt(index, Integer.parseInt(""+value));
		}else if("long".equals(type)){
			query.setLong(index, Long.parseLong(value+""));
		}else if("short".equals(type)){
			query.setShort(index, Short.parseShort(value+""));
		}else if("double".equals(type)){
			query.setDouble(index, Double.parseDouble(value+""));
		}else if("float".equals(type)){
			query.setFloat(index, Float.parseFloat(value+""));
		}else if("string".equals(type)){
			query.setString(index,value+"");
		}else if("date".equals(type)){
			query.setDate(index, (Date)value);
		}else if("time".equals(type)){
			query.setTime(index, (Time)value);
		}else if("timestamp".equals(type)){
			query.setTimestamp(index, (Date)value);
		}else if("bytes".equals(type)){
			query.setBytes(index, (byte[]) value);
		}
	}
	
	/**
	 * cloneһ���µ�Sql����
	 */
	@Override
	public Sql clone() throws CloneNotSupportedException {
		Sql _sql = (Sql)super.clone();
		return _sql;
	}
}
