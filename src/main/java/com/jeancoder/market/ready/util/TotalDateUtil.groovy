package com.jeancoder.market.ready.util

import java.text.SimpleDateFormat
import java.util.regex.Pattern.First

class TotalDateUtil {

	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	//设置日期格式
	
	static def get_now_time_week_one() {
		
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());//当前时间
		
		cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//周一
		String week_one = df.format(cld.getTime());
		
		return week_one;
	}
	
	static def get_now_time_week_seven() {
		Calendar cld = Calendar.getInstance(Locale.CHINA);
		cld.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
		cld.setTimeInMillis(System.currentTimeMillis());//当前时间
		
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//周日
		String week_seven = df.format(cld.getTime());
		
		return week_seven;
	}
	
	static def get_now_time_month_one() {
		Calendar cale = Calendar.getInstance(Locale.CHINA);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = df.format(cale.getTime());
		
		return firstday;
	}
	
	static def get_now_time_month_last() {
		Calendar cale = Calendar.getInstance(Locale.CHINA);
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String firstday = df.format(cale.getTime());
		println firstday;
		return firstday;
	}
	
	static def get_now_time() {
		Calendar cale = Calendar.getInstance(Locale.CHINA);
		String frstday = df.format(cale.getTime());
		
		println frstday;
		return frstday;
	}
	
}
