package com.leave.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leave.config.Config;
import com.leave.db.JDBC;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        //super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		String opt=request.getParameter("opt");
		if(opt==null) opt="";
		if(opt.equals("login")) {
			//登陆验证
			String user_name = "";user_name=request.getParameter("username");
			String user_password = request.getParameter("userpasswd");
			
			List<Map<String, Object>> list = login(user_name, user_password);
			HttpSession session = request.getSession();
			ServletContext application = request.getServletContext();
			String id="";
			//账号密码正确
			if(list != null) {
				id = String.valueOf(list.get(0).get("id"));
				session.setAttribute("id", id);
				
				List<Map<String, Object>> list2 = setupInfo();
				application.setAttribute("setupInfo", list2);
				//设置cookie,用于判断是否是今天第一次登陆
				Cookie cookie = new Cookie("first", "1");
				cookie.setMaxAge(3600*12);//设置cookie过期时间为12个小时
				//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
				cookie.setPath("/");
				// 将Cookie添加到Response中,使之生效 
				response.addCookie(cookie);
				
				//相对user_information.html路径也可以
				response.sendRedirect("user_information.html");
				
			}
			else response.sendRedirect("login.html");
			//这里如果恰巧html页面中也有ajax传来的id参数会有问题
			//和请求转发中id冲突，用重定向		//request.getRequestDispatcher("user_information.html").forward(request, response);
			//response.sendRedirect("user_information.html");
		}
		else if(opt.equals("updatePass")) {
			String old_password = request.getParameter("old_password");
			String new_password = request.getParameter("new_password");
			String user_name=Config.get("db.name1");//这里的名称固定，多个的话可以改成session中获得名称
			List<Map<String, Object>> list = login(user_name, old_password);
			if(list!=null) {//账号密码正确
				boolean b= update(user_name,new_password);//修改密码
				if(b) {
					HttpSession session = request.getSession();
					session.invalidate();//消除会话重新登陆
					response.sendRedirect("login.html");
				}
			}
			else response.sendRedirect("update_pwd.html");
		}
	}
	//获得设置页面的配置信息
	private List<Map<String, Object>> setupInfo() {
		String sql = "select *from leave_setup";
		return JDBC.executeQuery(sql);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	//登录检查
		public static List<Map<String, Object>> login(String username, String password){
			boolean b = false;
			String sql = "select * from leave_admin a inner join leave_user b on admin_id=b.id where user_name=?";
			List<Map<String,Object>> list =  JDBC.executeQuery(sql, username);
			List<Map<String, Object>> ret = null;
			if(list != null){
				//循环遍历,看该帐号的所有对应密码是否有符合要求的
				for(Map<String,Object> map : list){
					String pass = (String) map.get("admin_password");
					String salt = pass.substring(32);
					String thishash = getHash(password, salt);
					b = thishash.equals(pass);
					if(b) {//登陆成功
						ret =  list;
					}
				}
			}
			return ret;
		}
		
		//哈希，用来密码的判断
		public static String getHash(String orign, String salt){
			return md5(orign + salt) + salt;
		}
		//获得盐
		public static String getSalt(){
			return md5(new Date().toString()).substring(0,8);
		}
		//md5
		public static String md5(String str){
			
			String ret = str;
			try {
				MessageDigest mDigest = MessageDigest.getInstance("MD5");
				mDigest.update(str.getBytes());
				byte b[] = mDigest.digest();
				int i;
				StringBuffer buffer = new StringBuffer("");
				for(int offset = 0; offset < b.length; offset++){
					i = b[offset];
					if(i < 0)
						i += 256;
					if(i < 16)
						buffer.append("0");
					buffer.append(Integer.toHexString(i));
				}
				ret = buffer.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return ret;
		}
		//修改密码
		public static boolean update(String username,String password){
			//根据md5加密加盐算法加密
			String salt = getSalt();
			String pass = md5(password+salt)+salt;
			//System.out.println(pass);
			String sql = "update leave_admin set admin_password=? where id=1";//简单粗暴些吧,管理员们的名字就不要重复了
			return JDBC.executeUpdate(sql,pass);
		}
}
