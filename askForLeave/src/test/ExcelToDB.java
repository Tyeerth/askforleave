package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToDB {

private String filePath="C:\\Users\\lu\\Desktop\\info.xlsx";

public boolean insertDB(){
  
   boolean flag=true;
   Workbook workbook = null;
   try {
	   
	   //文件流指向excel文件
	   FileInputStream fin=new FileInputStream(filePath);
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
    //以下的字段一一对应数据库表的字段
    String bookName="";
    String bookAuthor="";
    String bookPublish="";
   
    for(int i=0;i<=totalRow;i++){
     row=sheet.getRow(i);
     cell=row.getCell(1);
     bookName=cell.getRichStringCellValue().toString();
     cell=row.getCell(2);
     bookAuthor=cell.getRichStringCellValue().toString();
     //格式化字符串时间
     //SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
     //bookDate=new Date((format.parse(cell.getRichStringCellValue().toString())).getTime());
    }
   } catch (Exception e) {
	   System.out.println("批量导入错误");
   }
   return flag;
  
}
public static class ExportExcel {
    public static void Export(){
        // 声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        //声明一个单子并命名
        HSSFSheet sheet = wb.createSheet();
        //给单子名称一个长度
        sheet.setDefaultColumnWidth((short)15);
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
         
               //添加一些数据，这里先写死，大家可以换成自己的集合数据
               /*List<student> list = new ArrayList<student>();
               list.add(new Student(111,张三,男));
               list.add(new Student(111,李四,男));
               list.add(new Student(111,王五,女));*/
 
               //向单元格里填充数据
              /* for (short i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(list.get(i).getId());
                row.createCell(1).setCellValue(list.get(i).getName());
                row.createCell(2).setCellValue(list.get(i).getSex());
               }*/
        String filename="";
        //系统
        String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){  
			//windows系统路径,后面用filename.lastIndexOf("\\")
			filename = "WebContent\\upload\\季度表.xls";
			System.out.println("windows系统："+filename);
		}  
		else {
			//linux系统路径
			filename = "WebContent/upload/季度表.xls";
			System.out.println("linux系统："+filename);
		}
         
        try {
            //写入upload目录中提供下载
            FileOutputStream out = new FileOutputStream(filename);
            wb.write(out);
            out.close();
            System.out.println("导出成功！");
        } catch (Exception e) {
        	System.out.println("导出失败！");
        } 
    }
}
public static void main(String args[]){
   //ExcelToDB toDB=new ExcelToDB();
  // toDB.insertDB();
	//导出
	ExportExcel.Export();
	
}
}
