package com.jeancoder.market.internal.coupon

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.entity.CouponCode

JCLogger logger = JCLoggerFactory.getLogger("");

def coupon_code = JC.internal.param('coupon_code');
def ap_id = JC.internal.param('ap_id');
def mobile = JC.internal.param('mobile');
def pid = JC.internal.param('pid');
def oc = JC.internal.param("oc");
if (!oc) {
	oc = "2000";
}

if(!coupon_code) {
	return SimpleAjax.notAvailable('code_param_empty,券码参数为空');
}

try {
	ap_id = new BigInteger(ap_id.toString());
} catch(any) {
	logger.error("ap_id error", any)
	return SimpleAjax.notAvailable('ap_id_error,请检查登录账户');
}

def sql = 'select * from CouponCode where flag!=? and code=? and batch_id in ( select id from CouponBatch where flag!=? and pid=? and coupon_type=?)';

List<CouponCode> result = JcTemplate.INSTANCE().find(CouponCode, sql, -1, coupon_code, -1, pid, oc);
if(!result || result.empty) {
	return SimpleAjax.notAvailable('code_not_found,券码未找到');
}

CouponCode cc = null;
for(x in result) {
	if(x.status==CouponConstants._coupon_code_status_available_) {
		cc = x;
		break;
	}
}
if(!cc) {
	return SimpleAjax.notAvailable('code_belong_error,券码已被绑定');
}

cc.mobile = mobile;
cc.account_project_id = ap_id.toLong();
cc.status = CouponConstants._coupon_code_status_touse_;
JcTemplate.INSTANCE().update(cc);

return SimpleAjax.available();



