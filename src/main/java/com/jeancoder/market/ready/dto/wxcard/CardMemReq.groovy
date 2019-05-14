package com.jeancoder.market.ready.dto.wxcard;

import com.jeancoder.market.ready.util.JackSonBeanMapper

public class CardMemReq {
	private CardMemCardReq card;

	public CardMemCardReq getCard() {
		return card;
	}

	public void setCard(CardMemCardReq card) {
		this.card = card;
	}
	
	public static CardMemReq gene(
			//卡
			String background_pic_url
			,String prerogative
			,String discount
			//卡券基础
			,String logo_url
			,String brand_name
			,String title
			,String color
			,String notice
			,String description
			,Map<String,Object> date_info
			,String service_phone
			,String custom_url_name
			,String custom_url
			,String custom_url_sub_title
		) {
		CardMemReq ins = new CardMemReq();
		ins.setCard(CardMemCardReq.gene(background_pic_url, prerogative,discount, logo_url, brand_name, title, color, notice, description, date_info, service_phone, custom_url_name, custom_url, custom_url_sub_title));
		return ins;
	}
	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("map", "");
		CardMemReq ins = CardMemReq.gene(
				"background_pic_url"
				, "prerogative"
				, "discount"
				, "logo_url"
				, "brand_name"
				, "title"
				, "color"
				, "notice"
				, "description"
				, map
				, "service_phone"
				, "custom_url_name"
				, "custom_url"
				, "custom_url_sub_title"
			); 
		System.out.println(JackSonBeanMapper.toJson(ins));
	}
}
