package com.jeancoder.market.entry.coupon.batch
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailableHallTags
import com.jeancoder.market.ready.dto.CouponDic
import com.jeancoder.market.ready.dto.SelectModel
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.GoodsCouponRule
import com.jeancoder.market.ready.entity.MovieCouponRule
import com.jeancoder.market.ready.entity.OnlineServerCouponRule
import com.jeancoder.market.ready.entity.ServerCouponRule
import com.jeancoder.market.ready.entity.ServicePack
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.GoodsCouponRuleServer
import com.jeancoder.market.ready.service.MovieCouponRuleServer
import com.jeancoder.market.ready.service.OnlineServerCouponRuleService
import com.jeancoder.market.ready.service.ServerCouponRuleServer
import com.jeancoder.market.ready.service.ServicePackService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.MoneyUtil
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil


JCRequest request =RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result=new Result();
try {
	String batch_id = request.getParameter("b_id");
	if (StringUtil.isEmpty(batch_id)) {
		return result.setRedirectResource("coupon/index");
	}
	BigInteger pid =RemoteUtil.getProj().getId();

	CouponBatch batch = CouponBatchService.INSTANSE.getItem(new BigInteger(batch_id));
	MovieCouponRule movie = MovieCouponRuleServer.INSTANSE.getRuleByBathId(batch.id);
	if ("2000".equals(batch.crapp)) {
		result.addObject("available_tags",AvailableHallTags.toSelectList());
		if (movie!=null) {
			String [] rule_maps=movie.rule_value.split("/");
			if (batch.coupon_type=="1000") {
				movie.rule_value=MoneyUtil.get_yuan_from_cent(movie.rule_value);
			}
			if (batch.coupon_type=="3000") {
				if(rule_maps.length.equals(2)){
					movie.rule_value=rule_maps[0]+"/"+MoneyUtil.get_yuan_from_cent(rule_maps[1]);
				}else{
					movie.rule_value=rule_maps[0];
				}
			}
			if (batch.coupon_type=="2000") {
				if(rule_maps.length.equals(3)){
					movie.rule_value=rule_maps[0]+"/"+rule_maps[1]+"/"+MoneyUtil.get_yuan_from_cent(rule_maps[2]);
				}else{
					movie.rule_value=rule_maps[0]+"/"+rule_maps[1];
				}
			}
			if(!movie.time_value.equals(null)&&!movie.time_value.equals("")){
				movie.time_value = DateUtil.compareR_to_W(movie.time_type,movie.time_value);
			}
			result.addObject("movie", movie);
		}else{
			MovieCouponRule movie1=new MovieCouponRule();
			result.addObject("movie", movie1);
		}
	}
	if ("1000".equals(batch.crapp)) {
		GoodsCouponRule gcr = GoodsCouponRuleServer.INSTANSE.getRule_ByBathId(new BigInteger(batch_id));
		if(gcr!=null){
			String[] rule_maps = gcr.rule_value.split("/");
			StringBuffer sf = new StringBuffer();
			switch(batch.coupon_type) {
				case "1000":
					sf.append(MoneyUtil.get_yuan_from_cent(gcr.rule_value));
					break;
				case "3000":
					if(rule_maps.length.equals(2)){
						sf.append(rule_maps[0]+"/"+MoneyUtil.get_yuan_from_cent(rule_maps[1]));
					}else{
						sf.append(rule_maps[0]);
					}
					break;
				default:
					if(rule_maps.length.equals(3)){
						sf.append(rule_maps[0]+"/"+rule_maps[1]+"/"+MoneyUtil.get_yuan_from_cent(rule_maps[2]));
					}else{
						sf.append(rule_maps[0]+"/"+rule_maps[1]);
					}
					break;
			}
			gcr.rule_value = sf.toString();
			if(!gcr.time_value.equals(null)&&!gcr.time_value.equals("")){
				gcr.time_value = DateUtil.compareR_to_W(gcr.time_type,gcr.time_value);
			}
			result.addObject("gcr", gcr);
		}else{
			GoodsCouponRule gcr1 = new GoodsCouponRule();
			result.addObject("gcr", gcr1);
		}
	}
	if ("5000".equals(batch.crapp)) {
		List<ServicePack> serviceList = ServicePackService.INSTANSE.getList(pid);
		result.addObject("serviceList", serviceList);
		ServerCouponRule scRule =ServerCouponRuleServer.INSTANSE.getRuleByBathId(batch.id);
		if(null!=scRule){
			if(scRule.quantitys_key=="101"||scRule.quantitys_key=="102"){
				scRule.quantitys_value = MoneyUtil.get_yuan_from_cent(scRule.quantitys_value.split("/")[0])+"/"+scRule.quantitys_value.split("/")[1];
			}
			if(scRule.quantitys_key=="103"){
				scRule.quantitys_value = scRule.quantitys_value;
			}
			switch(batch.coupon_type){
				case "1000":
					scRule.rule_value = MoneyUtil.get_yuan_from_cent(scRule.rule_value);
					break;
				case "3000":
					if(scRule.rule_value.split("/").length.equals(2)){
						scRule.rule_value =scRule.rule_value.split("/")[0]+"/"+ MoneyUtil.get_yuan_from_cent(scRule.rule_value.split("/")[1]);
					}else{
						scRule.rule_value =scRule.rule_value.split("/")[0];
					}
					break;
				case "2000":
					if(scRule.rule_value.split("/").length.equals(3)){
						scRule.rule_value =scRule.rule_value.split("/")[0]+"/"+scRule.rule_value.split("/")[1]+"/"+ MoneyUtil.get_yuan_from_cent(scRule.rule_value.split("/")[2]);
					}else{
						scRule.rule_value =scRule.rule_value.split("/")[0]+"/"+scRule.rule_value.split("/")[1];
					}
					break;
			}
			if(!scRule.time_value.equals(null)&&!scRule.time_value.equals("")){
				scRule.time_value = DateUtil.compareR_to_W(scRule.time_type,scRule.time_value);
			}
			result.addObject("scRule", scRule);
		}else{
			ServerCouponRule scRule1 = new ServerCouponRule();
			result.addObject("scRule", scRule1);
		}
	}
	
	if ("5001".equals(batch.crapp)) {
		OnlineServerCouponRule gcr = OnlineServerCouponRuleService.INSTANSE.getRule_ByBathId(new BigInteger(batch_id));
		if(gcr!=null){
			String[] rule_maps = gcr.rule_value.split("/");
			StringBuffer sf = new StringBuffer();
			switch(batch.coupon_type) {
				case "1000":
					sf.append(MoneyUtil.get_yuan_from_cent(gcr.rule_value));
					break;
				case "3000":
					if(rule_maps.length.equals(2)){
						sf.append(rule_maps[0]+"/"+MoneyUtil.get_yuan_from_cent(rule_maps[1]));
					}else{
						sf.append(rule_maps[0]);
					}
					break;
				default:
					if(rule_maps.length.equals(3)){
						sf.append(rule_maps[0]+"/"+rule_maps[1]+"/"+MoneyUtil.get_yuan_from_cent(rule_maps[2]));
					}else{
						sf.append(rule_maps[0]+"/"+rule_maps[1]);
					}
					break;
			}
			gcr.rule_value = sf.toString();
			if(!gcr.time_value.equals(null)&&!gcr.time_value.equals("")){
				gcr.time_value = DateUtil.compareR_to_W(gcr.time_type,gcr.time_value);
			}
			result.addObject("gcr", gcr);
		}else{
			OnlineServerCouponRule gcr1 = new OnlineServerCouponRule();
			result.addObject("gcr", gcr1);
		}
	}
	//所有添加服务预约类卡券
	List<SelectModel> supp_types = new ArrayList<SelectModel>();
	supp_types.add(CouponDic.COUPON_GENERAL.toSlectModel());
	supp_types.add(CouponDic.COUPON_APPOINT.toSlectModel());
	supp_types.add(CouponDic.COUPON_TICKETS.toSlectModel());
	result.addObject("supp_types", supp_types);
	result.addObject("moneyUtil", new MoneyUtil());
	result.setView("coupon/batch/edit_"+batch.crapp).addObject("batch",batch);
	return result;
} catch (Exception e) {
	Logger.error("查询卡劵详情失败",e);
	return result.setRedirectResource("coupon/index");
}
