package com.jeancoder.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.market.ready.dto.sys.AppFunction
import com.jeancoder.market.ready.dto.sys.SysFunction
import com.jeancoder.market.ready.util.FuncUtil
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.NativeUtil

JC.interceptor.add("project/PreInterceptor", null);
JC.interceptor.add("token/PreInterceptor", null);
JC.interceptor.add("mod/PreInterceptor", null);



def mod_g_1 = FuncUtil.build(1, '卡劵管理', null, '', 'fa-shopping-cart');
def mod_g_1_1 = FuncUtil.build(101, '卡劵列表',1, 'index', 'fa-shopping-cart',2);
def mod_g_1_2 = FuncUtil.build(102, '服务列表', 1, 'coupon/servpp/index', 'fa-shopping-cart',2);
def mod_g_1_3 = FuncUtil.build(103, '券验证', 1, 'coupon/destroy/index', 'fa-shopping-cart',2);
def mod_g_2 = FuncUtil.build(2, '营销管理', null, 'market/index', 'fa-shopping-cart');

def mod_g_3 = FuncUtil.build(3, '营销工具', null, '', 'fa-header');
def mod_g_3_1 = FuncUtil.build(301, '手机号发券', 3, 'util/coupon/bymob/index', 'fa-header',2);
def mod_g_3_2 = FuncUtil.build(302, '智能规则发券', 3, 'util/coupon/byrule/index', 'fa-header',2);

def mod_g_4 = FuncUtil.build(4, '订单列表', null, null, 'fa-shopping-cart');
def mod_g_4_1 = FuncUtil.build(401, '卡劵使用订单',4, 'order/use_order_list', 'fa-shopping-cart',2);

def mod_g_5 = FuncUtil.build(5, '轮播图设置', null, 'figure/index','fa-shopping-cart');


List<AppFunction> result = [mod_g_1,mod_g_1_1,mod_g_1_2,mod_g_1_3,mod_g_4,mod_g_4_1];
result.addAll([mod_g_2]);
result.addAll([mod_g_3, mod_g_3_1, mod_g_3_2]);
result.addAll([mod_g_5]);
 
NativeUtil.connect('project', '/incall/mod/mods', [app_code:'market', accept:URLEncoder.encode(JackSonBeanMapper.listToJson(result), 'UTF-8')]);
