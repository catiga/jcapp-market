package com.jeancoder.market.ready.util

import com.jeancoder.market.ready.dto.sys.AppFunction
import com.jeancoder.market.ready.dto.sys.SysFunction

class FuncUtil {

	public static AppFunction build(def id, def name, def parent, def url, def icon = 'fa-cubese', def lev = 1) {
		AppFunction fun = new AppFunction();
		fun.id = id;
		fun.func_name = name;
		fun.parent_id = parent;
		fun.click_url = url;
		fun.func_type = '1000';
		fun.level = lev;
		fun.func_ss = icon;
		return fun;
	}
}
