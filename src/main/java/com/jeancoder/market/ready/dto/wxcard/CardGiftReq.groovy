package com.jeancoder.market.ready.dto.wxcard;

public class CardGiftReq {
	private CardGiftCardReq card;

	public CardGiftCardReq getCard() {
		return card;
	}

	public void setCard(CardGiftCardReq card) {
		this.card = card;
	}
	
	public static CardGiftReq gene(String logo_url,String brand_name,
			String title,Object sku,Object date_info,String url,String giftName){
		CardGiftReq req = new CardGiftReq();
		
		req.setCard(CardGiftCardReq.gene(logo_url, brand_name, title, sku, date_info, url, giftName));
		
		return req;
	}
}
