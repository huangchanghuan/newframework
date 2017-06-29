package sos.parameter;

/**
 * ��ѯ������
 * @author zhou
 *
 */
public class Parameter {
	private String obj;  //�������ͣ� po,map,user
	private String col;  //�ֶ�����
	private String type;  //����
	private String field1; //��������1
	private String field2; //��������22����������Ϊbetween andʱ��colvalue2��Ҫ������ֵ
	private String op; //��������> < = >= <= <> between and like 
	private String relation; //�ֶ�֮��Ĺ�ϵ��and / or ,��ϵΪǰƥ�䣬�磺and field=��aaa��
	private String excValue; //���ų���ֵ����Ϊ���ֵ��ʱ�򣬲�����where��������
	private boolean leftBracket; //��û��������
	private boolean rightBracket;//��û��������
	private boolean condition = false; // �ò����Ƿ��Զ�����where�������ӵ�sql���ĺ��棬�����Ҫ���������ļ����������condition=true���ɡ�
	private String switchValue; //�����field1�ֶε�ֵ���Ƿ����switchvalueֵ����������ʹ��switchcol���ֶ�����Ϊ�������ӵ�sql����У���������between and������
	private String switchCol;  //���滻col��Ϊ��ѯ��������where��������������between and������
	
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	public String getExcValue() {
		return excValue;
	}
	public void setExcValue(String excValue) {
		this.excValue = excValue;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public boolean isLeftBracket() {
		return leftBracket;
	}
	public void setLeftBracket(boolean leftBracket) {
		this.leftBracket = leftBracket;
	}
	public boolean isRightBracket() {
		return rightBracket;
	}
	public void setRightBracket(boolean rightBracket) {
		this.rightBracket = rightBracket;
	}
	public boolean isCondition() {
		return condition;
	}
	public void setCondition(boolean condition) {
		this.condition = condition;
	}
	public String getSwitchValue() {
		return switchValue;
	}
	public void setSwitchValue(String switchValue) {
		this.switchValue = switchValue;
	}
	public String getSwitchCol() {
		return switchCol;
	}
	public void setSwitchCol(String switchCol) {
		this.switchCol = switchCol;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Parameter [obj=");
		builder.append(obj);
		builder.append("\r\n, col=");
		builder.append(col);
		builder.append("\r\n, type=");
		builder.append(type);
		builder.append("\r\n, field1=");
		builder.append(field1);
		builder.append("\r\n, field2=");
		builder.append(field2);
		builder.append("\r\n, op=");
		builder.append(op);
		builder.append("\r\n, relation=");
		builder.append(relation);
		builder.append("\r\n, excValue=");
		builder.append(excValue);
		builder.append("\r\n, leftBracket=");
		builder.append(leftBracket);
		builder.append("\r\n, rightBracket=");
		builder.append(rightBracket);
		builder.append("\r\n, condition=");
		builder.append(condition);
		builder.append("\r\n, switchValue=");
		builder.append(switchValue);
		builder.append("\r\n, switchCol=");
		builder.append(switchCol);
		builder.append("]");
		return builder.toString();
	}
	
}
