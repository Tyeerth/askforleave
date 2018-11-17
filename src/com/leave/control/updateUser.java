package com.leave.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.service.updateUserService;

@WebServlet("/updateUser")
public class updateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public updateUser() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		//判断进行的操作
		String operate = request.getParameter("opt");
		if(operate == null) operate = "";
		if(operate.equals("add")) {
			List list = new ArrayList();
			//获取所有有用参数
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){  
				String paraName=(String)enu.nextElement();
				
				list.add(request.getParameter(paraName));
				
				//System.out.println(paraName+": "+request.getParameter(paraName));  
			}
			list.remove(0);//删除操作动作
			updateUserService supdd = new updateUserService();
			boolean b = supdd.insertUser(list);
			if(b) request.getRequestDispatcher("user_information.html").forward(request, response);
		}
		else if(operate.equals("update")) {
			List list = new ArrayList();
			//获取所有有用参数
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){  
				String paraName=(String)enu.nextElement();
				
				list.add(request.getParameter(paraName));
				
				//System.out.println(paraName+": "+request.getParameter(paraName));  
			}
			list.remove(0);//删除操作动作
			updateUserService supdd = new updateUserService();
			supdd.updatetUser(list);
		}
		else if(operate.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			updateUserService supdd = new updateUserService();
			supdd.deletetUser(id);
			request.getRequestDispatcher("user_information.html").forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
