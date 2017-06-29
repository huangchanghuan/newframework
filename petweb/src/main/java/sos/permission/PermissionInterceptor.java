package sos.permission;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunstar.sos.cfg.ModulesConfig;
import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.constants.ActionType;
import com.sunstar.sos.constants.Constants;
/**
 * Ȩ����������
 * @author zhou
 *
 */
public class PermissionInterceptor extends AbstractInterceptor {
	private static final Logger logger = Logger.getLogger(PermissionInterceptor.class);
	private static final long serialVersionUID = -7565791452709783328L;
	public void init() {
		super.init();
	}

	/**
	 * ִ������
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		ActionProxy proxy = invocation.getProxy();
		String actionName = proxy.getActionName();
		HttpServletRequest req = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse resp = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		
		HttpSession session = req.getSession();
		
		LoginUser user = (LoginUser) req.getSession().getAttribute(Constants.LOGIN_USER);
		String method = proxy.getMethod();
		req.setAttribute("_method", method);
		if(SystemConfig.isPermissionlog()){
			logger.info("����·��:"+proxy.getNamespace()+"/"+actionName+", method = "+method);
		}
		if(method == null)
			return ActionType.RESULT_NO_CMD;
		if(!ModulesConfig.existUrl(proxy.getNamespace(), actionName)){
			req.setAttribute("NO_DEFINE_URL", req.getRequestURI());
			logger.error("δ���÷���·��URL��"+req.getRequestURI()+" ����ģ���ҵ���ܣ� �Լ�ҵ��������Ӧ��Action������ϵ���á�" +
					     "����ģ����������ø�ģ��Ĺ����Լ���Action������ӳ���ϵ" );
			return "noConfUrl";
		}
		if(!ModulesConfig.isDefineFuncMap(proxy.getNamespace(), proxy.getActionName(), method)){
			logger.error("δ����Action����:"+method+" ��ҵ���ܵ�ӳ���ϵ,����ģ����������ø�ģ��Ĺ�����Action������ӳ���ϵ");
			return ActionType.RESULT_NO_FUNC_METHOD;
		}
		if(user!=null) 
			LoginUser.userThread.set(user);
		//��Ҫִ��Ȩ�޹���  
		if("true".equalsIgnoreCase(session.getServletContext().getInitParameter("permission"))){
			if(actionName.equals("login") && Pattern.matches("login|logout|isLogoned", method) ){
				if(method.equals("login") && user != null)
					return ActionType.RESULT_MAIN_PAGE;
				if(method.equals("logout") && user == null)
					return ActionType.ACTION_LOGIN;
				return invocation.invoke();
			}
			if(user == null)
				return ActionType.RESULT_NO_USER;
			if(!actionName.equals("login")){
				if(!user.hasPermissions(proxy))
					return ActionType.RESULT_NO_RIGHT;
				else
					return invocation.invoke();
			}
		}
		return invocation.invoke();
	}
}
