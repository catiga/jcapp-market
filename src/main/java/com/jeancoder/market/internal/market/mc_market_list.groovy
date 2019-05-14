package com.jeancoder.market.internal.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.service.MarketInfoServer

def pid = JC.internal.param('pid');

List<MarketInfo> list = MarketInfoServer.INSTANSE.getAvailableListByType(new BigInteger(pid), MarketConstants._market_type_recharge_gift_);

return SimpleAjax.available('', list);