package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.dto.KV
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketRuleMemberCardRecharge
import com.jeancoder.market.ready.service.MarketRuleService
import com.jeancoder.market.ready.util.DataUtils
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

import java.text.SimpleDateFormat
//
//@RequestMapping(value = "_3000/do_add_update", method = RequestMethod.POST)
//public @ResponseBody AvailabilityStatus _3000_do_add_update(
//		@RequestParam(value = "mc_id", required = false) Long mc_id,
//		@RequestParam(value = "mc_type") String mc_type,
//		@RequestParam(value = "mc_name") String mc_name,
//		@RequestParam(value = "mc_no", required = false) String mc_no,
//		@RequestParam(value = "mc_info", required = false) String mc_info,
//		@RequestParam(value = "mc_start_time") String mc_start_time,
//		@RequestParam(value = "mc_end_time") String mc_end_time,
//		//@RequestParam(value = "mc_user_list") Integer mc_user_list,
//		@RequestParam(value = "mc_p_streg") String mc_p_streg,
//		@RequestParam(value = "mc_l_ms", required = false) String mc_l_ms,
//		@RequestParam(value = "mc_l_ms_name", required = false) String mc_l_ms_name,
//		//@RequestParam(value = "mc_l_cs", required = false) String mc_l_cs,
//		//@RequestParam(value = "mc_l_cs_name", required = false) String mc_l_cs_name,
//		//@RequestParam(value = "mc_l_pt", required = false) String mc_l_pt,
//		@RequestParam(value = "mc_l_u") Integer mc_l_u,
//		@RequestParam(value = "mc_l_u_f") String mc_l_u_f,
//		@RequestParam(value = "mc_l_u_v", required = false) String mc_l_u_v,
//		@RequestParam(value = "mc_content", required = false) String mc_content,
//		@RequestParam(value = "mc_l_f", required = false) String mc_l_f,
//		//@RequestParam(value = "mc_l_pay", required = false) String mc_l_pay,
//		//@RequestParam(value = "mc_l_cts", required = false) String mc_l_cts,
//		//@RequestParam(value = "mc_l_cts_name", required = false) String mc_l_cts_name,
//		@RequestParam(value = "l_cal_t_n", required = false) String l_cal_t_n,
//		@RequestParam(value = "l_cal_t_a", required = false) String l_cal_t_a,
//		//@RequestParam(value = "l_cal_sw", required = false) String l_cal_sw,
//		//@RequestParam(value = "l_cal_a", required = false) String l_cal_a,
//		//@RequestParam(value = "l_cal_r", required = false) String l_cal_r,
//		//@RequestParam(value = "mc_l_ht", required = false) String mc_l_ht,
//		//@RequestParam(value = "mc_l_ht_name", required = false) String mc_l_ht_name,
//		@RequestParam(value = "low_price_settle") Integer low_price_settle,
//		@RequestParam(value = "settle_aim", required = false) String settle_aim
//		//@RequestParam(value = "clear_handle_fee_policy") Integer clear_handle_fee_policy
//	) {
//	if(!(low_price_settle.equals(1)||low_price_settle.equals(0))) {
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	
//	SysProjectInfo project = this.getSysProj();
//	MarketInfo market = null;
//	if(mc_id!=null&&mc_id>0l) {
//		market = marketService.get_market_by_id(mc_id);
//	}
//	if(market==null) {
//		market = new MarketInfo();
//		market.setaTime(new Date());
//		market.setProject(project);
//		market.setCg(0);
//		market.setMstatus("10");
//		market.setCgu(this.getAdminUser().getId());
//		mc_no = StringUtil.trim(mc_no);
//		if(StringUtil.isEmpty(mc_no)) {
//			mc_no = CPISCoderTools.generateCouponNum();
//		}
//		market.setDnum(mc_no);
//
//	} else {
//		if(!market.getProject().getId().equals(project.getId())) {
//			return AvailabilityStatus.notAvailable(JsConstants.object_belong_error);
//		}
//	}
//	Date start_time = null, end_time = null;
//	try {
//		start_time = DateUtil.parse_yyyyMMddHHmmss(mc_start_time);
//		end_time = DateUtil.parse_yyyyMMddHHmmss(mc_end_time);
//		if(start_time.after(end_time)) {
//			return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//		}
//		if(start_time==null||end_time==null) {
//			return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//		}
//	} catch(Exception e) {
//		log.error("", e);
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	
//	//开始校验各种数据
//	MarketMcJsonRule m_j_r = null;
//	try {
//		//校验两个时间
//		//校验价格策略
//		List<KV> price_stregy = MarketUtil.build_recharge_stregy(mc_p_streg);
//		//校验用户购票限制策略
//		String l_u = MarketUtil.build_lun_stregy(mc_l_u, mc_l_u_f, mc_l_u_v);
//		
//		m_j_r = new MarketMcJsonRule();
//		if(mc_l_ms!=null&&!mc_l_ms.equals("")&&!mc_l_ms.equals("-1")) {
//			List<KV> kvs = MarketUtil.build_kvs(mc_l_ms, mc_l_ms_name);
//			m_j_r.setL_ms(kvs);
//		}
//		
//		m_j_r.setL_u_n(l_u);
//		m_j_r.setPs(price_stregy);
//		
//		KV l_f = new KV();
//		l_f.setK("l_f");
//		l_f.setV(mc_l_f);
//		m_j_r.setL_f(l_f);
//		
//		m_j_r.setCal_t_n(l_cal_t_n);
//		m_j_r.setCal_t_a(MoneyUtil.get_cent_from_yuan(l_cal_t_a));
//		
//		//处理新增字段
//		m_j_r.setSettle_third(low_price_settle);
//		m_j_r.setSettle_aim(settle_aim);
//	} catch(UnvalidValueException uvex) {
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	market.setcTime(DataUtils.getCurrentTimestamp());
//	market.setEndTime(end_time);
//	market.setFlag(0);
//	market.setInfo(mc_info);
//	market.setMtype(mc_type);
//	market.setStartTime(start_time);
//	market.setTitle(mc_name);
//	market.setContent(mc_content);
//	MarketRule m_r = market.getRule();
//	if(m_r==null) {
//		m_r = new MarketRule();
//		m_r.setMarket(market);
//	}
//	m_r.setJsonRule(JackSonBeanMapper.toJson(m_j_r));
//	try {
//		marketService.do_save_update_market(market, m_r);
//		return AvailabilityStatus.available();
//	} catch(Exception e) {
//		log.error("", e);
//		return AvailabilityStatus.notAvailable(JsConstants.unknown);
//	}
//}
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
String mtype=request.getParameter("mc_type")
String mc_name=request.getParameter("mc_name");
String mc_no=request.getParameter("mc_no");//编号可手动输入，为空则自动生成
String mc_info=request.getParameter("mc_info");
String mc_p_streg=request.getParameter("mc_p_streg");
String mc_start_time=request.getParameter("mc_start_time");
String mc_end_time=request.getParameter("mc_end_time");
String mc_l_ms=request.getParameter("mc_l_ms");
String mc_l_f=request.getParameter("mc_l_f");
String mc_l_u=request.getParameter("mc_l_u");
String mc_l_u_f=request.getParameter("mc_l_u_f");
String mc_l_u_v=request.getParameter("mc_l_u_v");
String mc_content=request.getParameter("mc_content");
String l_cal_t_n=request.getParameter("l_cal_t_n");
String l_cal_t_a=request.getParameter("l_cal_t_a");
String low_price_settle=request.getParameter("low_price_settle");
String settle_aim=request.getParameter("settle_aim");
try {
	MarketRuleMemberCardRecharge market=new MarketRuleMemberCardRecharge();
	MarketInfo maketInfo=new MarketInfo();
	def pid = RemoteUtil.getProj().getId();
	if (StringUtil.isEmpty(mc_name)||StringUtil.isEmpty(mc_info)||StringUtil.isEmpty(mc_p_streg)||StringUtil.isEmpty(mc_start_time)||
		StringUtil.isEmpty(mc_end_time)||StringUtil.isEmpty(mc_l_ms)||StringUtil.isEmpty(mc_l_f)||StringUtil.isEmpty(mc_l_u)||StringUtil.isEmpty(l_cal_t_n)||StringUtil.isEmpty(l_cal_t_a)||StringUtil.isEmpty(settle_aim)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	String [] p_streg=mc_p_streg.split(";");//判断充值金额与返回金额是否为数字
	for (int i = 0; i < p_streg.length; i++) {
		String streg=p_streg[i];
		String [] sum=streg.split("/");
		println  sum[0]
		if (!(DataUtils.isNumber(sum[0])&&DataUtils.isNumber(sum[1])&&DataUtils.isNumber(sum[2]))) {
			return AvailabilityStatus.notAvailable("输入的金额必须为数字");
		}
	}
	if (!(DataUtils.isNumber(l_cal_t_n)&&DataUtils.isNumber(l_cal_t_a)&&DataUtils.isNumber(low_price_settle))) {
		return AvailabilityStatus.notAvailable("输入的参数类型不符合要求");
	}
	maketInfo.mtype=mtype;
	maketInfo.pid=pid;
	maketInfo.info=mc_info;
	maketInfo.title=mc_name;
	
	maketInfo.start_time=DateUtil.getDate((mc_start_time), "yyyy-MM-dd HH:mm:ss");//开始时间
	maketInfo.end_time=DateUtil.getDate(mc_end_time,"yyyy-MM-dd HH:mm:ss");//结束时间
	maketInfo.content=mc_content;//详细信息
	maketInfo.dnum=mc_no;//编号
	market.mc_name=mc_name;
	market.pid=pid;
	market.mc_p_streg=mc_p_streg; //活动策略
	market.mc_l_ms=mc_l_ms;//会员卡级别限制编号
	market.mc_l_f=mc_l_f;//限制首次优惠
	market.mc_l_u=mc_l_u;//充值次数限制
	market.mc_l_u_f=mc_l_u_f;//充值频率限制
	market.mc_l_u_v=mc_l_u_v;//具体充值频率值
	market.l_cal_t_n=l_cal_t_n;//充值总次数
	market.l_cal_t_a=l_cal_t_a;//充值总金额
	market.low_price_settle=low_price_settle;//是否第三方结算
	market.settle_aim=settle_aim//结算对象
	String msg=MarketRuleService.INSTANSE.save_rule_info(market, maketInfo,mtype);
	if (!StringUtil.isEmpty(msg)) {
		AvailabilityStatus.notAvailable(msg)
	}
	return AvailabilityStatus.available();
} catch (Exception e) {
	e.printStackTrace()
	return AvailabilityStatus.notAvailable("添加失败");
}




