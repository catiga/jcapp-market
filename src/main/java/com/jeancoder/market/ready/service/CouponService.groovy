package com.jeancoder.market.ready.service

import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.dto.coupon.CouponRule
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.entity.GoodsCouponRule
import com.jeancoder.market.ready.entity.MovieCouponRule
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.ShortStrUtil
import com.jeancoder.market.ready.util.StringUtil

import groovy.transform.ConditionalInterrupt

class CouponService {

	private static final JCLogger LOGGER = LoggerSource.getLogger(CouponService.class);
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	static final CouponService INSTANSE = new CouponService();
	/************************************************查询卡劵开始*********************************************************/
	
	/**
	 * @Param id
	 */
	public CouponCode get_codes_by_id(BigInteger id){
		String sql ="select * from CouponCode where id=?";
		return jcTemplate.get(CouponCode.class, sql, id,);
	}
	
	
	public List<CouponCode> get_codes_by_ids(String ids, def pid) {
		String sql = "select cc.* from CouponBatch cb, CouponCode cc  where cb.pid=?  and cb.flag!=?  and cb.id = cc.batch_id and  cc.id in ("+ids+") and cc.flag!=?";
		return jcTemplate.find(CouponCode.class, sql,pid,-1,-1);
	}
	/**
	 * 通过手机号查询可用的卡劵列表
	 * @param mobile  手机号
	 * @return
	 */
	public List<CouponCode> get_available_codes_by_mobile(String mobile, BigInteger pid){
		String sql = "select cc.* from CouponBatch cb, CouponCode cc where cb.pid=? and cb.cbstatus=? and cb.flag !=? and cb.id = cc.batch_id and cc.flag !=? and cc.mobile=? and cc.status =?";
		return jcTemplate.find(CouponCode.class, sql, pid, CouponConstants._coupon_batch_enable_, -1, -1 , mobile, CouponConstants._coupon_code_status_touse_);
	}
	
	/**
	 * 通过券码查询可用的卡劵列表
	 * @param coupon_code  券码
	 * @return
	 */
	public List<CouponCode> get_coupon_by_code(String coupon_code, BigInteger pid){
		String sql = "select cc.* from CouponBatch cb, CouponCode cc where cb.pid=? and cb.cbstatus=? and cb.flag !=? and cb.id = cc.batch_id and cc.flag !=? and cc.code=? and cc.status =?";
		return jcTemplate.find(CouponCode.class, sql, pid, CouponConstants._coupon_batch_enable_, -1, -1 , coupon_code, CouponConstants._coupon_code_status_available_);	//这里要取待分配的
	}
	
	/**
	 * 通过手机号查询可用的卡劵列表和ap_id联合查询
	 * @param mobile  手机号
	 * @param ap_id   
	 * @return
	 */
	public List<CouponCode> get_available_codes_by_mobile(String mobile, def ap_id, BigInteger pid){
		String sql = "select cc.* from CouponBatch cb, CouponCode cc where cb.pid=? and cb.cbstatus=? and cb.flag !=? and cb.id = cc.batch_id and cc.flag !=? and ( cc.mobile=? or cc.account_project_id=? ) and cc.status =?";
		return jcTemplate.find(CouponCode.class, sql, pid, CouponConstants._coupon_batch_enable_, -1, -1 , mobile, ap_id, CouponConstants._coupon_code_status_touse_);
	}

	/**
	 * 根据id 字符串查询卡劵列表
	 * @param ids id,id,id
	 * @param pid
	 * @return
	 */
	public List<CouponCode> get_available_codes_by_ids(String ids, def pid) {
		String sql = "select cc.* from CouponBatch cb, CouponCode cc  where cb.pid=? and cb.cbstatus=? and cb.flag!=?  and cb.id = cc.batch_id and  cc.id in ("+ids+") and ( cc.status=? or cc.status=? ) and cc.flag!=?";
		return jcTemplate.find(CouponCode.class, sql,pid,CouponConstants._coupon_batch_enable_,-1, CouponConstants._coupon_code_status_available_,CouponConstants._coupon_code_status_touse_,-1);
	}
	
	public JcPage<CouponCode> getList(BigInteger b_id,JcPage<CouponCode> page,String status){
		String sql ="select * from CouponCode where batch_id=? and flag=? and status=?";
		return jcTemplate.find(CouponCode.class, page, sql, b_id,0,status);
	}
	
	/************************************************查询卡劵结束*********************************************************/
	
	
	public void update_code(CouponCode code) {
		code.c_time =  new Timestamp(new Date().getTime());
		jcTemplate.update(code);
	}

	//卡券发放，即绑定手机号
	public List<BigInteger>  give_out_coupons(BigInteger b_id,List<String> mobiles,BigInteger pid){
		CouponBatch cb = CouponBatchService.INSTANSE.get_batch_by_id(b_id,pid);
		if("".equals(cb)||(-1).equals(cb.flag)){
			return
		}
		if(mobiles.size()>cb.available){
			return
		}
		JcPage<CouponCode> page = new JcPage<CouponCode>();
		page.setPn(1);
		page.setPs(mobiles.size());

		page = getList(cb.id, page, CouponConstants._coupon_code_status_available_);
		if(mobiles.size()>page.result.size()){
			return
		}
		BigInteger need_decrease =0;
		def success_sent_ids = [];
		for(int i=0;i<mobiles.size();i++){
			CouponCode cc = page.result.get(i);
			cc.mobile = mobiles.get(i);
			cc.status = CouponConstants._coupon_code_status_touse_;
			cc.source = CouponConstants._coupon_code_source_send_;
			cc.get_time = new Date();
			jcTemplate.update(cc);
			need_decrease++;
			success_sent_ids.add(cc.id);
		}
		cb.available = cb.available-need_decrease;
		jcTemplate.update(cb);
		return success_sent_ids;
	}

	/**
	 * 批量发放卡劵
	 * @param list
	 * @param mobiles
	 * @param pid
	 */
	public void  give_out_coupons(def list, String mobiles,BigInteger pid){
		for (def item : list) {
			CouponBatch cb = CouponBatchService.INSTANSE.getItemByNo(item[0], pid);
			if (cb == null) {
				continue;
			}
			List<String> mobiles_list = new ArrayList<String>();
			for (int i = 0; i < item[1]; i++) {
				mobiles_list.add(mobiles);
			}
			give_out_coupons(cb.id, mobiles_list, pid);
		}
	}
	
	//返回卡券详情
	 public JcPage<CouponCode> getList(def key, BigInteger b_id,JcPage<CouponCode> page){
		 String sql ="select * from CouponCode where batch_id=? and flag!=? ";
		 if (!StringUtil.isEmpty(key)) {
			 sql += "and (code ='"+key +"' or mobile='"+key+"')";
		 }
		 return jcTemplate.find(CouponCode.class, page, sql, b_id, -1);
	 } 

	//增量生成
	public void incre_coupon_batch(CouponBatch batch, Integer num){
		if(batch!=null && num>0){
			batch.total = batch.total+num;
			batch.available = batch.available+num;
			jcTemplate.update(batch);
			for(int i=0;i<num;i++){
				double random = Math.random();
				String [] ret = ShortStrUtil.shortUrl((System.currentTimeMillis() + batch.getId() + (int) (random * 100000)) + "");
				CouponCode code = new CouponCode();
				code.batch_id = batch.id;
				code.code = ret[0];
				code.c_time = new Timestamp(new Date().getTime());
				code.a_time = new Timestamp(new Date().getTime());
				code.flag=0;
				code.status = CouponConstants._coupon_code_status_available_;
				code.validate_type = batch.validate_type;
				code.validate_period = batch.validate_period;
				jcTemplate.save(code);
			}
		}
	}

	public List<CouponCode> get_account_coupon_nums(BigInteger pid, String mobile) {
		String hql = "select cc.* from  CouponCode cc, CouponBatch cb  where cc.flag!=? and cc.mobile=? and cc.batch_id = cb.id and cb.pid=?";
		return jcTemplate.find(CouponCode.class,hql, -1, mobile, pid);
	}
	/**********************************************卡劵过滤***********************************************************/
	
	/**********************************************卡劵使用开始***********************************************************/
	public void use_by_order (String codeId) {
		String[] coupon_code_arr = codeId.split(",");
		for(String code_str : coupon_code_arr){
			CouponCode code = get_codes_by_id(new BigInteger(code_str));
			if(code == null) {
				continue;
			}
			code.c_time = new Timestamp(new Date().getTime());
			code.status =  CouponConstants._coupon_code_status_used_;
			code.use_time = new Date();
			jcTemplate.update(code);
		}
	}
	public void use_by_order (String codeId,String order_no, String oc, BigInteger pid) {
		String[] coupon_code_arr = codeId.split(",");
		List<CouponCode> list = new ArrayList<CouponCode>();
		for(String code_str : coupon_code_arr){
			CouponCode code = get_codes_by_id(new BigInteger(code_str));
			if(code == null) {
				continue;
			}
			code.c_time = new Timestamp(new Date().getTime());
			code.status =  CouponConstants._coupon_code_status_used_;
			code.use_time = new Date();
			jcTemplate.update(code);
			list.add(code);
		}
		OrderCouponServer.INSTANSE.create_order_counpon(list,order_no,oc,pid);
	}
	/**********************************************卡劵使用结束***********************************************************/
	

}