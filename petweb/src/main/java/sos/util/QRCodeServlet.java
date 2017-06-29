package sos.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunstar.sos.util.QRCodeUtil;

public class QRCodeServlet extends HttpServlet {

	/**
	 * ���ɶ�ά��
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/jpeg"); // �����������

		ServletOutputStream outstream = resp.getOutputStream();
		String encoderContent = req.getParameter("website");  
	    QRCodeUtil handler = new QRCodeUtil();  
	    handler.encoderQRCode(encoderContent, outstream);
		outstream.flush();
	}

}
