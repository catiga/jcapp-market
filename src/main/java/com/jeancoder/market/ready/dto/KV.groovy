package com.jeancoder.market.ready.dto

class KV {
	private String k;
	
	/**
	 * 这里需要统一编码
	 * 目前v需要支持的格式类型包括
	 * 10 			代表10元
	 * 00:10    	代表不限制2d3d，10元
	 * 2d:10		代表2d，10元
	 * 2d01:10		代表2d影片，imax类型，10元
	 */
	private String v;
	
	private String i;
	
	private String j;
	
	
	
	public MoviePrice getMp() {
		try {
			MoviePrice mp = new MoviePrice();
			if(v.indexOf(":")==-1) {
				//说明只有价格
				mp.setPrice(v);
			} else {
				String[] arr = v.split(":");
				mp.setPrice(arr[1]);
				//开始处理头部
				String head_str = arr[0];
				if(head_str.length()==2) {
					mp.setMov_cat(head_str);
				} else {
					mp.setMov_cat(head_str.substring(0, 2));
					mp.setScreen(head_str.substring(2, 4));
					mp.setOther(head_str.substring(4));
				}
			}
			return mp;
		}catch(Exception e) {
			return null;
		}
	}
	
	public static void main(String[] argc) {
//		KV kv = new KV();
//		kv.setV("10");
//		kv.setV("2d:10");
//		kv.setV("2d01:10");
//		kv.setV("2d010000:10");
//		MoviePrice mp = kv.getMp();
//		System.out.println(mp);
	}
}
