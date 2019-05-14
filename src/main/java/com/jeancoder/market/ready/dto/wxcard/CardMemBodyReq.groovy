package com.jeancoder.market.ready.dto.wxcard;

import java.io.Serializable;
import java.util.Map;

public class CardMemBodyReq implements Serializable{
	private static final long serialVersionUID = 2456728288368686585L;
	private CardMemBaseReq base_info;
	private String background_pic_url;//	否	string(128)	商家自定义会员卡背景图，须 先调用 上传图片接口 将背景图上传至CDN，否则报错， 卡面设计请遵循 微信会员卡自定义背景设计规范 ,像素大小控制在 1000像素*600像素以下
	private String prerogative;//	是	strin g(3072)	会员卡特权说明,限制1024汉字。
	private Boolean auto_activate = true;//	否	bool	设置为true时用户领取会员卡后系统自动将其激活，无需调用激活接口，详情见 自动激活 。
	private Boolean wx_activate = true;//	否	bool	设置为true时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效。填入该字段后仍需调用接口设置开卡项方可生效，详情见 一键开卡 。
	private Boolean supply_bonus = false;//	是	bool	显示积分，填写true或false，如填写true，积分相关字段均为必 填 若设置为true则后续不可以被关闭。
	//private String bonus_url;//	否	string(128)	设置跳转外链查看积分详情。仅适用于积分无法通过激活接口同步的情况下使用该字段。
	private Boolean supply_balance = false;//	是	bool	是否支持储值，填写true或false。如填写true，储值相关字段均为必 填 若设置为true则后续不可以被关闭。该字段须开通储值功能后方可使用， 详情见： 获取特殊权限
	//private String balance_url;//	否	string(128)	设置跳转外链查看余额详情。仅适用于余额无法通过激活接口同步的情况下使用该字段。
	//private String custom_field1;//	否	JSON结构	自定义会员信息类目，会员卡激活后显示,包含name_type (name) 和url字段
	//private String custom_field2;//	否	JSON结构	自定义会员信息类目，会员卡激活后显示，包含name_type(name)和url字段
	//private String custom_field3;//	否	JSON结构	自定义会员信息类目，会员卡激活后显示，包含name_type (name) 和url字段
	//private String name_type;//	否	string(24)	会员信息类目半自定义名称，当开发者变更这类类目信息的value值时 可以选择触发系统模板消息通知用户。 FIELD_NAME_TYPE_LEVEL 等级 FIELD_NAME_TYPE_COUPON 优惠券 FIELD_NAME_TYPE_STAMP 印花 FIELD_NAME_TYPE_DISCOUNT 折扣 FIELD_NAME_TYPE_ACHIEVEMEN 成就 FIELD_NAME_TYPE_MILEAGE 里程 FIELD_NAME_TYPE_SET_POINTS 集点 FIELD_NAME_TYPE_TIMS 次数
	//private String name;//	否	string(24)	会员信息类目自定义名称，当开发者变更这类类目信息的value值时 不会触发系统模板消息通知用户
	//private String url;//	否	string（128）	点击类目跳转外链url
	//private String bonus_cleared;//	否	string（128）	积分清零规则。
	//private String bonus_rules;//	否	string（128）	积分规则。
	//private String balance_rules;//	否	string（128）	储值说明。
	//private String activate_url;//	否	string（128）	激活会员卡的url。
	//private String activate_app_brand_user_name;//	否	string（128）	激活会原卡url对应的小程序user_name，仅可跳转该公众号绑定的小程序
	//private String activate_app_brand_pass;//	否	string（128）	激活会原卡url对应的小程序path
	//private String custom_cell1;//	否	JSON结构	自定义会员信息类目，会员卡激活后显示。
	//private String name;//	是	string(15)	入口名称。
	//private String tips;//	是	string(18)	入口右侧提示语，6个汉字内。
	//private String url;//	是	string(128)	入口跳转链接。
	//private String bonus_rule;//	否	JSON结构	积分规则 。
	//private String cost_money_unit;//	否	int	消费金额。以分为单位。
	//private String increase_bonus;//	否	int	对应增加的积分。
	//private String max_increase_bonus;//	否	int	用户单次可获取的积分上限。
	//private String init_increase_bonus;//	否	int	初始设置积分。
	//private String cost_bonus_unit;//	否	int	每使用5积分。
	//private String reduce_money;//	否	int	抵扣xx元，（这里以分为单位）
	//private String least_moneytouse_bonus;//	否	int	抵扣条件，满xx元（这里以分为单位）可用。
	//private String max_reduce_bonus;//	否	int	抵扣条件，单笔最多使用xx积分。
	private String discount;//	否	int	折扣，该会员卡享受的折扣优惠,填10就是九折。
	public CardMemBaseReq getBase_info() {
		return base_info;
	}
	public void setBase_info(CardMemBaseReq base_info) {
		this.base_info = base_info;
	}
	public String getBackground_pic_url() {
		return background_pic_url;
	}
	public void setBackground_pic_url(String background_pic_url) {
		this.background_pic_url = background_pic_url;
	}
	public String getPrerogative() {
		return prerogative;
	}
	public void setPrerogative(String prerogative) {
		this.prerogative = prerogative;
	}
	public Boolean getAuto_activate() {
		return auto_activate;
	}
	public void setAuto_activate(Boolean auto_activate) {
		this.auto_activate = auto_activate;
	}
	public Boolean getWx_activate() {
		return wx_activate;
	}
	public void setWx_activate(Boolean wx_activate) {
		this.wx_activate = wx_activate;
	}
	public Boolean getSupply_bonus() {
		return supply_bonus;
	}
	public void setSupply_bonus(Boolean supply_bonus) {
		this.supply_bonus = supply_bonus;
	}
	public Boolean getSupply_balance() {
		return supply_balance;
	}
	public void setSupply_balance(Boolean supply_balance) {
		this.supply_balance = supply_balance;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public static CardMemBodyReq gene(
			String background_pic_url
			,String prerogative
			,String discount
			//基础
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
		CardMemBodyReq ins = new CardMemBodyReq();
		ins.setBackground_pic_url(background_pic_url);
		ins.setPrerogative(prerogative);
		ins.setDiscount(discount);
		ins.setBase_info(CardMemBaseReq.gene(logo_url, brand_name, title, color, notice, description, date_info, service_phone, custom_url_name, custom_url, custom_url_sub_title));
		return ins;
	}
}
