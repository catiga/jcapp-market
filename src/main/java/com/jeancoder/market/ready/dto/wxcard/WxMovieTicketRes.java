package com.jeancoder.market.ready.dto.wxcard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WxMovieTicketRes implements Serializable {
	private int errcode;
	private String errmsg;
	private String card_id;
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
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
}
