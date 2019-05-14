package com.jeancoder.market.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import java.sql.Timestamp

@JCBean(tbname = "ma_figure_data")
class FigureInfo {

	@JCID
	BigInteger id;  // id
	BigInteger flag = 0;
	Date a_time;
	Timestamp c_time;
	String picture;// 图片
	String title;// 标题
	String info;//  简介
	String url_msg;// 链接地址
	String type;// 0000为H5端 1000为收银台
	String start_time;
	String end_time;
	String figure_type;//类型(00为轮播 10为弹窗)
	BigInteger pid; // 限制商品
}
