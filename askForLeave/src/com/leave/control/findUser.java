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
import javax.servlet.http.HttpSession;

import com.leave.service.findUserService;

@WebServlet("/findUser")
public class findUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public findUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String opt = request.getParameter("opt");//查询类型
		String content = "";
		//页面数据展示
		List listName = new ArrayList();
		List listValue = new ArrayList();
		if(opt == null) opt = "";
		//获取所有有用参数
		Enumeration enu=request.getParameterNames();
		while(enu.hasMoreElements()){
			String paraName=(String)enu.nextElement();
			String paravalue = request.getParameter(paraName);
			if(paravalue != null && !paravalue.equals("")&& !paraName.equals("opt")) {//&& !paravalue.equals("userLeave")&& !paravalue.equals("find_isleave")){
				listName.add(paraName);
				listValue.add(paravalue.trim());
			}
		}
		findUserService find = new findUserService();
		
		//用户人员信息搜索
		if(opt.equals("userInfo")) {
			content = find.findUser(listName,listValue,session);
		}//历史信息查询
		else if(opt.equals("userLeave")) {
			content = find.findLeaveHistory(listName,listValue,session);
		}//审批信息查询
		else if(opt.equals("find_isleave")) {
			content = find.find_isleave(listName,listValue,session);
		}//销假信息查询
		else if(opt.equals("find_cutLeave")) {
			content = find.find_cutLeave(listName,listValue,session);
		}
		//临时请假历史记录查询
		else if(opt.equals("find_TemporaryHistoryLeave")) {
			//System.out.println(listValue);
			content = find.find_TemporaryHistoryLeave(listName,listValue,session);
		}
		out.write(content);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
