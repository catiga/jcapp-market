package com.jeancoder.market.internal.wx

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.coupon.CouponFactoryUtil
import com.jeancoder.market.ready.dto.ProjectFrontConfig
import com.jeancoder.market.ready.dto.sys.SysProjectInfo
import com.jeancoder.market.ready.dto.wxcard.AddTicketCodeRes
import com.jeancoder.market.ready.dto.wxcard.CardCashReq
import com.jeancoder.market.ready.dto.wxcard.CardGiftReq
import com.jeancoder.market.ready.dto.wxcard.WxMovieTicketRes
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.service.GoodsCouponRuleServer
import com.jeancoder.market.ready.service.MovieCouponRuleServer
import com.jeancoder.market.ready.service.OnlineServerCouponRuleService
import com.jeancoder.market.ready.service.ServerCouponRuleServer
import com.jeancoder.market.ready.util.EncodeUtils
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.StringUtil
import com.jeancoder.market.ready.entity.CouponCode

import groovy.json.JsonSlurper

JCLogger logger = LoggerSource.getLogger();

//获取参数
def code_id = JC.internal.param('code_id');
def part_id = JC.internal.param('part_id');
def pid = JC.internal.param('pid');
try {
	code_id = new BigInteger(code_id + '');
	if(!part_id) {
		return SimpleAjax.notAvailable('param_error,用户信息参数错误');
	}
} catch(any) {
	return SimpleAjax.notAvailable('param_error,券码ID参数错误');
}
SimpleDateFormat _sdf_yyyyMMddHHmmss_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
try {
	//验证code
	CouponCode code = CouponService.INSTANSE.get_codes_by_id(code_id);
	if(!code) {
		return SimpleAjax.notAvailable('obj_not_found,券码未找到');
	}
	
	CouponBatch batch = CouponBatchService.INSTANSE.getById(code.batch_id);
	if(!batch) {
		return SimpleAjax.notAvailable('obj_not_found,券批次未找到或已停用');
	}
	def _create_ticket_ = "https://api.weixin.qq.com/card/create?access_token={ACCESS_TOKEN}";
	def _access_token_ = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APP_ID}&secret={APP_KEY}";
	
	//获取微信公众号
	ProjectFrontConfig supp_config = JC.internal.call(ProjectFrontConfig, 'project', '/incall/frontconfig', [app_type:'20',pid:pid]);
	if(supp_config==null||supp_config.app_id==null) {
		//以后统一的返回格式
		return SimpleAjax.notAvailable('pay_config_error,系统配置错误');
	}
	
	def app_id = supp_config.app_id;
	def app_key = supp_config.app_key;
	//获取access_token
	_access_token_ = _access_token_.replace("{APP_ID}", app_id);
	_access_token_ = _access_token_.replace("{APP_KEY}", app_key);
	def json = JC.remote.http_call(_access_token_, null);
	
	def jsonSlurper = new JsonSlurper();
	
	//获取到的是Map对象
	def map = jsonSlurper.parseText(json)
	if(!map['access_token']) {
		return SimpleAjax.notAvailable('wx_remote_error,微信获取access_token失败');
	}
	
	def access_token = map['access_token'];
	
	SysProjectInfo project = JC.internal.call(SysProjectInfo, 'project', '/incall/project_by_id', [pid:pid]);
	
	def wxcard_id = batch.wxcard_id;
	def center_url = 'http://' + project.domain + '/general_api/tcss/index';
	if(!wxcard_id) {
		//需要走创建卡券的操作
		//String logo_url = "http://pe1d.static.pdr365.com/"+project.getLogo();
		String logo_url = 'http://' + project.domain + '/img_server/' + project.logo;
		String brand_name = project.proj_name;
		String title = batch.title;
		
		//新增卡券显示数据，需要更丰富
		String service_phone = project.custom_phone;
		String sub_title = batch.info;
		String description = '不可与其他优惠同享，请在消费时向商户出示\n或在微信公众平台使用';
		
		Map<String,Object> sku = new HashMap<String,Object>();
		sku.put("quantity", 0);
		Map<String,Object> date_info = new HashMap<String,Object>();
		
		if(CouponConstants._coupon_validate_type_days_.equals(batch.validate_type)){
			//领取后多少天失效
			date_info.put("type", "DATE_TYPE_FIX_TERM");
			date_info.put("fixed_term", Double.valueOf(batch.validate_period).intValue());
		}else if(CouponConstants._coupon_validate_type_forever_.equals(batch.validate_type)){
			//永久有效
			date_info.put("type", "DATE_TYPE_FIX_TIME_RANGE");
			date_info.put("begin_timestamp", batch.a_time.getTime() / 1000);
			date_info.put("end_timestamp", _sdf_yyyyMMddHHmmss_.parse("2027-01-01 23:59:59").getTime() / 1000);
		}else if(CouponConstants._coupon_validate_type_unidied_.equals(batch.validate_type)){
			//固定失效日期
			date_info.put("type", "DATE_TYPE_FIX_TIME_RANGE");
			date_info.put("begin_timestamp", batch.a_time.getTime() / 1000);
			date_info.put("end_timestamp", _sdf_yyyyMMddHHmmss_.parse(batch.validate_period).getTime() / 1000);
		}
		String url = project.getDomain();
		String giftName = batch.title;
		String least_cost = "0";
		try {
			//least_cost = batch.getCr().getCmr().getCuvm();	//最低金额限制
		} catch (Exception e) {
		}
		
		String uri = _create_ticket_ = "https://api.weixin.qq.com/card/create?access_token={ACCESS_TOKEN}";
		uri = uri.replace("{ACCESS_TOKEN}", access_token);
		WxMovieTicketRes ticket = null;
		
		def couponCodeRule = null;
		if (CouponConstants._coupon_app_general_.equals(batch.crapp)) {
			couponCodeRule = GoodsCouponRuleServer.INSTANSE.getRule_ByBathId(batch.id);
		} else if (CouponConstants._coupon_app_ticket_.equals(batch.crapp)) {
			couponCodeRule = MovieCouponRuleServer.INSTANSE.getRuleByBathId(batch.id);
		} else if (CouponConstants._coupon_app_appoint_.equals(batch.crapp)) {
			couponCodeRule = ServerCouponRuleServer.INSTANSE.getRuleByBathId(batch.id);
		} else if (CouponConstants._coupon_online_server_.equals(batch.crapp)) {
			couponCodeRule = OnlineServerCouponRuleService.INSTANSE.getRule_ByBathId(batch.id);
		}

		def util =  CouponFactoryUtil.getCouponUtil(batch.crapp);
		def dto =  util.getCouponDto(batch, code, couponCodeRule);
		def coupon_type = dto.coupon_type;
		def rule_value = dto.rule_value;
		
		if(CouponConstants._coupon_type_exchange_.equals(batch.coupon_type)){
			def rules = rule_value.split("/");
			def code_sun  = rules[0]//  卡劵数量
			def item_sun = rules[1] // 商品或电影票数量
			def make_amount = "0" // 兑换需要补金额 单位分
			if ( rules.length == 3 && !StringUtil.isEmpty(rules[2])) {
				make_amount = rules[2];
			}
			//兑换券
			CardGiftReq reqbody = CardGiftReq.gene(logo_url, brand_name, title, sku, date_info, url, giftName);
			reqbody.getCard().getGift().getBase_info().setCenter_url(center_url);
			if(sub_title) {
				reqbody.card.gift.base_info.center_sub_title = sub_title;
			}
			if(service_phone) {
				reqbody.card.gift.base_info.service_phone = service_phone;
			}
			reqbody.card.gift.base_info.description = description;
			
			def req_json = JackSonBeanMapper.toJson(reqbody);
			logger.info('req_json:' + req_json);
			ticket = JC.remote.http_call(WxMovieTicketRes, uri, req_json);
		}else if(CouponConstants._coupon_type_voucher_.equals(batch.coupon_type)){
			def vouchere_amount = rule_value; //代金金额 单位分 
			//代金券
	//		Map<String,Object> rump = (Map<String, Object>) code.getBatch().getCr().getRuleMap().get("rule_map_map");
	//		String type = rump.get("type").toString();
			String val = vouchere_amount;
	//		for(String key : rump.keySet()){
	//			if(type.equals(key)){
	//				Map<String,Object> valmap = (Map<String, Object>) rump.get(key);
	//				val = valmap.get("kims_money").toString();
	//			}
	//		}
			CardCashReq reqbody = CardCashReq.gene(logo_url, brand_name, title, sku, date_info, url,least_cost,val);
			reqbody.getCard().getCash().getBase_info().setCenter_url(center_url);
			if(sub_title) {
				reqbody.card.cash.base_info.center_sub_title = sub_title;
			}
			if(service_phone) {
				reqbody.card.cash.base_info.service_phone = service_phone;
			}
			reqbody.card.cash.base_info.description = description;
			
			def req_json = JackSonBeanMapper.toJson(reqbody);
			logger.info('req_json:' + req_json);
			ticket = JC.remote.http_call(WxMovieTicketRes, uri, req_json);
		} else if(CouponConstants._coupon_type_discount_.equals(batch.coupon_type)){
			def rules = rule_value.split("/");
			def discount  = rules[0]// 80 则对应8折 
			def max_discount_amount = rules[1] // 最高折扣金额 单位分
		}
		logger.info('end_trans tickets:' + JackSonBeanMapper.toJson(ticket));
		if(!"ok".equals(ticket.getErrmsg())){
			//创建失败
			//return AvailabilityStatus.notAvailable(new String[]{"error"});
			return SimpleAjax.notAvailable(ticket.errcode + ',' + ticket.errmsg, ticket);
		}
		batch.wxcard_id = ticket.card_id;
		JcTemplate.INSTANCE().update(batch);	//保存券码
	}
	
	//增发一个code
	String addcodeuri = "http://api.weixin.qq.com/card/code/deposit?access_token={ACCESS_TOKEN}";
	addcodeuri = addcodeuri.replace("{ACCESS_TOKEN}", access_token);
	
	Map<String,Object> addCodeParam = new HashMap<String,Object>();
	addCodeParam.put("card_id", batch.wxcard_id);
	List<String> codes = new ArrayList<String>();
	codes.add(code.getCode());
	addCodeParam.put("code", codes);
	
	AddTicketCodeRes addcoderes = JC.remote.http_call(AddTicketCodeRes, addcodeuri, JackSonBeanMapper.toJson(addCodeParam));
	
	if(!"ok".equals(addcoderes.errmsg)){
		//增发失败
		return SimpleAjax.notAvailable(addcoderes.errcode + ',' + addcoderes.errmsg, addcoderes);
	}
	if(!addcoderes.succ_code.contains(code.code) && !addcoderes.duplicate_code.contains(code.code)){
		//增发失败
		//return AvailabilityStatus.notAvailable(new String[]{"both succ_code,duplicate_code have in '"+code.getCode()+"'"});
		return SimpleAjax.notAvailable('duplicate_code,券码重复 [' + code.getCode() + ']');
	}
	
	if(!addcoderes.getDuplicate_code().contains(code.getCode())){
		//如果没有重复添加  库存+1
		
		//获取卡券详情
		String wx_card_info_uri = "https://api.weixin.qq.com/card/get?access_token={ACCESS_TOKEN}";
		wx_card_info_uri = wx_card_info_uri.replace("{ACCESS_TOKEN}", access_token);
		
		def wx_card_info = JC.remote.http_call(wx_card_info_uri, "{\"card_id\":\""+batch.wxcard_id+"\"}");
		
		logger.info("get card info:" + wx_card_info);
		String quantity = wx_card_info.substring(wx_card_info.indexOf("\"quantity\":")+"\"quantity\":".length());
		quantity = quantity.substring(0,quantity.indexOf(","));
		
		//增发后修改库存
		String upsturi = "https://api.weixin.qq.com/card/modifystock?access_token={ACCESS_TOKEN}";
		upsturi = upsturi.replace("{ACCESS_TOKEN}", access_token);
		
		Map<String,Object> upstParam = new HashMap<String,Object>();
		upstParam.put("card_id", batch.wxcard_id);
		upstParam.put("increase_stock_value", Long.valueOf(quantity)+1);
		String upstreq = JC.remote.http_call(upsturi, JackSonBeanMapper.toJson(upstParam));
		logger.info("modify card stock:" + upstreq);
	}
	
	//获取jssdk卡券调用凭证
	def _jsapi_ticket_ = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={ACCESS_TOKEN}&type=wx_card";
	_jsapi_ticket_ = _jsapi_ticket_.replace('{ACCESS_TOKEN}', map['access_token']);
	json = JC.remote.http_call(_jsapi_ticket_, null);
	
	map = jsonSlurper.parseText(json);
	logger.info('test======' + JackSonBeanMapper.mapToJson(map));
	if(map['errcode']!='0'&&map['errcode']!=0) {
		return SimpleAjax.notAvailable('wx_remote_error:' + map['errcode'], '微信获取jsticket失败', null);
	}
	
	def api_ticket = map['ticket'];
	List<String> sort_key = new LinkedList<String>();
	String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
	Long timestamp = System.currentTimeMillis()/1000;
	
	sort_key.add(api_ticket);
	sort_key.add(part_id);
	sort_key.add(nonce_str);
	sort_key.add(""+timestamp);
	sort_key.add(batch.wxcard_id);
	sort_key.add(code.getCode());
	
	Collections.sort(sort_key);
	StringBuffer buffer = new StringBuffer();
	for(String s : sort_key) {
		buffer.append(s);
	}
	
	String sing = EncodeUtils.SHA1(buffer.toString());
	
	Map<String,Object> cardList = new HashMap<String,Object>();
	cardList.put("cardId", batch.wxcard_id);
	Map<String,Object> cardExt = new HashMap<String,Object>();
	cardExt.put("code", code.getCode());
	cardExt.put("openid", part_id);
	cardExt.put("nonce_str", nonce_str);
	cardExt.put("timestamp", ""+timestamp);
	cardExt.put("signature", sing);
	cardList.put("cardExt", cardExt);
	
	return SimpleAjax.available('', cardList);
} catch(any) {
	logger.error('wx coupon add error', any);
	return SimpleAjax.notAvailable('sys_error,系统错误请稍后再试');
}





