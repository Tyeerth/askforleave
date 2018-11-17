package com.leave.util;

import com.leave.config.Config;
import com.leave.dao.findUserDao;
import com.leave.db.JDBC;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/UserDBToExcel")
public class UserDBToExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserDBToExcel() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//导出所有用户信息
		String sql = "select * from leave_user order by id";
		List<Map<String, Object>> list = JDBC.executeQuery(sql);
		if(list != null) {

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
	        cell.setCellValue("性别");
	        cell = row.createCell(2);  
	        cell.setCellValue("出生年月");
	        cell = row.createCell(3);  
	        cell.setCellValue("参工时间");
	        cell = row.createCell(4);  
	        cell.setCellValue("本人籍贯");
	        cell = row.createCell(5);  
	        cell.setCellValue("配偶籍贯");
	        cell = row.createCell(6);  
	        cell.setCellValue("工作单位");
	        cell = row.createCell(7);  
	        cell.setCellValue("现任职务");
	        cell = row.createCell(8);  
	        cell.setCellValue("职级");
	        cell = row.createCell(9);  
	        cell.setCellValue("所在类区");
	        cell = row.createCell(10);  
	        cell.setCellValue("是否两地分居");
	        cell = row.createCell(11);  
	        cell.setCellValue("联系电话");
			cell = row.createCell(12);
			cell.setCellValue("允许休假天数");
	        for(int i = 0; i < list.size(); i++) {
	        	row = sheet.createRow(i+1);//创建第i+1行
	        	Map map = list.get(i);
	        	cell = row.createCell(0);
		        cell.setCellValue((String)map.get("user_name")); 
		        cell = row.createCell(1);  
		        cell.setCellValue((String)map.get("user_sex"));
		        cell = row.createCell(2);  
		        cell.setCellValue((String)map.get("user_born_time"));
		        cell = row.createCell(3);  
		        cell.setCellValue((String)map.get("user_work_time"));
		        cell = row.createCell(4);  
		        cell.setCellValue((String)map.get("user_origin"));
		        cell = row.createCell(5);  
		        cell.setCellValue((String)map.get("user_spouse_origin"));
		        cell = row.createCell(6);  
		        cell.setCellValue((String)map.get("user_work_address"));
		        cell = row.createCell(7);  
		        cell.setCellValue((String)map.get("user_position"));
		        cell = row.createCell(8);  
		        cell.setCellValue((String)map.get("user_position_rank"));
		        cell = row.createCell(9);  
		        cell.setCellValue((String)map.get("user_class_area"));
		        cell = row.createCell(10);  
		        cell.setCellValue((String)map.get("user_separated"));
		        cell = row.createCell(11);
				cell.setCellValue((Long)map.get("user_phone"));
				cell = row.createCell(12);
				cell.setCellValue((Integer)map.get("user_max_day"));
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
