package com.leave.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leave.dao.getUserInfoDao;
import com.leave.service.leavePasswdService;
import com.leave.service.relatedPersonService;
import com.leave.service.updateUserService;

/**
 * 批量操作
 * 自我建议：下次用seletc/delete where in 范围内操作
 * 一次次调用函数打开与数据库连接消耗资源
 * made:lucheng
 */
@WebServlet("/batchOperate")
public class batchOperate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public batchOperate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String opt = request.getParameter("opt");
		String ids = request.getParameter("IDS");
		
		String[] id = ids.split("\\$");//转义分割
		//System.out.println(id[0]);
		if(opt == null) opt = "";
		
		//选中的人的共同相关领导
		if(opt.equals("listRelated")){
			relatedPersonService rps = new relatedPersonService();
			String content = rps.listRelated(id);
			PrintWriter out = response.getWriter();
			out.write(content);
			out.close();
			return ;
		}
		
		leavePasswdService lps = new leavePasswdService();
		updateUserService supdd = new updateUserService();
		getUserInfoDao gDao = new getUserInfoDao();
		relatedPersonService rps = new relatedPersonService();
		for(int i = 0; i < id.length; i++){
			if(opt.equals("pass")) {//批量审核通过
				
				try {
					lps.pass(Integer.parseInt(id[i]));
				} catch (Exception e) {
					System.out.println("审核通过异常");
				}
			}
			else if(opt.equals("delete")){//批量删除
				supdd.deletetUser(Integer.parseInt(id[i]));
			}
			else if(opt.equals("addRelated")){//批量添加领导
				
				int leave_user_id = Integer.parseInt(id[i]);
				int related_leader_id = Integer.parseInt(request.getParameter("related_leader_id"));
				gDao.addRelated(leave_user_id,related_leader_id);
			}
			else if(opt.equals("deleteCommonLeader")){
				int user_id = Integer.parseInt(id[i]);//人员id
				int user_related_id = Integer.parseInt(request.getParameter("user_related_id"));//相关人员id
				rps.deleteRelated(user_id,user_related_id);
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
