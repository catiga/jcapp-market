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
	String figure_type;//前端呈现类型(00为轮播 10为弹窗 90为文字类公告)
	BigInteger pid; //归属项目
	
	//20200611新增字段
	String content;		//广告内容，rich text
	/**
	 * 00: 页面跳转
	 * 10: 点击导向商品
	 */
	String jump_type = '00';	//跳转类型，默认为链接跳转，显示content内容
	
	BigInteger jump_id;
	String jump_name;
	String jump_img;
	String jump_info;
	
}
