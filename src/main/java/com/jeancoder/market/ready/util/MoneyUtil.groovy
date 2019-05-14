package com.jeancoder.market.ready.util

import java.math.RoundingMode

class MoneyUtil {
	private static BigDecimal bd_100 = new BigDecimal(100);
	
	public static String get_cent_from_yuan(String yuan) {
		if(yuan==null) {
			return null;
		}
		try {
			BigDecimal bd = new BigDecimal(yuan);
			return bd.multiply(bd_100).intValue() + "";
		} catch(Exception e) {
		}
		return null;
	}
	
	public static String get_yuan_from_cent(String fen) {
		if(fen==null) {
			return null;
		}
		try {
			BigDecimal bd = new BigDecimal(fen);
			return bd.divide(bd_100, 2, RoundingMode.HALF_UP).toString();
		} catch(Exception e) {
		}
		return null;
	}

	public static String add(String one, String two) {
		if(one==null&&two==null) {
			return null;
		}
		if(one==null) {
			return two;
		}
		if(two==null) {
			return one;
		}
		try {
			BigDecimal bd = new BigDecimal(one);
			BigDecimal bd_two = new BigDecimal(two);
			return bd.add(bd_two).toString();
		}catch(Exception e){
			return null;
		}
	}
	
	public static String multiple(String one, String two) {
		if(one==null||two==null) {
			return null;
		}
		if(!DataUtils.isNumber(one)||!DataUtils.isNumber(two)) {
			return null;
		}
		BigDecimal bd = new BigDecimal(one);
		BigDecimal bd_two = new BigDecimal(two);
		return bd.multiply(bd_two).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	// 除
	public static String divide(String one, String two) {
		if(one==null||two==null) {
			return null;
		}
		if(!DataUtils.isNumber(one)||!DataUtils.isNumber(two)) {
			return null;
		}
		BigDecimal bd = new BigDecimal(one);
		BigDecimal bd_2 = new BigDecimal(two);
		String s = bd.divide(bd_2, 2, BigDecimal.ROUND_HALF_DOWN).toString();
		return s;
	}
	
	/**
	 * 去掉尾数
	 * @param amount
	 * @return
	 */
	public static def outEndZero (final String amount) {
		if (amount.indexOf(".") < 0) {
			return amount;
		}
		String amountStr = amount;
		while (amountStr.length() >1){
			if (amountStr.charAt(amountStr.length()-1) == '0') {
				amountStr = amountStr.substring(0, amountStr.length()-1);
				continue;
			}
			if (amountStr.charAt(amountStr.length()-1)== '.') {
				amountStr = amountStr.substring(0, amountStr.length()-1);
				return amountStr;
			}
			return amountStr;
		}
		return amountStr;
	}
	
	// 四舍五入取整
	public static String rounding(String one) {
		BigDecimal bd = new  BigDecimal(one);
		return bd.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 向下取整
	 * @param one
	 * @return
	 */
	public static String roundingDown(String one) {
		BigDecimal bd = new  BigDecimal(one);
		return bd.setScale(0,BigDecimal.ROUND_DOWN).toString();
	}
	
	public static void  main(String[] agr) {
		//def a = rounding(divide("2000","34"));
		println roundingDown("1000.5");
//		BigDecimal b = new BigDecimal("2345.0000000");
//		println b == 2345;
//		println multiple("5","10");
//		String a = "134.45";
//		print a.substring(0, 5);
//		String amountStr = "00000"
//		amountStr = amountStr.substring(0, amountStr.length());
//		println roundingr("11100000");
//		println "结束"
//		print amountStr.charAt(index)
	}
}
