package com.leave.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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

@WebServlet("/Find_History_DBToExcel")
public class Find_History_DBToExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Find_History_DBToExcel() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");

		List listName = new ArrayList();
		List listValue = new ArrayList();
		// 获取所有有用参数
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String paravalue = request.getParameter(paraName);
			if (paravalue != null && !paravalue.equals("") && !paraName.equals("opt")) {// &&
																						// !paravalue.equals("userLeave")&&
																						// !paravalue.equals("find_isleave")){
				listName.add(paraName);
				listValue.add(paravalue.trim());
			}
		}

		String sql = "select *from leave_user a inner join ask_for_leave b on a.id=b.user_id where leave_passed!=0 and ";
		// 表单数据有值
		String leave_start_day = "";
		String leave_end_day = "";
		// int pageNum = 1;//分页初始化
		int select_year = 0;
		String leave_quarterly = "";
		if (listValue != null) {
			for (int i = 0; i < listValue.size(); i++) {
				if (listName.get(i).equals("select_year")) {// 查询的季度年份
					select_year = Integer.parseInt((String) listValue.get(i));
					continue;
				}
				if (listName.get(i).equals("leave_quarterly")) {// 查询的季度
					leave_quarterly = (String) listValue.get(i);
					continue;
				}
				if (listName.get(i).equals("pageNum")) {
					// pageNum = Integer.parseInt((String)listValue.get(i));//分页
					continue;
				}
				if (listName.get(i).equals("leave_start_day")) {
					leave_start_day = (String) listValue.get(i);
					continue;
				}
				if (listName.get(i).equals("leave_end_day")) {
					leave_end_day = (String) listValue.get(i);
					continue;
				}
				sql += listName.get(i) + " like '%" + listValue.get(i) + "%' and ";
			}
		}
		sql = sql.substring(0, sql.length() - 4);
		List<Map<String, Object>> liNum = JDBC.executeQuery(sql);
		findUserDao fD = new findUserDao();

		sql += " order by b.id desc";// limit "+(pageNum-1)*pageListNum+","+pageListNum;
		List<Map<String, Object>> list = JDBC.executeQuery(sql);
		// 查询后进行日期筛选(在日期范围内)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean b = false;
		boolean b2 = false;
		Date time1 = null;
		Date time2 = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				try {
					// 开始日期和结束日期之间的判断
					time1 = sdf.parse((String) map.get("leave_start_day"));
					time2 = sdf.parse((String) map.get("leave_end_day"));
					b = fD.belongCalendar(time1, sdf.parse(leave_start_day), sdf.parse(leave_end_day));
					b2 = fD.belongCalendar(time2, sdf.parse(leave_start_day), sdf.parse(leave_end_day));
					if (!b || !b2) {
						list.remove(map);
						i--;
						continue;
					}
				} catch (Exception e) {
					System.out.println("没有日期条件或日期范围判断出错findUserDao.java");
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
	        String filename="",name="查询记录导出.xls";
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
