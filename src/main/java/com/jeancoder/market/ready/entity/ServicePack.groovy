package com.jeancoder.market.ready.entity

import java.sql.Timestamp
import java.util.Date

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID


@JCBean(tbname = "ma_service_pack")
class ServicePack {
	@JCID
	BigInteger id;  // id
	BigInteger pid;
	
	String spno;
	
	String spname;
	
	String spinfo;
	
	String spcontent;
	
	/**
	 * 1:到店
	 * 2:上门
	 */
	Integer spin = 1;
	
	String imgurl;
	
	/**
	 * 计费模式
	 * 10:打包计费
	 * 20:按选择项目计费
	 * 30:按选择资源计费
	 */
	String chargetype = "10";
	
	//包含基础项目的价格，基本上只用来展示
	String price;
	
	//基础服务时长
	//Integer duratime;
	/**
	 * 时长单位
	 * 1:秒
	 * 2:分
	 * 3:时
	 * 以后再叠加
	 */
	//String duraunit = "2";
	
	Integer flag = 0;
	
	Date a_time;
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	/**
	 * 10:一次服务
	 * 2*:固定频率
	 * 50:日常
	 */
	//String sptype = "10";
	
	//String plrule;
	
	//增加默认预约生成时间间隔，为空时则与基础服务时长应该相同
	//Integer timeran;
}
