package com.leave.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument.Content;

import com.leave.db.JDBC;

@WebServlet("/remindMessage")
public class remindMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public remindMessage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String end_day = sdf.format(new Date(date.getTime() - 24 * 60 * 60 * 1000));
		
		String sql = "select * from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_end_day=? and leave_cut=0";
		List<Map<String, Object>> list = JDBC.executeQuery(sql, end_day);
		
		if(list != null && list.size() !=0) {
			StringBuffer content = new StringBuffer("");
			content.append("昨天应到假但未销假的人员信息\\\n姓名      单位           职务          应到假日期     联系电话\\\n");
			for (Map<String, Object> map : list) {
				content.append(map.get("user_name")+"   "+map.get("user_work_address")+"  "+map.get("user_position")+"  "+map.get("leave_end_day")+"  "+map.get("user_phone"));
			}
			content.append("");
			response.getWriter().write(content.toString());
			System.out.println(content);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
