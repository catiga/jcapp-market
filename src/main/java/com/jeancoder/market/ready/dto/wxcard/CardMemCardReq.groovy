package com.jeancoder.market.ready.dto.wxcard;

import java.util.Map;

public class CardMemCardReq {
	private String card_type = "MEMBER_CARD";
	private CardMemBodyReq member_card;
	
	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public CardMemBodyReq getMember_card() {
		return member_card;
	}

	public void setMember_card(CardMemBodyReq member_card) {
		this.member_card = member_card;
	}
	
	public static CardMemCardReq gene(
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
		CardMemCardReq ins = new CardMemCardReq();
		ins.setMember_card(CardMemBodyReq.gene(background_pic_url, prerogative,discount, logo_url, brand_name, title, color, notice, description, date_info, service_phone, custom_url_name, custom_url, custom_url_sub_title));
		return ins;
	}
}
