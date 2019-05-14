package com.jeancoder.market.ready.util

class NumberUtil {
	
	public static String getSerialNumber() {
		return  DateUtil.getCurrentStr("yyyy-MM-dd-HH:mm:ss:SSS") + new Random().nextInt(1000).toString();
	}
	
	public static void main (String[] arg) {
		println DateUtil.getCurrentStr("yyyy-MM-dd-HH:mm:ss:SSS ");
	}
}
