package com.jeancoder.market.ready.coupon

import com.jeancoder.market.ready.entity.CouponCode

interface CouponInterface {
	/**
	 *  返回 卡劵Dto
	 * @param CouponBatch batch
	 * @param CouponCode couponCode
	 * @param rule
	 * @return CouponCodeDto
	 */
	def getCouponDto(def batch, def couponCode, def rule);
	/**
	 * 判断当前卡劵是否还在有效期
	 * @param CouponBatch batch
	 * @param CouponCode couponCode
	 * @param rule
	 * @return CouponRule
	 */
	def isAvailable(def batch, def couponCode, def rule);
	/**
	 * 判断当前订单是否满足当前卡劵
	 * @param batch
	 * @param couponCode
	 * @param rule
	 * @param order
	 * @return CouponRule
	 */
	def isOrderAvailable(def batch, def couponCode, def rule, def order);

	/**
	 * 
	 * @param couponCodes
	 * @param sid
	 * @param order
	 * @return CouponRule
	 */
	def consumeCouponPrice(List<CouponCode> couponCodes, BigInteger sid, def order, def totalAmount,def param);
}
