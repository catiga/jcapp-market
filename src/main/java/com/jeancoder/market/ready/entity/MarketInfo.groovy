package com.jeancoder.market.ready.entity

import java.sql.Timestamp
import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn
import com.jeancoder.market.ready.dto.MovieRuleRechargeDto

@JCBean(tbname = "ma_market_info")
class MarketInfo {
	@JCID
	BigInteger id;  // id
	
	String dnum; //编号
	
	//只有当cg为1时候才有效
	String dgid;
	

	BigInteger pid;
	
	String title;// 标题
	
	String info;// 一句话描述
	
	String content;// n内容
	
	String mtype;// 类型    
	
	Date a_time;
	
	String mstatus;//  状态
	
	Date start_time;
	
	Date end_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	//默认0为项目创建；1为集团创建
	Integer cg = 0;
	
	//创建人id
	Long cgu;
	
	Integer selectmode;
	
	BigInteger mru_id;
	
	@JCNotColumn
	def rule;
	@JCNotColumn
	def mc_start_time;
	@JCNotColumn
	def mc_end_time;
	@JCNotColumn
	def resultList;
}
