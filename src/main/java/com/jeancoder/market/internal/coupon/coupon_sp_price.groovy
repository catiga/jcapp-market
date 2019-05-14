package com.jeancoder.market.internal.coupon

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.coupon.CouponFactoryUtil
import com.jeancoder.market.ready.dto.coupon.CouponRule
import com.jeancoder.market.ready.dto.market.GoodsDto
import com.jeancoder.market.ready.dto.market.MCCompute
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.MoneyUtil
import com.jeancoder.market.ready.util.StringUtil

/*
 *商品卡劵计算 计算金额
 */
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName()+".coupon_goods_price");
def t_num =   new Date().getTime();
t_num = t_num.toString() + new Random().nextInt(1000).toString();
MCCompute mcc = new MCCompute();
try {
	// {t_num=1537079019163310,p=
	def p = JC.internal.param("p");
	def sid = JC.internal.param("sid");
	def pid = JC.internal.param("pid");
	String op = JC.internal.param("op");
	def on = JC.internal.param("on");
	def oc = JC.internal.param("oc");
 
	Logger.info('{t_num=' + t_num + ',p='+p + ',sid=' + sid + ',pid=' + pid + ',op=' + op);
	if (StringUtil.isEmpty(p)|| pid == null) {
		mcc.code = JsConstants.input_param_null;
		mcc.msg = "参数不能为空";
		return mcc;
	}
	
	if (!StringUtil.isEmpty(op) && !"use".equals(op)) {
		mcc.code = "1000";
		mcc.msg = "操作类型错误";
		return mcc;
	}
	// 转换参数
	def deto  = JackSonBeanMapper.jsonToMap(p);
	pid = new BigInteger(pid.toString());
	if (sid != null) {
		sid = new BigInteger(sid.toString());
	}
	// 参数
	String coupon_codes = deto.card_code;
	
	if (StringUtil.isEmpty(coupon_codes) || StringUtil.isEmpty(sid.toString())) {
		mcc.code = "1000";
		mcc.msg = JsConstants.input_param_null;
		return mcc;
	}
	
	// 组装商品
	//[[goods_id,tycode,cat_ids,price,num],[goods_id,tycode,cat_ids,price,num]]
	String totalAmount = "0";
	List<GoodsDto> goods_List = new ArrayList<GoodsDto>();
	def list = deto.g;
	for (def dto : list) {
		def goods_id = dto[0];
		def name = dto[1];
		def price = dto[2];
		def num = dto[3];
		Logger.info(goods_id+ "_" + name+ "_" + price+ "_" + num)
		GoodsDto good = new GoodsDto();
		good.id = goods_id.toString();
		good.price = price;
		good.total_amount = MoneyUtil.multiple(price.toString(), num.toString());
		good.mc_price = price;
		good.pay_amount = MoneyUtil.multiple(good.mc_price, num.toString());
		good.discount = "0";
		totalAmount = MoneyUtil.add(totalAmount, good.total_amount);
		goods_List.add(good);
	}
	// 查询卡劵
	List<CouponCode>  couponCodeList = CouponService.INSTANSE.get_available_codes_by_ids(coupon_codes, pid);
	if (couponCodeList == null || couponCodeList.isEmpty()) {
		mcc.code = JsConstants.unknown;
		mcc.msg = "未找到卡劵"
		return mcc;
	}
	
	Map<String,CouponBatch> couponBatch = new HashMap<String, CouponBatch>();
	StringBuffer codeIds = new StringBuffer();
	for (CouponCode couponCode : couponCodeList) {
		CouponBatch cb = couponBatch.get(couponCode.batch_id.toString());
		if (cb == null) {
			cb = CouponBatchService.INSTANSE.getById(couponCode.batch_id);
			couponBatch.put(couponCode.batch_id.toString(), cb);
		}
		if (cb == null) {
			mcc.code = JsConstants.unknown;
			mcc.msg = "未找到卡劵"
			return mcc;
		}
		if (!CouponConstants._coupon_online_server_.equals(cb.crapp)) {
			mcc.code = JsConstants.unknown;
			mcc.msg = "卡劵类型使用错误"
			return mcc;
		}
		if (codeIds.length()!=0) {
			codeIds.append(",");
		}
		codeIds.append(couponCode.id);
	}
	def util = CouponFactoryUtil.getCouponUtil(CouponConstants._coupon_online_server_);
	CouponRule couponPrice = util.consumeCouponPrice(couponCodeList, sid, deto.g,totalAmount,null);
	if (couponPrice.success) {
		mcc.code = "0";
		mcc.offerAmount = couponPrice.offerAmount; // 优惠了的价格 原价100， 应付80 ， offerAmount=20
		mcc.items = goods_List;
		mcc.totalAmount = couponPrice.totalAmount;
		if ("use".equals(op)) {
			//当前是使用操作
		   try {
			  // CouponService.INSTANSE.use_by_order(codeIds.toString());
			   CouponService.INSTANSE.use_by_order(codeIds.toString(),on,oc,pid);
		   } catch (Exception e) {
			   Logger.error("消费卡劵失败codeId=" + codeIds.toString() , e);
		   }
	   }
	} else {
		mcc.code = JsConstants.unknown;
		mcc.msg = couponPrice.msg
//		mcc.offerAmount = "0"; // 优惠了的价格 原价100， 应付80 ， offerAmount=20
//		mcc.items = goods_List;
//		mcc.totalAmount = totalAmount;
	}
	
	return  mcc;
} catch (Exception e) {
	Logger.error("卡劵计算失败", e);
	mcc.code = JsConstants.unknown;
	mcc.msg = "卡劵计算失败"
	return mcc;
} finally {
	Logger.info('优惠价计算结果{t_num= '+t_num+ ' , rules:'+ JackSonBeanMapper.toJson(mcc)+'}');
}