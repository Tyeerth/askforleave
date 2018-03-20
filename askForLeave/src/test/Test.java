package test;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
	static TimerTask task = new TimerTask() {   
		public void run() {   
			//每次需要执行的代码放到这里面。   
			System.out.println("测试");
				
		}   
	}; 
	static Timer timer=null;
	public void start() {
		Calendar calendar = Calendar.getInstance();
		//增加或减少天数(负数)
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		/*** 定制每日17:00执行方法 ***/
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date date=calendar.getTime(); //第一次执行定时任务的时间
		System.out.println(date);
		timer = new Timer();
		//timer.cancel();//关闭
		timer.schedule(task,date,86400000);//每天下午5点执行任务
	}
	public void stop() {
		timer.cancel();
	}
}
