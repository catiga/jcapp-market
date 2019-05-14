package com.jeancoder.market.entry.coupon.printsetup.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.constant.PrintSetupConstants
import com.jeancoder.market.ready.entity.PrintSetup
import com.jeancoder.market.ready.util.GlobalHolder

def setup = JC.request.param('setup');

def pid = GlobalHolder.proj.id;

PrintSetup print_setup = JcTemplate.INSTANCE().get(PrintSetup, 'select * from PrintSetup where flag!=? and proj_id=? and setup_type=? order by id asc', -1, pid, PrintSetupConstants.PSTYPE_COUNPN);

def update = true;
if(print_setup==null) {
	print_setup = new PrintSetup();
	print_setup.proj_id = pid;
	print_setup.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	print_setup.flag = 0;
	print_setup.setup_type = PrintSetupConstants.PSTYPE_COUNPN;
	update = false;
}

print_setup.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
print_setup.setup_json = setup;

if(update) 
	JcTemplate.INSTANCE().update(print_setup);
else
	print_setup.id = JcTemplate.INSTANCE().save(print_setup);
	
return SimpleAjax.available('', print_setup);
