package com.jeancoder.market.internal.coupon

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.dto.ProjectFrontConfig
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode

import groovy.json.JsonSlurper

JCLogger logger = LoggerSource.getLogger('process_use_coupon:');

def order_id = JC.internal.param('order_id');
def order_no = JC.internal.param('order_no');
def order_oc = JC.internal.param('oc');
def pid = JC.internal.param('pid');

logger.info('process_use_coupon:order_id=' + order_id + ', order_no=' + order_no + ', oc=' + order_oc);

ProjectFrontConfig supp_config = JC.internal.call(ProjectFrontConfig, 'project', '/incall/frontconfig', [app_type:'20',pid:pid]);
if(supp_config==null||supp_config.app_id==null) {
	//以后统一的返回格式
	return;
}

def app_id = supp_config.app_id;
def app_key = supp_config.app_key;
//获取access_token
def _access_token_ = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APP_ID}&secret={APP_KEY}";
_access_token_ = _access_token_.replace("{APP_ID}", app_id);
_access_token_ = _access_token_.replace("{APP_KEY}", app_key);
def json = JC.remote.http_call(_access_token_, null);

logger.info('process_use_coupon_wx_token:' + json);

def jsonSlurper = new JsonSlurper();

//获取到的是Map对象
def map = jsonSlurper.parseText(json)
if(!map['access_token']) {
	return;
}

def wx_card_consume_uri = "https://api.weixin.qq.com/card/code/consume?access_token={ACCESS_TOKEN}";
wx_card_consume_uri = wx_card_consume_uri.replace('{ACCESS_TOKEN}', map['access_token']);

try {
	//查询是否有券
	def sql = 'select * from CouponCode where id in ( select cbid from OrderCoupon where flag!=? and pid=? and order_no=? and oc=?)';
	List<CouponCode> coupon_codes = JcTemplate.INSTANCE().find(CouponCode, sql, -1, pid, order_no, order_oc);
	if(coupon_codes && !coupon_codes.empty) {
		for(x in coupon_codes) {
			//查找对应的批次
			CouponBatch batch = JcTemplate.INSTANCE().get(CouponBatch, 'select * from CouponBatch where id=?', x.batch_id);
			if(batch && batch.wxcard_id) {
				def notify_wx_pub = "{\"code\": \""+x.code+"\",\"card_id\":\""+batch.wxcard_id+"\"}";
				JC.remote.http_call(wx_card_consume_uri, notify_wx_pub);
			}
		}
	}
}catch(any) {
	logger.error('process_use_coupon error:', any);
}

