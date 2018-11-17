package com.leave.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.service.updateRuleService;

@WebServlet("/updateRule")
public class updateRule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public updateRule() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		ServletContext application = request.getServletContext();
		PrintWriter out = response.getWriter();
		String opt = request.getParameter("opt");
		if(opt == null) opt ="";
		
		updateRuleService us = new updateRuleService();
		if(opt.equals("listRule")) {
			String content = us.listRule();
			out.write(content);
			out.close();
		}
		else if(opt.equals("add")) {//添加规则
			List listName = new ArrayList();
			List listValue = new ArrayList();
			//获取所有有用参数
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){
				String paraName=(String)enu.nextElement();
				String paravalue = request.getParameter(paraName);
				if(paravalue != null && !paravalue.equals("") && !paraName.equals("opt")) {
					listName.add(paraName);
					listValue.add(paravalue);
				}
			}
			boolean b = us.addRule(listName,listValue);
			if(b) response.sendRedirect("rule_manage.html");
		}
		else if(opt.equals("delete")) {//删除规则
			int id = Integer.parseInt(request.getParameter("id"));
			boolean b = us.deleteRule(id);
		}
		else if(opt.equals("updateRuleList")) {//删除规则
			int id = Integer.parseInt(request.getParameter("id"));
			String content = us.updateRuleList(id);
			out.write(content);
			out.close();
		}
		else if(opt.equals("update")) {//修改规则
			List listName = new ArrayList();
			List listValue = new ArrayList();
			//获取所有有用参数
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){
				String paraName=(String)enu.nextElement();
				String paravalue = request.getParameter(paraName);
				if(paravalue != null && !paravalue.equals("") && !paraName.equals("opt")) {
					listName.add(paraName);
					listValue.add(paravalue);
				}
			}
			boolean b = us.updateRule(listName,listValue);
			if(b) response.sendRedirect("rule_add.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
