package com.leave.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leave.db.JDBC;

@WebServlet("/SetupField")
public class SetupField extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetupField() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		ServletContext application = request.getServletContext();
		PrintWriter out = response.getWriter();
		String opt = request.getParameter("opt");
		if(opt == null) opt ="";
		/**
		 * 字段field
		 * flag:0表示工作单位，1表示职务，2表示职级，3表示短信提前发送天数，4表示打印表格末尾编号
		 */
		if(opt.equals("getBum")) {//获得编号
			HttpSession session = request.getSession();
			String ask_for_leave_id = (String) session.getAttribute("ask_for_leave_id");
			String id = request.getParameter("id");
			
			//查询出来编号数据
			String sql = "select field from leave_setup where id=2";//id=2或者flag=4
			List<List> list = JDBC.list(sql);
			String bum = (String)list.get(1).get(0);//编号
			//同一个请假人员信息
			if(ask_for_leave_id==null || !ask_for_leave_id.equals(id)) {
			
				session.setAttribute("ask_for_leave_id", id);
				
				//编号的年月日
				int byear = Integer.parseInt(bum.substring(0, 4));
				int bmonth = Integer.parseInt(bum.substring(4, 6));
				int bday = Integer.parseInt(bum.substring(6, 8));
				//每天的年月日
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				String s_year="",s_month="",s_day="";
				if(byear==year && bmonth==month && bday==day) {//今天的编号
					int num = Integer.parseInt(bum);
					num++;//编号加1
					String field = String.valueOf(num);
					//更新编号
					sql = "update leave_setup set field=? where id=2";
					JDBC.executeUpdate(sql, field);
					//查询今天更新过后的编号
					sql = "select field from leave_setup where id=2";//id=2或者flag=4
					list = JDBC.list(sql);
					bum = (String)list.get(1).get(0);//编号
				}
				else {//昨天的编号，当天重置，当天第一个请假的人的编号
					s_year = year+"";
					s_month = month<10?("0"+month):(""+month);
					s_day = day<10?("0"+day):(""+day);
					int num = Integer.parseInt((s_year+s_month+s_day))*100+1;
					String field = String.valueOf(num);
					sql = "update leave_setup set field=? where id=2";
					JDBC.executeUpdate(sql, field);
					//今天第一个人请假的编号
					sql = "select field from leave_setup where id=2";//id=2或者flag=4
					list = JDBC.list(sql);
					bum = (String)list.get(1).get(0);//编号
				}
			}
			//int field = Integer.parseInt((String)list.get(1).get(0));
			out.write(bum);
			out.close();
		}
		else if(opt.equals("listDay")) {//天数
			String sql = "select field from leave_setup where id=1";
			List<List> list = JDBC.list(sql);
			int field = Integer.parseInt((String)list.get(1).get(0));
			
			String content = "<input id=\"field\" name=\"field\" type=\"text\" class=\"form-control\" value=\""+field+"\">";
			out.write(content);
			out.close();
		}
		else if(opt.equals("updateDay")) {//天数
			String sql = "update leave_setup set field=? where id=1";
			int field = Integer.parseInt(request.getParameter("field"));
			boolean b = JDBC.executeUpdate(sql, field);
			if(b) response.sendRedirect("update_day.html");
			String content = "";
			out.write(content);
			out.close();
		}
		else if(opt.equals("listRule")) {//规则
			StringBuffer content = new StringBuffer("");
			List<Map<String, Object>> list = (List<Map<String, Object>>) application.getAttribute("setupInfo");
			//System.out.println(list);
			if(list!=null) {
				content.append("<select name=\"user_position_rank\" class=\"form-control\">");
				for(Map map : list) {
					if((Integer)map.get("flag")==2) {
						content.append("<option value=\""+map.get("field")+"\">"+map.get("field")+"</option>");
					}
				}
				content.append("</select>");
			}
			//System.out.println(content);
			out.write(content.toString());
			out.close();
		}
		else if(opt.equals("listAddress")) {//数据展示
			String content = "";
			List<Map<String, Object>> list = (List<Map<String, Object>>) application.getAttribute("setupInfo");
			//System.out.println(list);
			if(list!=null) {
				for(Map map : list) {
					if((Integer)map.get("flag")==0) content += "<tr><td>"+map.get("field")+"</td><td><a onclick=\"setupAddress('delete',"+map.get("id")+")\">删除</a></td></tr>";
				}
			}
			//System.out.println(content);
			out.write(content);
			out.close();
		}
		else if(opt.equals("listPosition")) {//数据展示
				String content = "";
				List<Map<String, Object>> list = (List<Map<String, Object>>) application.getAttribute("setupInfo");
				if(list!=null) {
					for(Map map : list) {
						if((Integer)map.get("flag")==1) content += "<tr><td>"+map.get("field")+"</td><td><a onclick=\"setupPosition('delete',"+map.get("id")+")\">删除</a></td></tr>";
					}
				}
				out.write(content);
				out.close();
		}
		else if(opt.equals("listPositionRank")) {//数据展示
			String content = "";
			List<Map<String, Object>> list = (List<Map<String, Object>>) application.getAttribute("setupInfo");
			//System.out.println(list);
			if(list!=null) {
				for(Map map : list) {
					if((Integer)map.get("flag")==2) content += "<tr><td>"+map.get("field")+"</td><td><a onclick=\"setupPositionRank('delete',"+map.get("id")+")\">删除</a></td></tr>";
				}
			}
			out.write(content);
			out.close();
		}
		else if(opt.equals("delete")) {//删除字段
			String id = request.getParameter("id");
			if(Integer.parseInt(id) <= 2) return;//id=1、2有特殊作用
			String sql = "delete from leave_setup where id=?";
			boolean b = JDBC.executeUpdate(sql, id);
			if(b) {
				sql = "select *from leave_setup";
				application.setAttribute("setupInfo", JDBC.executeQuery(sql));
				response.sendRedirect("update_words.html");
			}
		}
		else if(opt.equals("add")) {//添加工作单位
			String field = request.getParameter("field");
			String flag = request.getParameter("flag");
			String sql = "insert into leave_setup(field,flag) value(?,?)";
			boolean b = JDBC.executeUpdate(sql, field,flag);
			if(b) {
				sql = "select *from leave_setup";
				application.setAttribute("setupInfo", JDBC.executeQuery(sql));
				response.sendRedirect("update_words.html");
			}
		}
		else if(opt.equals("listField")) {//数据展示
			StringBuffer content = new StringBuffer("");
			List<Map<String, Object>> list = (List<Map<String, Object>>) application.getAttribute("setupInfo");
			//System.out.println(list);
			if(list!=null) {
				content.append("<div class=\"col-md-3\">\r\n" + 
						"                                        <label class=\"control-label \">工作单位</label>\r\n" + 
						"                                         <select name=\"user_work_address\" id=\"user_work_address\" class=\"form-control select2\" style=\"width: 74%\">\r\n" + 
						"	                                        <option value=\"\"></option>\r\n"); 
				for(Map map : list) {
					if((Integer)map.get("flag")==0) content.append("<option value=\""+map.get("field")+"\">"+map.get("field")+"</option>");
				}
				content.append("</select></div>");
				
				content.append("<div class=\"col-md-3\">\r\n" + 
						"                                        <label class=\"control-label \">现任职务</label>\r\n" + 
						"                                         <select name=\"user_position\" id=\"user_position\" class=\"form-control select2\" style=\"width: 74%\">\r\n" + 
						"	                                        <option value=\"\"></option>\r\n"); 
				for(Map map : list) {
					if((Integer)map.get("flag")==1) content.append("<option value=\""+map.get("field")+"\">"+map.get("field")+"</option>");
				}
				content.append("</select></div>");
				
				content.append("<div class=\"col-md-3\">\r\n" + 
						"                                        <label class=\"control-label \">职级</label>\r\n" + 
						"                                         <select name=\"user_position_rank\" id=\"user_position_rank\" class=\"form-control select2\" style=\"width: 74%\">\r\n" + 
						"	                                        <option value=\"\"></option>\r\n"); 
				for(Map map : list) {
					if((Integer)map.get("flag")==2) content.append("<option value=\""+map.get("field")+"\">"+map.get("field")+"</option>");
				}
				content.append("</select></div>");
			}
			//System.out.println(content);
			out.write(content.toString());
			out.close();
		}
		else if(opt.equals("listField2")) {//数据展示
			StringBuffer content = new StringBuffer("");
			List<Map<String, Object>> list = (List<Map<String, Object>>) application.getAttribute("setupInfo");
			//System.out.println(list);
			if(list!=null) {
				content.append("<div class=\"form-group\">\r\n" + 
						"                                        <label class=\"col-md-3 control-label \">工作单位</label>\r\n" + 
						"                                         <div class=\"col-md-6\"><select name=\"user_work_address\" id=\"user_work_address\" class=\"form-control\">\r\n" + 
						"	                                        <option value=\"\"></option>\r\n"); 
				for(Map map : list) {
					if((Integer)map.get("flag")==0) content.append("<option value=\""+map.get("field")+"\">"+map.get("field")+"</option>");
				}
				content.append("</select></div></div>");
				
				content.append("<div class=\"form-group\">\r\n" + 
						"                                        <label class=\"col-md-3 control-label \">现任职务</label>\r\n" + 
						"                                         <div class=\"col-md-6\"><select name=\"user_position\" id=\"user_position\" class=\"form-control\">\r\n" + 
						"	                                        <option value=\"\"></option>\r\n"); 
				for(Map map : list) {
					if((Integer)map.get("flag")==1) content.append("<option value=\""+map.get("field")+"\">"+map.get("field")+"</option>");
				}
				content.append("</select></div></div>");
				
				content.append("<div class=\"form-group\">\r\n" + 
						"                                        <label class=\"col-md-3 control-label \">职级</label>\r\n" + 
						"                                         <div class=\"col-md-6\"><select name=\"user_position_rank\" id=\"user_position_rank\" class=\"form-control\">\r\n" + 
						"	                                        <option value=\"\"></option>\r\n"); 
				for(Map map : list) {
					if((Integer)map.get("flag")==2) content.append("<option value=\""+map.get("field")+"\">"+map.get("field")+"</option>");
				}
				content.append("</select></div></div>");
			}
			//System.out.println(content);
			out.write(content.toString());
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
