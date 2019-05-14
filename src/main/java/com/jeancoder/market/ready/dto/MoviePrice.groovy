package com.jeancoder.market.ready.dto

class MoviePrice {
	private String price;
	
	private String mov_cat = "00";
	
	private String screen = "00";
	
	private String other = "0000";

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMov_cat() {
		return mov_cat;
	}

	public void setMov_cat(String mov_cat) {
		this.mov_cat = mov_cat;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
}
