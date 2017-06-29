package sos.permission;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionProxy;
import com.sunstar.adms.usermanage.pojo.SysUsers;

/**
 * ��½�û�
 * @author Administrator
 *
 */
public class LoginUser implements Serializable{
	public static ThreadLocal<LoginUser> userThread = new ThreadLocal<LoginUser>(); 
	private int userId = 0;
	private String userNo = "System";
	private String userName = "all";
	private String userCname = "ϵͳ";
	private Integer shopId = 0;
	private Integer brandId = 0;
	private String urType = "0";
	private String effDate = "";
	private String expDate = "";
	private Permissions perms;
	private Long deptId = new Long(0);
	private String deptLevel;
	private String deptType;
	private String filterDeptIds;
	private String filterLevel;
	
	public LoginUser(){}
	/**
	 * ���캯�������ݵ�½�û������ظ��û���Ȩ����Ϣ
	 * @param vo
	 * @throws Exception
	 */
	public LoginUser(SysUsers vo) throws Exception{
		this.userId = vo.getUserId();
		this.userNo = vo.getUserName();
		this.userName = vo.getUserName();
		this.userCname = vo.getUserCname();
		this.shopId = vo.getShopId();
		this.brandId = vo.getBrandId();
		this.effDate = vo.getEffDate();
		this.expDate = vo.getExpDate();
		this.urType = vo.getUrType();
		this.perms = new Permissions(vo);
	}
	
	/**
	 * �жϵ�ǰ�û���ĳ������url�Ƿ���з���Ȩ�ޡ�
	 * @param invocation 
	 * @return
	 */
	public boolean hasPermissions(ActionProxy proxy){
		if(this.getUserName().equals("all"))
			return true;
		if(proxy.getActionName().equals("password") && Pattern.matches("main|data|update", proxy.getMethod()))
			return true;
		return perms.hasRight(proxy.getNamespace(), proxy.getActionName(), proxy.getMethod());
	}
	
//	/**
//	 * �жϵ�ǰ�û���ĳ��ģ���ĳ�������Ƿ���в���Ȩ��
//	 * @param moduleId ģ��ID
//	 * @param func  ҵ����
//	 * @return ����Ȩ�޷���true�����򷵻�false
//	 */
//	public boolean hasPermission(String moduleId,String func){
//		if(this.getUserNo().equals("all"))
//			return true;
//		return perms.hasOptRight(moduleId, func);
//	}
	/**
	 * �жϵ�ǰ�û���ĳ��ģ���ĳ�������Ƿ���в���Ȩ��
	 * @param moduleId ģ��ID
	 * @param func  ҵ����
	 * @return ����Ȩ�޷���true�����򷵻�false
	 */
	public boolean hasPermission(String func){
		if(this.getUserName().equals("all"))
			return true;
		return perms.hasRight(func);
	}
	
	public boolean hasMenuPermission(String moduleId){
		if(this.getUserId()==1)
			return true;
		return perms.hasModuleRight(moduleId);
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Permissions getPerms() {
		return perms;
	}
	public void setPerms(Permissions perms) {
		this.perms = perms;
	}

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	
	public String getFilterLevel() {
		return filterLevel;
	}

	public void setFilterLevel(String filterLevel) {
		this.filterLevel = filterLevel;
	}
	public String getFilterDeptIds(){
		return this.filterDeptIds;
	}
	
	public static ThreadLocal<LoginUser> getUserThread() {
		return userThread;
	}
	public static void setUserThread(ThreadLocal<LoginUser> userThread) {
		LoginUser.userThread = userThread;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getUrType() {
		return urType;
	}
	public void setUrType(String urType) {
		this.urType = urType;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setFilterDeptIds(String filterDeptIds) {
		this.filterDeptIds = filterDeptIds;
	}
	public String getUserCname() {
		return userCname;
	}
	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}
	
}
