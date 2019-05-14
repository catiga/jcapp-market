package com.jeancoder.market.ready.dto.wxcard;

public class CardCashReq {
	private CardCashCardReq card;

	public CardCashCardReq getCard() {
		return card;
	}

	public void setCard(CardCashCardReq card) {
		this.card = card;
	}
	
	public static CardCashReq gene(String logo_url,String brand_name,
			String title,Object sku,Object date_info,String url,String least_cost,String reduce_cost){
		CardCashReq req = new CardCashReq();
		
		req.setCard(CardCashCardReq.gene(logo_url, brand_name, title, sku, date_info, url, least_cost, reduce_cost));
		
		return req;
	}
}
