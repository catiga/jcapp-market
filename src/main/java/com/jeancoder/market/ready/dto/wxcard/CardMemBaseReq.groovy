package com.jeancoder.market.ready.dto.wxcard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardMemBaseReq implements Serializable{
	private static final long serialVersionUID = 8014361925492081801L;
	private String logo_url;//	 是	string(128)	卡券的商户logo，建议像素为300*300。
	private String code_type = "CODE_TYPE_TEXT"	;//是	string(16)	Code展示类型， "CODE_TYPE_TEXT" 文本 "CODE_TYPE_BARCODE" 一维码 "CODE_TYPE_QRCODE" 二维码 "CODE_TYPE_ONLY_QRCODE" 仅显示二维码 "CODE_TYPE_ONLY_BARCODE" 仅显示一维码 "CODE_TYPE_NONE" 不显示任何码型
	//private String pay_info = "";//	否	JSON	支付功能结构体，swipe_card结构
	//private String swipe_card = "";//	否	JSON	刷卡功能结构体，包含is_swipe_card字段
	//private Boolean is_swipe_card = false;//	否	bool	是否设置该会员卡支持拉出微信支付刷卡界面
	//private Boolean is_pay_and_qrcode = false;//否	bool	是否设置该会员卡中部的按钮同时支持微信支付刷卡和会员卡二维码
	private String brand_name;//	是	string	商户名字,字数上限为12个汉字。
	private String title	;//是	string	卡券名，字数上限为9个汉字 (建议涵盖卡券属性、服务及金额)。
	private String color	;//是	string	券颜色。按色彩规范标注填写Color010-Color100
	private String notice;//	是	string	卡券使用提醒，字数上限为16个汉字。
	private String description;//	是	string	卡券使用说明，字数上限为1024个汉字。
	private Object sku;//	是	JSON	商品信息。
	//private Integer quantity = 1;//	是	int	卡券库存的数量，不支持填写0，上限为100000000。
	private Object date_info;//	是	JSON	使用日期，有效期的信息。
	private Boolean use_custom_code = true;// 	否	bool	是否自定义Code码。填写true或false，默认为false 通常自有优惠码系统的开发者选择自定义Code码，详情见 是否自定义code
	private Boolean bind_openid = false;//	否	bool	是否指定用户领取，填写true或false。默认为false
	private String service_phone;//	否	string（24）	客服电话
	private List<Integer> location_id_list = new ArrayList<Integer>();//	否	array	门店位置ID。调用 POI门店管理接口 获取门店位置ID。
	private Boolean use_all_locations = true;//	否	bool	会员卡是否支持全部门店，填写后商户门店更新时会自动同步至卡券
	//private String center_title;//	否	string（18）	卡券中部居中的按钮，仅在卡券激活后且可用状态 时显示
	//private String center_sub_title;// 	否	string（24）	显示在入口下方的提示语 ， 仅在卡券激活后且可用状态时显示
	//private String center_url;//	否	string（128）	顶部居中的url ，仅在卡券激活后且可用状态时显示
	private String custom_url_name = "";// 	否	string（15）	自定义跳转外链的入口名字。
	private String custom_url = "";//	否	string（128）	自定义跳转的URL。
	private String custom_url_sub_title = "";// 	否	string（18）	显示在入口右侧的提示语。
	//private String promotion_url_name;//	否	string（15）	营销场景的自定义入口名称。
	//private String promotion_url;//	否	string（128）	入口跳转外链的地址链接。
	//private String promotion_url_sub_title;//	否	string（18）	显示在营销入口右侧的提示语。
	private Integer get_limit = 1;//	否	int	每人可领券的数量限制，建议会员卡每人限领一张
	private Boolean can_share = false;//	否	bool	卡券领取页面是否可分享，默认为true
	private Boolean can_give_friend = false;//	否	bool	卡券是否可转赠,默认为true
	private Boolean need_push_on_view = false;//	否	bool	填写true为用户点击进入会员卡时推送事件，默认为false。详情见 进入会员卡事件推送
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
//	public String getPay_info() {
//		return pay_info;
//	}
//	public void setPay_info(String pay_info) {
//		this.pay_info = pay_info;
//	}
//	public String getSwipe_card() {
//		return swipe_card;
//	}
//	public void setSwipe_card(String swipe_card) {
//		this.swipe_card = swipe_card;
//	}
//	public Boolean getIs_swipe_card() {
//		return is_swipe_card;
//	}
//	public void setIs_swipe_card(Boolean is_swipe_card) {
//		this.is_swipe_card = is_swipe_card;
//	}
//	public Boolean getIs_pay_and_qrcode() {
//		return is_pay_and_qrcode;
//	}
//	public void setIs_pay_and_qrcode(Boolean is_pay_and_qrcode) {
//		this.is_pay_and_qrcode = is_pay_and_qrcode;
//	}
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
//	public Integer getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
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
	public Boolean getBind_openid() {
		return bind_openid;
	}
	public void setBind_openid(Boolean bind_openid) {
		this.bind_openid = bind_openid;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	public List<Integer> getLocation_id_list() {
		return location_id_list;
	}
	public void setLocation_id_list(List<Integer> location_id_list) {
		this.location_id_list = location_id_list;
	}
	public Boolean getUse_all_locations() {
		return use_all_locations;
	}
	public void setUse_all_locations(Boolean use_all_locations) {
		this.use_all_locations = use_all_locations;
	}
	public String getCustom_url_name() {
		return custom_url_name;
	}
	public void setCustom_url_name(String custom_url_name) {
		this.custom_url_name = custom_url_name;
	}
	public String getCustom_url() {
		return custom_url;
	}
	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}
	public String getCustom_url_sub_title() {
		return custom_url_sub_title;
	}
	public void setCustom_url_sub_title(String custom_url_sub_title) {
		this.custom_url_sub_title = custom_url_sub_title;
	}
	public Integer getGet_limit() {
		return get_limit;
	}
	public void setGet_limit(Integer get_limit) {
		this.get_limit = get_limit;
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
	public Boolean getNeed_push_on_view() {
		return need_push_on_view;
	}
	public void setNeed_push_on_view(Boolean need_push_on_view) {
		this.need_push_on_view = need_push_on_view;
	}
	
	public static CardMemBaseReq gene(
			String logo_url
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
		CardMemBaseReq ins = new CardMemBaseReq();
		ins.setLogo_url(logo_url);
		ins.setBrand_name(brand_name);
		ins.setTitle(title);
		ins.setColor(color);
		ins.setNotice(notice);
		ins.setDescription(description);
		ins.setDate_info(date_info);
		ins.setService_phone(service_phone);
		ins.setCustom_url_name(custom_url_name);
		ins.setCustom_url(custom_url_sub_title);
		ins.setCustom_url_sub_title(custom_url_sub_title);
		Map<String,Object> sku = new HashMap<String,Object>();
		sku.put("quantity", 1);
		ins.setSku(sku);
		return ins;
	}
}
