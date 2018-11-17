package com.leave.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.leave.service.updateUserService;

@WebServlet("/ExcelToDB")
@MultipartConfig//表单上传文件要求
public class ExcelToDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExcelToDB() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		Part part = request.getPart("file");
		//文件路径
		String name = part.getHeader("content-disposition");
		String root = request.getServletContext().getRealPath("/upload");
		String filePath = name.substring(name.lastIndexOf("."),name.length()-1);
		String filename = "";
		//系统
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){  
			//windows系统路径,后面用filename.lastIndexOf("\\")
			filename = root+"\\excel"+filePath;
			System.out.println("windows系统："+filename);
		}  
		else {
			//linux系统路径
			filename = root+"/excel"+filePath;
			System.out.println("linux系统："+filename);
		}
		
		
		part.write(filename);
		Workbook workbook = null;
		
		 List<List> list = new ArrayList();
		
		try {
			   //文件流指向excel文件
				
			   FileInputStream fin=new FileInputStream(filename);
			   String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
			   
			   if (fileType.equals("xls")) {
					workbook = new HSSFWorkbook(fin);
				} else if (fileType.equals("xlsx")) {
					workbook = new XSSFWorkbook(fin);
				} else {
					System.out.println("您输入的excel格式不正确");
				}
		   
		    //workbook=new HSSFWorkbook(fin);//创建工作薄
		    Sheet sheet=workbook.getSheetAt(0);//得到工作表
		    Row row=null;//对应excel的行
		    Cell cell=null;//对应excel的列
		   
		    int totalRow=sheet.getLastRowNum();//得到excel的总记录条数
		   
		    SimpleDateFormat dformat=new SimpleDateFormat("yyyy-MM-dd");
		    //以下的字段一一对应数据库表的字段
		    for(int i=1;i<=totalRow;i++){//行,第一行用作标题，不都读入
		    	row=sheet.getRow(i);
		    	List li = new ArrayList<>();
		    	for(int j=0; j< 13;j++) {//13列
		    		cell=row.getCell(j);
		    		
	                if(j==2 || j==3) {//日期格式
	                	li.add(dformat.format(cell.getDateCellValue()));
	                	continue;
	                }
		    		if(j==11) {//电话号码
		    			li.add(BigDecimal.valueOf(cell.getNumericCellValue()).toString());
		    			continue;
		    		}
		    		if(j==12) {//电话号码
		    			int user_max_day = (int)cell.getNumericCellValue();
		    			li.add(user_max_day);
		    			break;
		    		}
		    		li.add(cell.getStringCellValue());
		    	}
		     list.add(li);
		    }
		    //System.out.println(list);
		   } catch (Exception e) {
			   System.out.println("批量导入错误");
		}
		updateUserService up = new updateUserService();
	    boolean b = up.insertUserMore(list);
	    if(b) response.sendRedirect("user_information.html");
	    else response.getWriter().println("上传失败\t<a href=\"user_information.html\">返回</a>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
