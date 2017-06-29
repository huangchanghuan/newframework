package sos.constants;


/**
 * �������Ͷ�����,�������������
 * @author Administrator
 *
 */
public class ActionType
{
	private ActionType(){}
	/********************* �����������  *******************************/
	/****** ��¼��ע�� *********/
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_LOGOUT = "logout";
	/******  ������ѯ ���� **********/
	public static final String ACTION_MAIN = "main";
	public static final String ACTION_LIST = "list";
	public static final String ACTION_DATA = "data";
	public static final String ACTION_SUB_DATA = "subData";
	public static final String ACTION_SUB_LIST= "subList";
	public static final String ACTION_SEARCH = "search";
	public static final String ACTION_PREVIEW = "preview";
	public static final String ACTION_GO_DOWNLOAD = "goDownload";
	public static final String ACTION_DOWNLOAD = "download";
	public static final String ACTION_TREE = "tree";
	public static final String ACTION_NOTIFY = "notify";
	public static final String ACTION_MENU = "menu";
	public static final String ACTION_CONDITION = "condition";
	
	/******  д����� ���� **********/
	public static final String ACTION_GO_ADD = "goAdd";
	public static final String ACTION_ADD = "add";
	public static final String ACTION_GO_UPLOAD = "goUpload";
	public static final String ACTION_UPLOAD = "upload";
	
	/******  �ޡ����� ���� **********/
	public static final String ACTION_GO_UPDATE = "goUpdate";
	public static final String ACTION_UPDATE = "update";
	
	/******  ɾ��ɾ�� ���� **********/
	public static final String ACTION_DELETE = "delete";
	
	/******  �����鿴 ���� **********/
	public static final String ACTION_GO_VIEW = "goView";
	public static final String ACTION_VIEW = "view";
	
	/******  ��� ���� **********/
	public static final String ACTION_GO_CHECK = "goCheck";
	public static final String ACTION_CHECK = "check";
	
	
	/*********************   ���õ�result����֣���������������Щ   ************************/
	public static final String RESULT_ADD_UPDATE = "addOrUpdate";//ת�����/�޸Ľ���
	public static final String RESULT_NO_USER = "noUser";//û���û���session��ʱ��û�е�¼
	public static final String RESULT_NO_CMD = "cmdError";//�����ִ��󣬲������������
	public static final String RESULT_NO_PATH = "pathError";//�����ڵķ���·������û�еǼ�ע��ķ���·��
	public static final String RESULT_NO_RIGHT = "noRight";//û��Ȩ��
	public static final String RESULT_NO_FUNC_METHOD = "noFuncMethod";//û�ж��幦�ܺͷ���ӳ��
	public static final String RESULT_SUCCESS = "success"; //�����ɹ�
	public static final String RESULT_FAILSE = "failse"; //����ʧ��
	public static final String RESULT_ERROR = "error";   //�����ִ��󣬻�����������ת������ҳ��
	public static final String RESULT_MSG = "msg";  //������Ϣ,������msg�����ֶη���С��Ƭ������
	public static final String RESULT_MAIN_PAGE = "mainPage";  //����������
	public static final String BRAND_RESULT_SUCCESS = "brandMainPage";
}