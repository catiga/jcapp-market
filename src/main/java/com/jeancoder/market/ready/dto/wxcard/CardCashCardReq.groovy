package com.jeancoder.market.ready.dto.wxcard;

public class CardCashCardReq {
	private String card_type;
	private CardCashBodyReq cash;
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public CardCashBodyReq getCash() {
		return cash;
	}
	public void setCash(CardCashBodyReq cash) {
		this.cash = cash;
	}
	
	public static CardCashCardReq gene(String logo_url,String brand_name,
			String title,Object sku,Object date_info,String url,String least_cost,String reduce_cost){
		CardCashCardReq card = new CardCashCardReq();
		card.setCard_type("CASH");
		card.setCash(CardCashBodyReq.gene(logo_url, brand_name, title, sku, date_info, url, least_cost, reduce_cost));
		return card; 
	}
}
