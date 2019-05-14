package com.jeancoder.market.ready.service

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.GoodsCouponRule
import com.jeancoder.market.ready.entity.MovieCouponRule
import java.sql.Timestamp

class MovieCouponRuleServer {
	private static final JCLogger LOGGER = LoggerSource.getLogger(MovieCouponRuleServer.class);
	static final MovieCouponRuleServer INSTANSE = new MovieCouponRuleServer();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();

	/**
	 * 
	 * @param batch_id
	 * @param stores
	 * @return
	 */
	public Integer findAvaliableRule(BigInteger batch_id,String stores){
		String sql ="select * from MovieCouponRule where batch_id =? and stores = ? or stores is null";
		MovieCouponRule mcr = jcTemplate.get(MovieCouponRule.class, sql, batch_id,stores);
		if(mcr==null){
			return -1;
		}
		return 1;
	}
	
	public  MovieCouponRule getRuleByBathId(BigInteger batch_id) {
		String sql = "select  * from MovieCouponRule where batch_id=?";
		return jcTemplate.get(MovieCouponRule.class, sql, batch_id);
	}
	public Integer updateItem(MovieCouponRule movie){
		MovieCouponRule item=getRuleByBathId(movie.batch_id);
		if (item==null) {
			MovieCouponRule items=new MovieCouponRule();
			items.batch_id=movie.batch_id;
			items.stores=movie.stores;
			items.a_time=new Date();
			items.c_time=new Timestamp(new Date().getTime());;
			items.movie=movie.movie;
			items.movie_type=movie.movie_type;
			items.cinema_type=movie.cinema_type;
			items.lowest=movie.lowest;
			items.rule_value=movie.rule_value;
			items.halls = movie.halls;
			items.time_type=movie.time_type;
			items.time_value=movie.time_value;
			items.quantitys_key=movie.quantitys_key;
			items.quantitys_value=movie.quantitys_value;
			return jcTemplate.save(items);
		}
		item.stores=movie.stores;
		item.c_time=new Timestamp(new Date().getTime());;
		item.movie=movie.movie;
		item.movie_type=movie.movie_type;
		item.cinema_type=movie.cinema_type;
		item.halls = movie.halls;
		item.lowest=movie.lowest;
		item.rule_value=movie.rule_value;
		item.time_type=movie.time_type;
		item.time_value=movie.time_value;
		item.quantitys_key=movie.quantitys_key;
		item.quantitys_value=movie.quantitys_value;
		return jcTemplate.update(item);
	}
}
