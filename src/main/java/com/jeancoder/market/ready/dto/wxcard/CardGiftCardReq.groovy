package com.jeancoder.market.ready.dto.wxcard;

public class CardGiftCardReq {
	private String card_type = "GIFT";
	private CardGiftBodyReq gift;
	
	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public CardGiftBodyReq getGift() {
		return gift;
	}

	public void setGift(CardGiftBodyReq gift) {
		this.gift = gift;
	}

	public static CardGiftCardReq gene(String logo_url,String brand_name,
			String title,Object sku,Object date_info,String url,String giftName){
		CardGiftCardReq card = new CardGiftCardReq();
		card.setGift(CardGiftBodyReq.gene(logo_url, brand_name, title, sku, date_info, url, giftName));
		return card; 
	}
}
