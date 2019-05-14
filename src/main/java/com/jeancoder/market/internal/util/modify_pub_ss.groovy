package com.jeancoder.market.internal.util

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.CbPubInfo

def pubid = JC.internal.param('pubid');

def pub = JcTemplate.INSTANCE().get(CbPubInfo, 'select * from CbPubInfo where id=? and flag!=?', pubid, -1);

def ss = JC.internal.param('ss');

pub.ss = ss;
JcTemplate.INSTANCE().update(pub);
