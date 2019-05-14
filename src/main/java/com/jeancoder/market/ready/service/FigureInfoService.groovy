package com.jeancoder.market.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.FigureInfo

class FigureInfoService {
	private static final JCLogger LOGGER = LoggerSource.getLogger(CouponService.class);
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	static final FigureInfoService INSTANSE = new FigureInfoService();
	//查询所有出所有的轮播图
	public List<FigureInfo> get_all_figure(def pid){
		String sql = "select * from FigureInfo where flag !=-1 AND pid =? order by a_time desc";
		return jcTemplate.INSTANCE.find(FigureInfo.class, sql, pid);
	}
	//根据type查询(0000为H5,1000为收银台)
	public JcPage<FigureInfo> get_figure_by_type(def type ,def pid,JcPage<FigureInfo> page){
		StringBuffer sql = new StringBuffer(" select * from FigureInfo where pid =? and flag !=-1 and type = ? order by a_time desc");
		return jcTemplate.find(FigureInfo.class, page, sql.toString(),pid,type);
	}
	public Integer save(FigureInfo info){
		info.a_time = new Date();
		info.c_time = new Timestamp(new Date().getTime());
		return jcTemplate.save(info);
	}
	public Integer update_time(FigureInfo info){
		FigureInfo item = getItem(info.id);
		item.c_time = new Timestamp(new Date().getTime());
		item.start_time = info.start_time;
		item.end_time = info.end_time;
		return jcTemplate.update(item);
	}
	public FigureInfo getItem(def id){
		String sql = "select * from FigureInfo where flag !=-1 AND id=?"
		return jcTemplate.get(FigureInfo, sql, id);
	}
	public Integer delete_by_id(def id){
		FigureInfo item = getItem(new BigInteger(id));
		item.flag = -1;
		item.c_time = new Timestamp(new Date().getTime());
		return jcTemplate.update(item);
	}
}
