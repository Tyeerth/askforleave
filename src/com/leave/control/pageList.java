package com.leave.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aliyuncs.exceptions.ClientException;
import com.leave.service.pageListService;


@WebServlet("/pageList")
public class pageList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public pageList() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String list = request.getParameter("list");//展示不同的页面数据
		String pn = "1";pn = request.getParameter("pageNum");
		int pageNum = Integer.parseInt(pn);//分页
		pageListService pL = new pageListService();//service
		String content = "";
		if(list == null) list = "";
		if(list.equals("userInfo")) {
			//消除登陆时设置的第一次登陆系统时的销假提醒cookie
			Cookie[] cookies=request.getCookies();
    		if(cookies != null) {
    			for (Cookie cookie : cookies) {
					if(cookie.getName().equals("first") && cookie.getValue().equals("1")) {
						//cookie.setValue(null);
						cookie.setMaxAge(2);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
    		}
			//人员信息页面数据展示
			content = pL.listPage(pageNum);
		}
		else if(list.equals("leaveInfo")) {
			//请假审批
			content = pL.isLeave(pageNum);
		}//历史记录
		else if(list.equals("historyLeave")) {
			content = pL.historyLeave(pageNum);
		}//销假页面数据加载查询(历史记录)
		else if(list.equals("cuthistoryLeave")) {
			content = pL.cuthistoryLeave(pageNum);
		}
		//销假(修改历史记录)
		else if(list.equals("updatehistoryLeave")) {
			String leave_end_day = request.getParameter("leave_end_day");
			String leave_cut_remark = request.getParameter("leave_cut_remark");
			int id = Integer.parseInt((String)request.getParameter("id"));
			try {
				content = pL.updatehistoryLeave(id,leave_end_day,leave_cut_remark);
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//临时请假记录
		else if(list.equals("temporaryLeave")) {
			content = pL.temporaryLeave();
		}//临时请假历史记录
		else if(list.equals("temporaryHistoryLeave")) {
			content = pL.temporaryHistoryLeave(pageNum);
		}
		else if(list.equals("countAll")) {
			content = pL.countAll();
		}
		else if(list.equals("countAllLeave")) {
			content = pL.countAllLeave();
		}
		else if(list.equals("countAllHistory")) {
			content = pL.countAllHistory();
		}
		else if(list.equals("countAllcutHistory")) {
			content = pL.countAllcutHistory();
		}
		else if(list.equals("countAll2")) {
			content = pL.countAll2(session);
		}
		else if(list.equals("countAllLeave2")) {
			content = pL.countAllLeave2(session);
		}
		else if(list.equals("countAllHistory2")) {
			content = pL.countAllHistory2(session);
		}
		else if(list.equals("countAllcutHistory2")) {
			content = pL.countAllcutHistory2(session);
		}
		else if(list.equals("countAllTemporaryHistory")) {
			content = pL.countAllTemporaryHistory(session);
		}
		else if(list.equals("countAllTemporaryHistory2")) {
			content = pL.countAllTemporaryHistory2(session);
		}
		//if(content == null) content = "";
		out.write(content);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
