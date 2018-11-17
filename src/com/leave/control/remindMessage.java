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
			content.append("<table border=\"1\" cellspacing=\"0\" >\r\n" + 
					"		<tr class=\"ui-widget-header \">\r\n" + 
					"			<th>姓名</th>\r\n" + 
					"			<th>单位</th>\r\n" + 
					"			<th>职务</th>\r\n" + 
					"			<th>应到假日期</th>\r\n" + 
					"			<th>联系电话</th>\r\n" + 
					"		</tr>\r\n");
			for (Map<String, Object> map : list) {
				content.append("<tr><td>"+map.get("user_name")+"</td><td>"+map.get("user_work_address")+"</td><td>"+map.get("user_position")+"</td><td>"+map.get("leave_end_day")+"</td><td>"+map.get("user_phone")+"</td></tr>");
			}
			content.append("</table>");
			response.getWriter().write(content.toString());
			//System.out.println(content);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
