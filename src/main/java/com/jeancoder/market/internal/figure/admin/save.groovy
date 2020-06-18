package com.jeancoder.market.internal.figure.admin

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.FigureInfo

def id = JC.internal.param('id');	//广告id
def pid = JC.internal.param('pid');
def picture = JC.internal.param('picture');
def title = JC.internal.param('title');
def info = JC.internal.param('info');

def url_msg = JC.internal.param('url_msg');
def type = JC.internal.param('type');

def start_time = JC.internal.param('start_time');
def end_time = JC.internal.param('end_time');

def figure_type = JC.internal.param('figure_type');

def content = JC.internal.param('content');

def jump_type = JC.internal.param('jump_type');
def jump_id = JC.internal.param('jump_id');
def jump_name = JC.internal.param('jump_name');
def jump_img = JC.internal.param('jump_img');
def jump_info = JC.internal.param('jump_info');

try {
	pid = new BigInteger(pid.toString());
} catch(any) {
	pid = null;
}
if(!pid) {
	return SimpleAjax.notAvailable('pid_param_error');
}

def update = true;
FigureInfo ad = null;
if(id && id!='0' && id!=0) {
	ad = JcTemplate.INSTANCE().get(FigureInfo, 'select * from FigureInfo where flag!=? and pid=? and id=?', -1, pid, id);
	if(ad==null) {
		return SimpleAjax.notAvailable('obj_not_found');
	}
} else {
	ad = new FigureInfo();
	ad.pid = pid;
	ad.flag = 0;
	ad.a_time = new Date();
	update = false;
}

if(picture)
	ad.picture = picture;

if(title)
	ad.title = title;

if(info)
	ad.info = info;

if(url_msg)
	ad.url_msg = url_msg;

if(type)
	ad.type = type;

if(start_time)
	ad.start_time = start_time;

if(end_time)
	ad.end_time = end_time;

if(figure_type)
	ad.figure_type = figure_type;

if(content)
	ad.content = content;

if(jump_type)
	ad.jump_type = jump_type;

if(jump_id) {
	try {
		ad.jump_id = jump_id;
	}catch(any) {
		ad.jump_id = 0;
	}
}

if(jump_name)
	ad.jump_name = jump_name;

if(jump_img)
	ad.jump_img = jump_img;

if(jump_info)
	ad.jump_info = jump_info;

if(update) {
	JcTemplate.INSTANCE().update(ad);
} else {
	JcTemplate.INSTANCE().save(ad);
}

return SimpleAjax.available('', ad);


