package com.leave.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.service.pageListService;

@WebServlet("/updatePageList")
public class updatePageList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public updatePageList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		//页面数据展示
		int id = Integer.parseInt(request.getParameter("id"));
		pageListService pL = new pageListService();
		String content = pL.updateListPage(id);
		//if(content == null) content = "";
		out.write(content);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
