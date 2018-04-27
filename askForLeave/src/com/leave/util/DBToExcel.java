package com.leave.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.leave.config.Config;
import com.leave.dao.findUserDao;
import com.leave.db.JDBC;

@WebServlet("/DBToExcel")
public class DBToExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DBToExcel() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String quarterly ="",select_year="";//季度和年份
		quarterly = request.getParameter("quarterly");
		select_year = request.getParameter("select_year");
		//根据姓名，放在一起
		String sql = "select *from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed!=0 order by user_name";//未审核的不算历史
		List<Map<String, Object>> list = JDBC.executeQuery(sql);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		boolean b = false; boolean b2 = false;
		Date time1 = null;Date time2=null;
		Date start=null;Date end=null;
		if(list != null) {
			findUserDao fD = new findUserDao();
			for(int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				try {
					time1  = sdf.parse((String)map.get("leave_start_day"));
					//time2  = sdf.parse((String)map.get("leave_end_day"));
					switch (quarterly) {
						case "第1季度":
							start  = sdf.parse(select_year+"-01-01");
							end  = sdf.parse(select_year+"-03-31");//经测试，不用管闰年还是平年都可以用
							break;
						case "第2季度":
							start  = sdf.parse(select_year+"-04-01");
							end  = sdf.parse(select_year+"-06-30");//经测试，不用管闰年还是平年都可以用
							break;
						case "第3季度":
							start  = sdf.parse(select_year+"-07-01");
							end  = sdf.parse(select_year+"-09-30");//经测试，不用管闰年还是平年都可以用
							break;
						case "第4季度":
							start  = sdf.parse(select_year+"-10-01");
							end  = sdf.parse(select_year+"-12-31");//经测试，不用管闰年还是平年都可以用
							break;
						case "全季度":
							start  = sdf.parse(select_year+"-01-01");
							end  = sdf.parse(select_year+"-12-31");//经测试，不用管闰年还是平年都可以用
							break;

						default:
							break;
					}
				}
				catch (Exception e2) {
					System.out.println("季度导出出错");
				}
				b = fD.belongCalendar(time1,start,end);
				//b2 = fD.belongCalendar(time2,start,end);
				if(!b) {
					list.remove(map);
					i--;//删除元素后，从当前位置重新开始抵消影响
					if(list.size()==0) {
						break;//防止死循环
					}
					continue;
				}
			}
			
			// 声明一个工作薄
	        HSSFWorkbook wb = new HSSFWorkbook();
	        //声明一个单子并命名
	        HSSFSheet sheet = wb.createSheet();
	        //给单子名称一个长度
	        //sheet.setDefaultColumnWidth((short)15);
	        // 生成一个样式  
	        //HSSFCellStyle style = wb.createCellStyle();
	        //创建第一行（也可以称为表头）
	        HSSFRow row = sheet.createRow(0);
	        //样式字体居中
	        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        //给表头第一行一次创建单元格
	        HSSFCell cell = null;
	        cell = row.createCell(0);
	        cell.setCellValue("姓名"); 
	        //cell.setCellStyle(style);
	        cell = row.createCell(1);  
	        cell.setCellValue("单位");  
	        cell = row.createCell(2);  
	        cell.setCellValue("职务");
	        cell = row.createCell(3);  
	        cell.setCellValue("职级");
	        cell = row.createCell(4);  
	        cell.setCellValue("不在岗期间主持工作领导");
	        cell = row.createCell(5);  
	        cell.setCellValue("请假类型");
	        cell = row.createCell(6);  
	        cell.setCellValue("请假天数");
	        cell = row.createCell(7);  
	        cell.setCellValue("开始日期");
	        cell = row.createCell(8);  
	        cell.setCellValue("结束日期");
	        cell = row.createCell(9);  
	        cell.setCellValue("请假备注");
	        cell = row.createCell(10);  
	        cell.setCellValue("到岗备注");
	        cell = row.createCell(11);  
	        cell.setCellValue("审批状态");
	        for(int i = 0; i < list.size(); i++) {
	        	row = sheet.createRow(i+1);//创建第i+1行
	        	Map map = list.get(i);
	        	cell = row.createCell(0);
		        cell.setCellValue((String)map.get("user_name")); 
		        cell = row.createCell(1);  
		        cell.setCellValue((String)map.get("user_work_address"));  
		        cell = row.createCell(2);  
		        cell.setCellValue((String)map.get("user_position"));
		        cell = row.createCell(3);  
		        cell.setCellValue((String)map.get("user_position_rank"));
		        cell = row.createCell(4);  
		        cell.setCellValue((String)map.get("leave_leader"));
		        cell = row.createCell(5);  
		        cell.setCellValue((String)map.get("leave_kind"));
		        cell = row.createCell(6);  
		        cell.setCellValue((String)map.get("leave_day"));
		        cell = row.createCell(7);  
		        cell.setCellValue((String)map.get("leave_start_day"));
		        cell = row.createCell(8);  
		        cell.setCellValue((String)map.get("leave_end_day"));
		        cell = row.createCell(9);  
		        cell.setCellValue((String)map.get("leave_remark"));
		        cell = row.createCell(10);  
		        cell.setCellValue((String)map.get("leave_cut_remark"));
		        cell = row.createCell(11);
		        if((Integer)map.get("leave_passed")==1) 
		        	cell.setCellValue("通过");
		        else cell.setCellValue("不通过");//==2
	        }
	        String filename="",name="季度表.xls";
	        String root = request.getServletContext().getRealPath("/upload");
	        //系统
	        String os = System.getProperty("os.name");  
	        if(os.toLowerCase().startsWith("win")){  
				//windows系统路径,后面用filename.lastIndexOf("\\")
				filename = root+"\\"+name;
				System.out.println("windows系统："+filename);
			}  
			else {
				//linux系统路径
				filename = root+"/"+name;
				System.out.println("linux系统："+filename);
			}
	         
	        try {
	            //写入upload目录中提供下载
	            FileOutputStream out = new FileOutputStream(filename);
	            wb.write(out);
	            out.close();
	            //先将导出来的excel表写道服务器上，然后提供一个路径自动下载
	            //本地浏览器没有办法测试，服务器测试
	            PrintWriter rout = response.getWriter();
	            rout.write("/"+Config.get("db.progectName")+"/upload/"+name);//返回一个路径提供访问下载，
	            rout.close();
	            //out.flush();
	            System.out.println("导出成功！");
	        } catch (Exception e) {
	        	System.out.println("导出失败！");
	        }
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
