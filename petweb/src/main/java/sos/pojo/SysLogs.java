package sos.pojo;

import org.carp.annotation.Column;
import org.carp.annotation.Id;
import org.carp.annotation.Table;

/**
 * SysLogs pojo�ࡣ
 * @author �ܱ�  �������ɹ���v1.0
 * @version 1.0
 */
@Table(name = "ss_sys_logs", remark = "������־�����ڼ�¼ϵͳ��¼����ӡ��޸ġ�ɾ��������")
public class SysLogs implements java.io.Serializable{
	private static final long serialVersionUID = -2226198764006724287L;

	@Id(name = "log_id", remark = "����")
	private Integer logId;
	
	@Column(name = "user_no", remark = "�����û���")
	private String userNo;
	
	@Column(name = "table_name", remark = "������")
	private String tableName;
	
	@Column(name = "action_type", remark = "�������� ��¼��LOGIN ��ӣ�WRITE �޸ģ�UPDATE ɾ����DELETE")
	private String actionType;
	
	@Column(name = "action_time", remark = "����ʱ��")
	private String actionTime;
	
	private String endTime;
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	@Column(name = "log_context", remark = "��������")
	private byte[] logContext;
	
	@Column(name = "action_result", remark = "�������  �ɹ���SUCC ʧ�ܣ�FAIL")
	
	private String actionResult;
	
	private String context;
	

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getLogId()
	{
		return logId;
	}
	
	public void setLogId(Integer logId)
	{
		this.logId = logId;
	}

	public String getUserNo()
	{
		return userNo;
	}
	
	public void setUserNo(String userNo)
	{
		this.userNo = userNo;
	}

	public String getTableName()
	{
		return tableName;
	}
	
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getActionType()
	{
		return actionType;
	}
	
	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}

	public String getActionTime()
	{
		return actionTime;
	}
	
	public void setActionTime(String actionTime)
	{
		this.actionTime = actionTime;
	}

	public byte[] getLogContext()
	{
		return logContext;
	}
	
	public void setLogContext(byte[] logContext)
	{
		this.logContext = logContext;
	}

	public String getActionResult()
	{
		return actionResult;
	}
	
	public void setActionResult(String actionResult)
	{
		this.actionResult = actionResult;
	}


	public String toString(){
		StringBuffer buf = new StringBuffer();
				buf.append("\r\nlogId ="+ this.logId );
						buf.append("\r\nuserNo ="+ this.userNo );
				buf.append("\r\ntableName ="+ this.tableName );
				buf.append("\r\nactionType ="+ this.actionType );
				buf.append("\r\nactionTime ="+ this.actionTime );
				buf.append("\r\nlogContext ="+ this.logContext );
				buf.append("\r\nactionResult ="+ this.actionResult );
				return buf.toString();
	}
}
