package com.leave.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.service.temporaryLeaveService;

@WebServlet("/temporaryLeave")
public class temporaryLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public temporaryLeave() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String opt = request.getParameter("opt");//展示不同的页面数据
		String content = "";
		if(opt == null) opt = "";
		
		temporaryLeaveService ts = new temporaryLeaveService();
		
		if(opt.equals("nameSearch")) {
			String user_name = request.getParameter("user_name");
			content = ts.getUserInfo(user_name);
		}//请假信息查询
		else if(opt.equals("idSearch")) {
			int id = Integer.parseInt(request.getParameter("id"));
			content = ts.getUserInfo(id);
		}
		else if(opt.equals("add")) {
			List listName = new ArrayList();
			List listValue = new ArrayList();
			//获取所有有用参数
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){
				String paraName=(String)enu.nextElement();
				String paravalue = request.getParameter(paraName);
				if(paravalue != null && !paravalue.equals("") && !paraName.equals("user_name")) {
					listName.add(paraName);
					listValue.add(paravalue);
				}
			}
			//删除opt参数
			listName.remove(0);
			listValue.remove(0);
			
			ts.addTemporaryInfo(listName,listValue);
		}
		//if(content == null) content = "";
		out.write(content);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
