package com.leave.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.service.printInfoService;

@WebServlet("/print/printInfo")
public class printInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public printInfo() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		printInfoService ps = new printInfoService();
		String content = "";
		
		String kind = request.getParameter("kind");
		String id = request.getParameter("id");
		if(kind == null) kind = "";
		if(kind.equals("print1")) {
			content = ps.print1(id);
		}
		else if(kind.equals("print11")) {
			content = ps.print11(id);
		}
		else if(kind.equals("print12")) {
			content = ps.print12(id);
		}
		else if(kind.equals("print13")) {
			content = ps.print13(id);
		}
		else if(kind.equals("print2")) {
			content = ps.print2(id);
		}
		else if(kind.equals("print21")) {
			content = ps.print21(id);
		}
		else if(kind.equals("print22")) {
			content = ps.print22(id);
		}
		else if(kind.equals("print23")) {
			content = ps.print23(id);
		}
		out.write(content);
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
