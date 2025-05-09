package com.jeancoder.market.ready.service

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.MarketRuleTcss

class MarketRuleTcssService {

    public static final MarketRuleTcssService INSTANSE = new MarketRuleTcssService();
    private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();

    //根据id查询
    public MarketRuleTcss getItem(BigInteger market_id){
        String sql = "select * from MarketRuleTcss where market_id = ?";
        jcTemplate.get(MarketRuleTcss.class, sql,market_id);
    }
}
