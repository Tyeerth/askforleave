package com.leave.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyuncs.exceptions.ClientException;
import com.leave.service.leavePasswdService;

/**
 * 请假审批
 */
@WebServlet("/leavePasswd")
public class leavePasswd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public leavePasswd() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		leavePasswdService lS = new leavePasswdService();
		String opt = request.getParameter("opt");
		int id = Integer.parseInt(request.getParameter("id"));
		if(opt == null) opt = "";
		if(opt.equals("pass")) {//审核通过
			try {
				lS.pass(id);
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
		else if(opt.equals("unpass")){
			lS.unpass(id);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
