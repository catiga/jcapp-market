package com.jeancoder.market.ready.util

class GeneralPub {

	def ret_code;
	
	def ret_msg;
	
	def data;
	
	static def success(def data = null) {
		return new GeneralPub(ret_code:'0000', ret_msg:'success', data:data);
	}
	
	static def success(def ret_msg, def data) {
		return new GeneralPub(ret_code:'0000', ret_msg:ret_msg, data:data);
	}
	
	static def fail(def code, def msg, def data) {
		return new GeneralPub(ret_code:code, ret_msg:msg, data:data);
	}
	
	static def comfail(def msg, def data = null) {
		return new GeneralPub(ret_code:'-1', ret_msg:msg, data:data);
	}
}
