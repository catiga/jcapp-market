package com.jeancoder.market.ready.dto.wxcard;

public class AddTicketCodeRes {
	private int errcode;
	private String errmsg;
	private List<String> succ_code;
	private List<String> duplicate_code;
	private List<String> fail_code;
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public List<String> getSucc_code() {
		return succ_code;
	}
	public void setSucc_code(List<String> succ_code) {
		this.succ_code = succ_code;
	}
	public List<String> getDuplicate_code() {
		return duplicate_code;
	}
	public void setDuplicate_code(List<String> duplicate_code) {
		this.duplicate_code = duplicate_code;
	}
	public List<String> getFail_code() {
		return fail_code;
	}
	public void setFail_code(List<String> fail_code) {
		this.fail_code = fail_code;
	}
}
