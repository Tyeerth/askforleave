package com.leave.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leave.control.remindMessage;
import com.leave.util.ReceiveDemo;
import com.leave.util.TimerMessage;

public class LoginFilter implements Filter{

    public void destroy() {
    	ReceiveDemo.stop();//停止短信接受监听线程
    	TimerMessage.stop();//停止提前发短信进程
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response =(HttpServletResponse) arg1;
        HttpSession session = request.getSession();
        //获取根目录所对应的绝对路径
        String path = request.getRequestURI();
        // 从session取得已经登录验证的凭证
        String id = (String) session.getAttribute("id");
        // 登陆页面无需过滤(根据自己项目的要求来)，加载的样式和图片也无需过滤
        if(path.indexOf("Login") > -1 
        		|| path.indexOf("/login.html") > -1 
        		|| path.indexOf("/css/") > -1 
        		|| path.indexOf("/js/") > -1
        		|| path.indexOf("/img/") > -1        		) {
        	//注意：登录页面千万不能过滤  不然过滤器就。。。。。自行调试不要偷懒！这样记忆深刻
            arg2.doFilter(request, response);
            return;
        }
        /*if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")) { 
            // ajax请求
            response.setHeader("sessionstatus", "timeout");
            arg2.doFilter(request, response);
        }*/ 
        //已经登陆
        
        if (id == null || "".equals(id)) {
        		// 跳转到登陆页面
        		response.sendRedirect("/"+Config.get("db.progectName")+"/login.html");
        } else {
                // 已经登陆,继续此次请求
                arg2.doFilter(request, response);
        }
        
    }
    public void init(FilterConfig arg0) throws ServletException {
    	//启动短信回复监听事件
    	reciveMessage();
    	//启动短信通知假期结束提前通知事件
    	TimerMessage.start();
    	
    	//判断是否第一次登陆
    }

	private void reciveMessage() {
		try {
			ReceiveDemo.start();
		} catch (Exception e) {
			System.out.println("短信回复监听异常");
		} 
	}
}
