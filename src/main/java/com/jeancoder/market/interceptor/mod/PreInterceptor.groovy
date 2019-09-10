package com.jeancoder.market.interceptor.mod

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.market.ready.dto.sys.SysProjectInfo
import com.jeancoder.market.ready.dto.sys.AppFunction
import com.jeancoder.market.ready.dto.sys.SysFunction
import com.jeancoder.market.ready.util.FuncUtil
import com.jeancoder.market.ready.util.GlobalHolder
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.NativeUtil
import com.jeancoder.market.ready.util.RemoteUtil

@urlmapped("/")
@urlpassed(['/ypcall'])
def mod_g_1 = FuncUtil.build(1, '卡劵管理', null, '', 'fa-shopping-cart');
def mod_g_1_1 = FuncUtil.build(101, '卡劵列表',1, 'index', 'fa-shopping-cart',2);
def mod_g_1_2 = FuncUtil.build(102, '服务列表', 1, 'coupon/servpp/index', 'fa-shopping-cart',2);
def mod_g_1_3 = FuncUtil.build(103, '券验证', 1, 'coupon/destroy/index', 'fa-shopping-cart',2);
def mod_g_2 = FuncUtil.build(2, '营销管理', null, 'market/index', 'fa-shopping-cart');
def mod_g_3 = FuncUtil.build(3, '营销工具', null, '', 'fa-header');
def mod_g_3_1 = FuncUtil.build(301, '手机号发券', 3, 'util/coupon/bymob/index', 'fa-header',2);
def mod_g_3_2 = FuncUtil.build(302, '智能规则发券', 3, 'util/coupon/byrule/index', 'fa-header',2);
def mod_g_3_3 = FuncUtil.build(303, '调查问卷', 3, 'util/questionnaire/index', 'fa-header',2);
 
def mod_g_4 = FuncUtil.build(4, '订单列表', null, null, 'fa-shopping-cart');
def mod_g_4_1 = FuncUtil.build(401, '卡劵使用订单',4, 'order/use_order_list', 'fa-shopping-cart',2);

def mod_g_5 = FuncUtil.build(5, '轮播图设置', null, 'figure/index','fa-shopping-cart');

List<AppFunction> result = [mod_g_1,mod_g_1_1,mod_g_1_2,mod_g_1_3,mod_g_4,mod_g_4_1];
result.addAll([mod_g_2]);
result.addAll([mod_g_3, mod_g_3_1, mod_g_3_2, mod_g_3_3]);
result.addAll(mod_g_5);

AppFunction mod_g_main = new AppFunction();
mod_g_main.func_name = '运营支持';
mod_g_1_1.func_info = '设置卡券规则的详细信息';
mod_g_1_2.func_info = '设置服务列表的详细信息';
mod_g_1_3.func_info = '进行券码验证';
mod_g_2.func_info = '设置当前卡券的优惠活动';
mod_g_3.func_info = '营销工具';
mod_g_3_1.func_info = '根据手机号码批量发放卡券';
mod_g_3_2.func_info = '智能规则批量发放卡券';
def appFun=[];
appFun.addAll([mod_g_1, mod_g_2, mod_g_3]);
def appFunChild=[];
appFunChild.addAll([mod_g_1_1, mod_g_1_2,mod_g_1_3,mod_g_3_1, mod_g_3_2]);

JCRequest request = JC.request.get();
request.setAttribute("appMain", mod_g_main);
request.setAttribute('appFun', appFun);
request.setAttribute("appFunChild", appFunChild);
def uri = request.getRequestURI();
def context = request.getContextPath();

def uri_without_code = uri[context.length()+1..-1];
if(uri_without_code.endsWith("/"))
	uri_without_code = uri_without_code[0..-2];
request.setAttribute("__now_uri_", uri_without_code);


List<AppFunction> functions = NativeUtil.connectAsArray(AppFunction.class, 'project', '/incall/mod/mods', [pid:GlobalHolder.getProj().id,user_id:GlobalHolder.getToken().user.id,app_code:'market', accept:URLEncoder.encode(JackSonBeanMapper.listToJson(result), 'UTF-8')]);
Map<AppFunction, List<AppFunction>> my_funcs = my_funcs(functions);
request.setAttribute("user_roles_functions", my_funcs);
return true;




def get_by_id(def id, List<AppFunction> functions) {
	for(AppFunction f : functions) {
		if(f.id==id) {
			return f;
		}
	}
	return null;
}



def Map<AppFunction, List<AppFunction>> my_funcs(List<AppFunction> functions) {
	Map<AppFunction, List<AppFunction>> parent_functions = new LinkedHashMap<AppFunction, List<AppFunction>>();
	SysProjectInfo project = GlobalHolder.getProj();
	if(functions!=null&&!functions.isEmpty()) {
		for(AppFunction f : functions) {
			AppFunction parent_f = null;
			List<AppFunction> result_f = new ArrayList<AppFunction>();
			
			//只取两级的判断
			if(f.getLevel().equals(1)) {
				//表示当前这个为一级模块
				parent_f = f;
				for(AppFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			} else if(f.getLevel().equals(2)) {
				//表示当前这个为二级模块
				parent_f = get_by_id(f.getParent_id(), functions);
				if(parent_f==null) {
					continue;
				}
				for(AppFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			}
			parent_functions.put(parent_f, result_f);
		}
	}
	return parent_functions;
}