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

import com.leave.service.getUserInfoService;

@WebServlet("/getUserInfo")
public class getUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public getUserInfo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		getUserInfoService gus = new getUserInfoService();

		
		String opt = request.getParameter("opt");
		if(opt == null)opt="";
		//请假信息查询
		if(opt.equals("nameSearch")) {
			String user_name = request.getParameter("user_name");
			String content = gus.getUserInfo(user_name);
			//if(content == null) content = "";
			out.write(content);
			out.close();
		}//请假信息查询
		else if(opt.equals("idSearch")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String content = gus.getUserInfo(id);
			//if(content == null) content = "";
			out.write(content);
			out.close();
		}
		//请假数据详情
		else if(opt.equals("idLeavedInfo")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String content = gus.idLeavedInfo(id);
			out.write(content);
			out.close();
		}
		//个人请假历史查看
		else if(opt.equals("idleavedInfoHistory")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String content = gus.idleavedInfoHistory(id);
			out.write(content);
			out.close();
		}
		else if(opt.equals("nameLeavedInfo")) {
			String user_name = request.getParameter("user_name");
			String content = gus.nameLeavedInfo(user_name);
			//if(content == null) content = "";
			out.write(content);
			out.close();
		}
		else if(opt.equals("askForLeave")) {//请假
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
			int id = gus.insertLeave(listName,listValue);
			//out.write(id)返回得不到id,返回数据类型为text/html
			out.write(String.valueOf(id));
			out.close();
			//System.out.println(b);
			//ajax请求无法重定向和请求转发，修改为ajax回调用函数中跳转
			//if(b) response.sendRedirect("isleave.html");//request.getRequestDispatcher("isleave.html").forward(request, response);
		}
		else if(opt.equals("askForLeave2")) {//请假
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
			int id = gus.insertLeave2(listName,listValue);
			//out.write(id)返回得不到id,返回数据类型为text/html
			out.write(String.valueOf(id));
			out.close();
			//System.out.println(b);
			//ajax请求无法重定向和请求转发，修改为ajax回调用函数中跳转
			//if(b) response.sendRedirect("isleave.html");//request.getRequestDispatcher("isleave.html").forward(request, response);
		}
		else if(opt.equals("addRelated")) {//添加人员的相关领导
			int leave_user_id = Integer.parseInt(request.getParameter("leave_user_id"));
			int related_leader_id = Integer.parseInt(request.getParameter("related_leader_id"));
			gus.addRelated(leave_user_id,related_leader_id);
			out.write(String.valueOf(leave_user_id));
			out.close();
		}
		else if(opt.equals("ID")){
			int id = Integer.parseInt(request.getParameter("id"));
			//System.out.println(id);
			String person_kind = gus.getPersonKind(id);
			out.write(person_kind);
			out.close();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public static void main(String[] args) {
		String a=null;
		System.out.println(a);
	}

}
