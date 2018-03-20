package com.leave.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.service.getUserInfoService;
import com.leave.service.relatedPersonService;

@WebServlet("/relatedPerson")
public class relatedPerson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public relatedPerson() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		String opt = request.getParameter("opt");//展示不同的页面数据

		relatedPersonService rs = new relatedPersonService();//service
		getUserInfoService gus = new getUserInfoService();
		String content = "";
		if(opt == null) opt = "";
		if(opt.equals("list")) {
			//人员信息页面数据展示
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));//分页
			int id = Integer.parseInt(request.getParameter("id"));//人员id
			content = rs.listRelated(id,pageNum);
		}
		else if(opt.equals("delete")) {
			//人员信息页面数据展示
			int user_id = Integer.parseInt(request.getParameter("user_id"));//人员id
			int user_related_id = Integer.parseInt(request.getParameter("user_related_id"));//相关人员id
			rs.deleteRelated(user_id,user_related_id);
		}
		else if(opt.equals("nameSearch")) {
			int leave_user_id = Integer.parseInt(request.getParameter("leave_user_id"));
			String user_name = request.getParameter("user_name");
			content = gus.getUserInfo2(leave_user_id,user_name);
		}//请假信息查询
		else if(opt.equals("idSearch")) {
			int leave_user_id = Integer.parseInt(request.getParameter("leave_user_id"));
			int id = Integer.parseInt(request.getParameter("id"));
			content = gus.getUserInfo2(leave_user_id,id);
			//if(content == null) content = "";
		}
		//if(content == null) content = "";
		out.write(content);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
