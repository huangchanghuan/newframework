package sos.pojo;

import org.carp.annotation.Column;
import org.carp.annotation.Id;
import org.carp.annotation.Table;

/**
 * SysSequences pojo�ࡣ
 * @author �ܱ�  �������ɹ���v1.0
 * @version 1.0
 */
@Table(name = "ss_sys_sequences", remark = "�������й����")
public class SysSequences  implements java.io.Serializable{

	@Id(name = "table_name", remark = "����")
	private String tableName;
	
	@Column(name = "curr_value", remark = "������ʼֵ")
	private Integer currValue;
	
	@Column(name = "incrementvalue", remark = "����")
	private Integer incrementvalue;
	

	public String getTableName()
	{
		return tableName;
	}
	
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public Integer getCurrValue()
	{
		return currValue;
	}
	
	public void setCurrValue(Integer currValue)
	{
		this.currValue = currValue;
	}

	public Integer getIncrementvalue()
	{
		return incrementvalue;
	}
	
	public void setIncrementvalue(Integer incrementvalue)
	{
		this.incrementvalue = incrementvalue;
	}


	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("\r\ntableName ="+ this.tableName );
		buf.append("\r\ncurrValue ="+ this.currValue );
		buf.append("\r\nincrementvalue ="+ this.incrementvalue );
		return buf.toString();
	}
}
