package com.jeancoder.market.ready.dto.wxcard;

public class CardCashBodyReq {
	private CardCashBaseReq base_info;
	private String least_cost;
	private String reduce_cost;

	public CardCashBaseReq getBase_info() {
		return base_info;
	}

	public void setBase_info(CardCashBaseReq base_info) {
		this.base_info = base_info;
	}

	public String getLeast_cost() {
		return least_cost;
	}

	public void setLeast_cost(String least_cost) {
		this.least_cost = least_cost;
	}

	public String getReduce_cost() {
		return reduce_cost;
	}

	public void setReduce_cost(String reduce_cost) {
		this.reduce_cost = reduce_cost;
	}
	
	public static CardCashBodyReq gene(String logo_url,String brand_name,
			String title,Object sku,Object date_info,String url,String least_cost,String reduce_cost){
		CardCashBodyReq body = new CardCashBodyReq();
		body.setBase_info(CardCashBaseReq.gene(logo_url, brand_name, title, sku, date_info, url));
		body.setLeast_cost(least_cost);
		body.setReduce_cost(reduce_cost);
		
		return body;
	}
}
