package com.jeancoder.market.ready.dto.wxcard;

public class CardGiftBodyReq {
	private CardGiftBaseReq base_info;
	private String gift;

	public CardGiftBaseReq getBase_info() {
		return base_info;
	}

	public void setBase_info(CardGiftBaseReq base_info) {
		this.base_info = base_info;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public static CardGiftBodyReq gene(String logo_url,String brand_name,
			String title,Object sku,Object date_info,String url,String giftName){
		CardGiftBodyReq body = new CardGiftBodyReq();
		body.setBase_info(CardGiftBaseReq.gene(logo_url, brand_name, title, sku, date_info, url));
		body.setGift(giftName);
		
		return body;
	}
}
