package com.jeancoder.market.ready.dto.wxcard;

public class CardGiftBaseReq {
	private String logo_url;
	private String code_type = "CODE_TYPE_TEXT";
	private String brand_name;
	private String title;
	private String color = "Color010";
	private String notice = "自营平台购票时使用";
	private String description = "可在收银台使用";
	private Object sku;
	private Object date_info;
	private Boolean use_custom_code = true;
//	private String get_custom_code_mode = "GET_CUSTOM_CODE_MODE_DEPOSIT";
	private Boolean bind_openid = true;
	
	String service_phone;
	
//	private String location_id_list;
//	private String use_all_locations;
	private String center_title = "立即使用";
	private String center_sub_title = "去购票";
	private String center_url;
//	private String center_app_brand_user_name;
//	private String center_app_brand_pass;
//	private String custom_url_name = "马上去购票";
//	private String custom_url_sub_title = "马上去购票";
//	private String custom_url;
//	private String custom_app_brand_user_name;
//	private String custom_app_brand_pass;
//	private String promotion_url_name = "马上去购票";
//	private String promotion_url_sub_title = "马上去购票";
//	private String promotion_url;
//	private String promotion_app_brand_user_name;
//	private String promotion_app_brand_pass;
	private Integer get_limit = 100;
	private Integer use_limit = 100;
	private Boolean can_share = false;
	private Boolean can_give_friend = true;
	
	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getSku() {
		return sku;
	}

	public void setSku(Object sku) {
		this.sku = sku;
	}

	public Object getDate_info() {
		return date_info;
	}

	public void setDate_info(Object date_info) {
		this.date_info = date_info;
	}

	public Boolean getUse_custom_code() {
		return use_custom_code;
	}

	public void setUse_custom_code(Boolean use_custom_code) {
		this.use_custom_code = use_custom_code;
	}

//	public String getGet_custom_code_mode() {
//		return get_custom_code_mode;
//	}
//
//	public void setGet_custom_code_mode(String get_custom_code_mode) {
//		this.get_custom_code_mode = get_custom_code_mode;
//	}

	public String getCenter_title() {
		return center_title;
	}

	public void setCenter_title(String center_title) {
		this.center_title = center_title;
	}

	public String getCenter_sub_title() {
		return center_sub_title;
	}

	public void setCenter_sub_title(String center_sub_title) {
		this.center_sub_title = center_sub_title;
	}

	public String getCenter_url() {
		return center_url;
	}

	public void setCenter_url(String center_url) {
		this.center_url = center_url;
	}

//	public String getCustom_url_name() {
//		return custom_url_name;
//	}
//
//	public void setCustom_url_name(String custom_url_name) {
//		this.custom_url_name = custom_url_name;
//	}
//
//	public String getCustom_url_sub_title() {
//		return custom_url_sub_title;
//	}
//
//	public void setCustom_url_sub_title(String custom_url_sub_title) {
//		this.custom_url_sub_title = custom_url_sub_title;
//	}
//
//	public String getCustom_url() {
//		return custom_url;
//	}
//
//	public void setCustom_url(String custom_url) {
//		this.custom_url = custom_url;
//	}
//
//	public String getPromotion_url_name() {
//		return promotion_url_name;
//	}
//
//	public void setPromotion_url_name(String promotion_url_name) {
//		this.promotion_url_name = promotion_url_name;
//	}
//
//	public String getPromotion_url_sub_title() {
//		return promotion_url_sub_title;
//	}
//
//	public void setPromotion_url_sub_title(String promotion_url_sub_title) {
//		this.promotion_url_sub_title = promotion_url_sub_title;
//	}
//
//	public String getPromotion_url() {
//		return promotion_url;
//	}
//
//	public void setPromotion_url(String promotion_url) {
//		this.promotion_url = promotion_url;
//	}

	public Integer getGet_limit() {
		return get_limit;
	}

	public void setGet_limit(Integer get_limit) {
		this.get_limit = get_limit;
	}

	public Integer getUse_limit() {
		return use_limit;
	}

	public void setUse_limit(Integer use_limit) {
		this.use_limit = use_limit;
	}

	public Boolean getCan_share() {
		return can_share;
	}

	public void setCan_share(Boolean can_share) {
		this.can_share = can_share;
	}

	public Boolean getCan_give_friend() {
		return can_give_friend;
	}

	public void setCan_give_friend(Boolean can_give_friend) {
		this.can_give_friend = can_give_friend;
	}

	public Boolean getBind_openid() {
		return bind_openid;
	}

	public void setBind_openid(Boolean bind_openid) {
		this.bind_openid = bind_openid;
	}

	public static CardGiftBaseReq gene(String logo_url,String brand_name,
			String title,Object sku,Object date_info,String url){
		CardGiftBaseReq base = new CardGiftBaseReq();
		base.setLogo_url(logo_url);
		base.setBrand_name(brand_name);
		base.setTitle(title);
		base.setSku(sku);
		base.setDate_info(date_info);
		base.setCenter_url(url);
//		base.setCustom_url(url);
//		base.setPromotion_url(url);
		
		return base;
	}
}
