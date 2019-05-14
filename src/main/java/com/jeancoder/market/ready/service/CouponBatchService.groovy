package com.jeancoder.market.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponRuleFather
import com.jeancoder.market.ready.entity.GoodsCouponRule
import com.jeancoder.market.ready.entity.MovieCouponRule
import com.jeancoder.market.ready.entity.OnlineServerCouponRule
import com.jeancoder.market.ready.entity.ServerCouponRule

class CouponBatchService {
	private static final JCLogger LOGGER = LoggerSource.getLogger(CouponBatchService.class);
	static final CouponBatchService INSTANSE = new CouponBatchService();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();

	
	/**
	 * 启用、停止优惠券
	 * @param batch_id  主键 
	 * @pid  项目标志
	 */
	public Integer switch_s(BigInteger batch_id){
		CouponBatch batch = getById(batch_id);
		if(null!=batch){
			if(batch.cbstatus==CouponConstants._coupon_batch_enable_){
				batch.cbstatus=CouponConstants._coupon_batch_stop_;
			}else if(batch.cbstatus==CouponConstants._coupon_batch_stop_||batch.cbstatus==""||batch.cbstatus==CouponConstants._coupon_batch_init_){
				batch.cbstatus=CouponConstants._coupon_batch_enable_;
			}
			return jcTemplate.update(batch);
		}
		return -1;
	}
	/**
	 * @param p_id 项目标志
	 * @param pn 当前页数 总页数
	 * @param ps 每页显示数目
	 * @return 卡批次批量返回
	 */
	public JcPage<CouponBatch> getList(BigInteger pid, JcPage<CouponBatch> page) {
		StringBuffer sql = new StringBuffer(" select * from CouponBatch where pid=? and flag!=? order by a_time desc");
		return jcTemplate.find(CouponBatch.class, page, sql.toString(),pid,-1);
	}
	public CouponBatch getById(BigInteger id){
		String sql = " select * from CouponBatch where id=? and flag!=-1";
		return jcTemplate.get(CouponBatch.class, sql,id);
	}

	/*
	 * 保存卡批次
	 * 
	 */
	public Integer saveItem(BigInteger pid,CouponBatch cb){
		CouponBatch item = new CouponBatch();
		item.coupon_no = cb.coupon_no;
		item.title = cb.title;
		item.crapp = cb.crapp;
		item.is_outer = cb.is_outer;
		item.coupon_type = cb.coupon_type;
		item.pid = pid;
		item.total = new Integer(0);
		item.available = new Integer(0);
		item.c_time = new Timestamp(new Date().getTime());
		item.flag = new Integer(0);
		item.validate_type = "0000";
		item.validate_period = "0000";
		item.use_con = "10";
		item.cbstatus = CouponConstants._coupon_batch_init_;
		item.cbunifyenable = new Integer(0);
		return jcTemplate.save(item);
	}

	public Integer updateItem(CouponBatch coupon ,MovieCouponRule movie){
		CouponBatch batch=getItem(coupon.id);
		batch.c_time=new Timestamp(new Date().getTime());
		batch.coupon_no=coupon.coupon_no;
		batch.title=coupon.title;
		batch.info=coupon.info;
		batch.img=coupon.img;
		batch.crapp=coupon.crapp;
		batch.content=coupon.content;
		batch.coupon_type=coupon.coupon_type;
		batch.validate_type=coupon.validate_type;
		batch.validate_period=coupon.validate_period;
		if (batch == null) {
			return null;
		}
		if ("1000".equals(batch.crapp)) {
			GoodsCouponRuleServer.INSTANSE.updateItem();
		}

		if ("2000".equals(batch.crapp)) {
			MovieCouponRuleServer.INSTANSE.updateItem(movie);
		}

		if ("5000".equals(batch.crapp)) {
			
		}
		return jcTemplate.update(batch);
	}
	//通过id查询卡券
	public CouponBatch get_batch_by_id(Integer id,BigInteger pid){
		String sql = "select * from CouponBatch where id=? and pid=? and flag!=?";
		jcTemplate.get(CouponBatch.class, sql, id,pid,-1);
	}


	public Integer updateItemgcr(CouponBatch coupon ,GoodsCouponRule gcr){
		CouponBatch batch = getItem(coupon.id);
		batch.c_time=new Timestamp(new Date().getTime());
		batch.coupon_no=coupon.coupon_no;
		batch.title=coupon.title;
		batch.info=coupon.info;
		batch.crapp=coupon.crapp;
		batch.content=coupon.content;
		batch.coupon_type=coupon.coupon_type;
		batch.validate_type=coupon.validate_type;
		batch.validate_period=coupon.validate_period;
		if (batch == null) {
			return null;
		}
		GoodsCouponRuleServer.INSTANSE.updateItem(gcr);
		return jcTemplate.update(batch);
	}
	public void updateItemgcr(CouponBatch coupon ,OnlineServerCouponRule gcr){
		CouponBatch batch = getItem(coupon.id);
		batch.c_time=new Timestamp(new Date().getTime());
		batch.coupon_no=coupon.coupon_no;
		batch.title=coupon.title;
		batch.info=coupon.info;
		batch.crapp=coupon.crapp;
		batch.content=coupon.content;
		batch.coupon_type=coupon.coupon_type;
		batch.validate_type=coupon.validate_type;
		batch.validate_period=coupon.validate_period;
		if (batch == null) {
			return null;
		}
		OnlineServerCouponRuleService.INSTANSE.updateItem(gcr);
		jcTemplate.update(batch);
	}
	
	

	
	public Integer updateItem(CouponBatch batch,ServerCouponRule scr){
		CouponBatch bat=getItem(batch.id);
		if(bat==null){
			return null;
		}
		bat.coupon_no = batch.coupon_no;
		bat.title = batch.title;
		bat.info = batch.title;
		bat.content = batch.content;
		bat.img = batch.img;
		bat.c_time = new Timestamp(new Date().getTime());
		CouponRuleFather crf = new CouponRuleFather();
		crf=batch.couponRule;
		bat.couponRule = crf;
		bat.coupon_type = batch.coupon_type;
		bat.validate_type = batch.validate_type;
		bat.validate_period = batch.validate_period;
		if(bat.crapp!="5000"){
			return null;
		}else{
			ServerCouponRuleServer.INSTANSE.updateItem(scr);
		}
		return jcTemplate.update(bat);
	}
	
	
	public  CouponBatch getItem(BigInteger id) {
		String sql = " select * from CouponBatch where id=? and flag!=-1 ";
		CouponBatch batch = jcTemplate.get(CouponBatch.class, sql, id);
		if (batch == null) {
			return null;
		}
		if ("1000".equals(batch.crapp)) {
			batch.couponRule = GoodsCouponRuleServer.INSTANSE.getRule_ByBathId(batch.id);
			return  batch;
		}
		if ("2000".equals(batch.crapp)) {
			batch.couponRule = MovieCouponRuleServer.INSTANSE.getRuleByBathId(batch.id);
			return  batch;
		}
		if ("5000".equals(batch.crapp)) {
		}
		return batch;
	}
	public  CouponBatch getItemByNo(String no, BigInteger pid) {
		String sql = " select * from CouponBatch where  coupon_no=? and pid=? and flag!=-1 ";
		return jcTemplate.get(CouponBatch.class, sql, no,pid)
	}
}
