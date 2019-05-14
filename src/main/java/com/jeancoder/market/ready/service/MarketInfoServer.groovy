package com.jeancoder.market.ready.service

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.util.StringUtil

import groovy.sql.Sql

import java.sql.Timestamp

class MarketInfoServer {
	private static final JCLogger = LoggerSource.getLogger(MarketInfoServer.class);
	static final MarketInfoServer INSTANSE = new MarketInfoServer();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();

	public JcPage<MarketInfo> getList(BigInteger pid, JcPage<MarketInfo> page,String mstatus){
		StringBuffer sql = new StringBuffer ("select * from MarketInfo where pid =? and flag!=? ");
		if (!StringUtil.isEmpty(mstatus)) {
			sql.append("and mstatus=");
			sql.append("'"+mstatus+"'");
		}
		sql.append("order by a_time desc");
		return jcTemplate.find(MarketInfo.class, page, sql.toString(), pid,-1);
	}
	//根据id查询
	public MarketInfo getItem(BigInteger pid,BigInteger id){
		String sql = "select * from MarketInfo where pid =? and id=? ";
		return jcTemplate.get(MarketInfo.class, sql, pid,id);
	}
	public Integer updateStatus(def pid ,BigInteger id,String mstatus){
		MarketInfo market=getItem(pid, id)
		if (mstatus==MarketConstants._market_status_ing_) {
			market.mstatus=MarketConstants._market_status_ing_;
			market.c_time=new Timestamp(new Date().getTime());
			return jcTemplate.update(market);
		}
		if (mstatus==MarketConstants._market_status_pause_) {
			market.mstatus=mstatus;
			market.c_time=new Timestamp(new Date().getTime());
			return jcTemplate.update(market);
		}
		if (market.mstatus==MarketConstants._market_status_ing_||market.mstatus==MarketConstants._market_status_pause_) {
			market.mstatus=MarketConstants._market_status_finish_;
			market.c_time=new Timestamp(new Date().getTime());
			return jcTemplate.update(market);
		}
	}
	
	/**
	 * 根据类型返回可以参加的活动列表
	 * @param pid
	 * @param type
	 * @return
	 */
	public List<MarketInfo> getAvailableListByType(BigInteger pid, String type) {
		Date date = new Date();
		StringBuffer sql = new StringBuffer ("select * from MarketInfo where start_time<=? and end_time>=?  and pid =? and mstatus=? and mtype=? and flag!=? order by c_time desc");
		return jcTemplate.find(MarketInfo.class,sql.toString(),date,date,pid,MarketConstants._market_status_ing_,type,-1);
	}
}
