package com.leave.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateL {
	
		static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		public static int getYear(String date) {
			LocalDateTime lt = LocalDateTime.parse(date + " 00:00:00",dtf);
			return lt.getYear();
		}
		public static int getMonth(String date) {
			LocalDateTime lt = LocalDateTime.parse(date + " 00:00:00",dtf);
			return lt.getMonth().getValue();
		}
		public static int getDay(String date) {
			LocalDateTime lt = LocalDateTime.parse(date + " 00:00:00",dtf);
			return lt.getDayOfMonth();
		}
		//计算日期天数差
		public static int daysBetween(Date smdate,Date bdate) throws ParseException{    
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    }
		public static void main(String[] args) {
			DateTimeFormatter s = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime lt = LocalDateTime.parse("2017-11-23 00:00:00",s);
			System.out.println(lt.getYear());
		}
		
}
