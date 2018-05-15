package com.leave.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.leave.config.Config;
import com.leave.dao.printInfoDao;
import com.leave.util.DateL;

public class printInfoService {

	printInfoDao pDao = new printInfoDao();
	public String print1(String id) {
		List<Map<String, Object>> list = pDao.print(id);
		StringBuffer content = new StringBuffer("");
		if(list !=null) {
			Map map = list.get(0);
			String start_day = (String)map.get("leave_start_day");
			String end_day = (String)map.get("leave_end_day");
			content.append("<td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_name")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_work_address")+map.get("user_position")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_origin")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_separated")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("leave_leader")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("leave_kind")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("leave_day")+"</td>\r\n" + 
					"                    <td id=\"date1\" style=\" font-size: 14px;text-align: left;padding:0 10px;line-height:14px;width:140px;\"><p>"+DateL.getYear(start_day)+"年"+DateL.getMonth(start_day)+"月"+DateL.getDay(start_day)+"日至</p>"
							+ "<p>"+DateL.getYear(end_day)+"年"+DateL.getMonth(end_day)+"月"+DateL.getDay(end_day)+"日</p></td>");
		}
		return content.toString();
	}
	public String print11(String id) {
		List<Map<String, Object>> list = pDao.print(id);
		StringBuffer content = new StringBuffer("");
		if(list !=null) {
			Map map = list.get(0);
			content.append("<td style=\"padding:8px;height:20px;width:100px;text-align:center;\">事由</td>\r\n" + 
					"                    <td colspan=\"4\" style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("leave_reason")+"</td>\r\n" + 
					"                    <td colspan=\"2\" style=\"padding:8px;height:20px;width:100px;text-align:center;\">联系电话</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_phone")+"</td>");
		}
		return content.toString();
	}
	//臃肿的代码
	public String print12(String id) {
		List<List> list2 = pDao.selectInfo(id);
		StringBuffer content = new StringBuffer("");
		if(list2 !=null) {
			List<Map<String, Object>> listMap = pDao.print(id);
			if(listMap != null) {
				Map map = listMap.get(0);
				System.out.println(map.get("person_kind"));
				Calendar cal = Calendar.getInstance();
				String leave_kind = (String)map.get("leave_kind");
				String start_day = (String)map.get("leave_start_day");
				String end_day = (String)map.get("leave_end_day");
				
				if(map.get("person_kind").equals("专技工勤人员")) {
					content.append("<img  src=\"images/hengxian2.jpg\" style=\"width:800px;\"> "+
							"<p class=\"title1\" style=\"margin:5px 0;text-align: center;font-size:25px;\">准假批复</p>\r\n" + 
							"            <p class=\"no bum\" style=\"text-align:right;font-weight:bold;margin-right:70px;margin-bottom:5px;\">编号：</p>\r\n" + 
							"            <p><u>&nbsp;&nbsp;"+map.get("user_work_address")+"&nbsp;&nbsp;</u>：</p>\r\n" + 
							"            <p>&nbsp;&nbsp;经我局批准同意你单位<u>&nbsp;&nbsp;"+map.get("user_name")+"&nbsp;&nbsp;</u>同志请<u>&nbsp;&nbsp;"+leave_kind+"&nbsp;&nbsp;</u>假<u>&nbsp;&nbsp;"+map.get("leave_day")+"&nbsp;&nbsp;</u>天，"
									+ "该同志离岗时间为<u>&nbsp;&nbsp;"+DateL.getYear(start_day)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+DateL.getMonth(start_day)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+DateL.getDay(start_day)+"&nbsp;&nbsp;</u>日，假期自<u>&nbsp;&nbsp;"+DateL.getYear(start_day)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+DateL.getMonth(start_day)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+DateL.getDay(start_day)+"&nbsp;&nbsp;</u>日起至<u>&nbsp;&nbsp;"+DateL.getYear(end_day)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+DateL.getMonth(end_day)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+DateL.getDay(end_day)+"&nbsp;&nbsp;</u>日止。</p>\r\n" + 
							"            <p>&nbsp;&nbsp;特此批复！</p>\r\n" + 
							"            <div style=\"width:800px;height:100px;\">"+
							"			 <div style=\"float:left;width:500px;height:100px;\">" + 
							"			 <img  src=\"images/pizhun.jpg\"  style=\"margin-left:200px;margin-bottom:18px;\"/>" +
							"			 <img  src=\"images/erweima.jpg\" width=\"100\" height=\"100\" style=\"margin-left: 20px;\"/>" +
							"            <p style=\"display:inline;\">备注：此批复拿回单位备案<br>温馨提示:平安才是离家最近的路，销假电话0892—8332135</p>"+
							"            </div>" +
							"            <div style=\"float:right;width:300px;height:130px;\"> " +
							"			 <p style=\"margin-left:110px;margin-top:75px;\">"+Config.get("db.xtmxtitle")+"</p>\r\n" + 
							"            <p class=\"date2\" style=\"text-align: right;margin-bottom:0;margin-right:30px;\"><u>&nbsp;&nbsp;"+cal.get(Calendar.YEAR)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+(cal.get(Calendar.MONTH)+1)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+cal.get(Calendar.DAY_OF_MONTH)+"&nbsp;&nbsp;</u>日</p>\r\n" + 
							"            </div></div>");
				}
				else {
					content.append("<img  src=\"images/hengxian.jpg\" style=\"width:800px;\"> "+
							"<p class=\"title1\" style=\"margin:5px 0;text-align: center;font-size:25px;\">准假批复</p>\r\n" + 
							"            <p class=\"no bum\" style=\"text-align:right;font-weight:bold;margin-right:70px;margin-bottom:5px;\">编号：</p>\r\n" + 
							"            <p><u>&nbsp;&nbsp;"+map.get("user_work_address")+"&nbsp;&nbsp;</u>：</p>\r\n" + 
							"            <p>&nbsp;&nbsp;经我部批准同意你单位<u>&nbsp;&nbsp;"+map.get("user_name")+"&nbsp;&nbsp;</u>同志请<u>&nbsp;&nbsp;"+leave_kind+"&nbsp;&nbsp;</u>假<u>&nbsp;&nbsp;"+map.get("leave_day")+"&nbsp;&nbsp;</u>天，"
									+ "该同志离岗时间为<u>&nbsp;&nbsp;"+DateL.getYear(start_day)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+DateL.getMonth(start_day)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+DateL.getDay(start_day)+"&nbsp;&nbsp;</u>日，假期自<u>&nbsp;&nbsp;"+DateL.getYear(start_day)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+DateL.getMonth(start_day)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+DateL.getDay(start_day)+"&nbsp;&nbsp;</u>日起至<u>&nbsp;&nbsp;"+DateL.getYear(end_day)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+DateL.getMonth(end_day)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+DateL.getDay(end_day)+"&nbsp;&nbsp;</u>日止。</p>\r\n" + 
							"            <p>&nbsp;&nbsp;特此批复！</p>\r\n" + 
							"            <div style=\"width:800px;height:100px;\">"+
							"			 <div style=\"float:left;width:500px;height:100px;\">" + 
							"			 <img  src=\"images/pizhun.jpg\"  style=\"margin-left:200px;margin-bottom:18px;\"/>" +
							"			 <img  src=\"images/erweima.jpg\" width=\"100\" height=\"100\" style=\"margin-left: 20px;\"/>" +
							"            <p style=\"display:inline;\">备注：此批复拿回单位备案<br>温馨提示:平安才是离家最近的路，销假电话0892—8332135</p>"+
							"            </div>" +
							"            <div style=\"float:right;width:300px;height:130px;\"> " +
							"			 <p style=\"margin-left:110px;margin-top:75px;\">中共谢通门县委组织部</p>\r\n" + 
							"            <p class=\"date2\" style=\"text-align: right;margin-bottom:0;margin-right:30px;\"><u>&nbsp;&nbsp;"+cal.get(Calendar.YEAR)+"&nbsp;&nbsp;</u>年<u>&nbsp;&nbsp;"+(cal.get(Calendar.MONTH)+1)+"&nbsp;&nbsp;</u>月<u>&nbsp;&nbsp;"+cal.get(Calendar.DAY_OF_MONTH)+"&nbsp;&nbsp;</u>日</p>\r\n" + 
							"            </div></div>");
				}
			}
		}
		return content.toString();
	}

	public String print2(String id) {
		List<Map<String, Object>> list = pDao.print(id);
		StringBuffer content = new StringBuffer("");
		if(list !=null) {
			Map map = list.get(0);
			String start_day = (String)map.get("leave_start_day");
			String end_day = (String)map.get("leave_end_day");
			content.append("<td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_name")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_work_address")+map.get("user_position")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_origin")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_separated")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("leave_kind")+"</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("leave_day")+"</td>\r\n" + 
					"                    <td id=\"date1\" style=\" font-size: 14px;text-align: left;padding:0 10px;line-height:14px;width:140px;\"><p>"+DateL.getYear(start_day)+"年"+DateL.getMonth(start_day)+"月"+DateL.getDay(start_day)+"日至</p>"
							+ "<p>"+DateL.getYear(end_day)+"年"+DateL.getMonth(end_day)+"月"+DateL.getDay(end_day)+"日</p></td>");
		}
		return content.toString();
	}
	public String print21(String id) {
		List<Map<String, Object>> list = pDao.print(id);
		StringBuffer content = new StringBuffer("");
		if(list !=null) {
			Map map = list.get(0);
			content.append("<td style=\"padding:8px;height:20px;width:100px;text-align:center;\">事由</td>\r\n" + 
					"                    <td colspan=\"3\" style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("leave_reason")+"</td>\r\n" + 
					"                    <td colspan=\"2\" style=\"padding:8px;height:20px;width:100px;text-align:center;\">联系电话</td>\r\n" + 
					"                    <td style=\"padding:8px;height:20px;width:100px;text-align:center;\">"+map.get("user_phone")+"</td>");
		}
		return content.toString();
	}
	public String print22(String id) {
		return print12(id);
	}
	//13、23统计请假次数等数据
	public String print13(String id) {
		List<List> list = pDao.selectInfo(id);
		StringBuffer content = new StringBuffer("");
		if(list !=null) {
			List<Map<String, Object>> listMap = pDao.print(id);
			if(listMap != null) {
				Map map = listMap.get(0);
				if(map.get("person_kind").equals("科办员") || map.get("person_kind").equals("专技工勤人员")) {
					content.append("<td style=\"padding:8px;height:20px;width:100px;text-align:center;\">年度已请假情况</td>\r\n" + 
							"                    <td colspan=\"7\">\r\n" + 
							"                        <p style=\"text-align:left;margin-left:15px;\">已请事假<u>&nbsp;&nbsp;"+list.get(0)+"&nbsp;&nbsp;</u>次，累计<u>&nbsp;&nbsp;"+list.get(1)+"&nbsp;&nbsp;</u>天；已请病假<u>&nbsp;&nbsp;"+list.get(2)+"&nbsp;&nbsp;</u>次，累计<u>&nbsp;&nbsp;"+list.get(3)+"&nbsp;&nbsp;</u>天；\r\n" + 
							"                    </td>");
				}
			
				else content.append("<td style=\"padding:8px;height:20px;width:100px;text-align:center;\">年度已请假情况</td>\r\n" + 
					"                    <td colspan=\"7\">\r\n" + 
					"                        <p style=\"text-align:left;margin-left:15px;\">已请事假<u>&nbsp;&nbsp;"+list.get(0)+"&nbsp;&nbsp;</u>次，累计<u>&nbsp;&nbsp;"+list.get(1)+"&nbsp;&nbsp;</u>天；已请病假<u>&nbsp;&nbsp;"+list.get(2)+"&nbsp;&nbsp;</u>次，累计<u>&nbsp;&nbsp;"+list.get(3)+"&nbsp;&nbsp;</u>天；<br>已请轮休（分时段休假）<u>&nbsp;&nbsp;"+list.get(4)+"&nbsp;&nbsp;</u>次，累计<u>&nbsp;&nbsp;"+list.get(5)+"&nbsp;&nbsp;</u>天。</p>\r\n" + 
					"                    </td>");
			}
		}
		return content.toString();
	}
	public String print23(String id) {
		//List<List> list = pDao.selectInfo(id);
		return print13(id);
	}

}
