package com.jeancoder.market.entry.coupon.printsetup

import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.PrintSetupConstants
import com.jeancoder.market.ready.entity.PrintSetup
import com.jeancoder.market.ready.util.GlobalHolder

def pid = GlobalHolder.proj.id;

List<PrintSetup> print_setup_rules = JcTemplate.INSTANCE().find(PrintSetup, 'select * from PrintSetup where flag!=? and proj_id=? and setup_type=? order by id asc', -1, pid, PrintSetupConstants.PSTYPE_COUNPN);

PrintSetup print_setup = null;
if(print_setup_rules!=null && !print_setup_rules.empty) {
	print_setup = print_setup_rules.get(0);
}

Result view = new Result();
view.setView('coupon/printsetup/index');

view.addObject('print_setup', print_setup);

return view;
