package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketJsonRuleWithMC
import com.jeancoder.market.ready.service.MarketRuleService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil

//@RequestMapping(value = "_2200/do_add_update", method = RequestMethod.POST)
//public @ResponseBody AvailabilityStatus _2200_do_add_update(
//		@RequestParam(value = "mc_id", required = false) Long mc_id,
//		@RequestParam(value = "mc_type") String mc_type,
//		@RequestParam(value = "mc_name") String mc_name,
//		@RequestParam(value = "mc_no", required = false) String mc_no,
//		@RequestParam(value = "mc_info", required = false) String mc_info,
//		@RequestParam(value = "mc_start_time") String mc_start_time,
//		@RequestParam(value = "mc_end_time") String mc_end_time,
//		@RequestParam(value = "mc_user_list") Integer mc_user_list,
//		@RequestParam(value = "mc_p_streg") String mc_p_streg,
//		@RequestParam(value = "mc_l_ms", required = false) String mc_l_ms,
//		@RequestParam(value = "mc_l_ms_name", required = false) String mc_l_ms_name,
//		@RequestParam(value = "mc_l_cs", required = false) String mc_l_cs,
//		@RequestParam(value = "mc_l_cs_name", required = false) String mc_l_cs_name,
//		@RequestParam(value = "mc_l_pt", required = false) String mc_l_pt,
//		@RequestParam(value = "mc_l_u") Integer mc_l_u,
//		@RequestParam(value = "mc_l_u_f") String mc_l_u_f,
//		@RequestParam(value = "mc_l_u_v", required = false) String mc_l_u_v,
//		@RequestParam(value = "mc_content", required = false) String mc_content,
//		@RequestParam(value = "mc_l_f", required = false) String mc_l_f,
//		@RequestParam(value = "mc_l_pay", required = false) String mc_l_pay,
//		@RequestParam(value = "mc_l_cts", required = false) String mc_l_cts,
//		@RequestParam(value = "mc_l_cts_name", required = false) String mc_l_cts_name,
//		@RequestParam(value = "l_cal_t_n", required = false) String l_cal_t_n,
//		@RequestParam(value = "l_cal_t_nw", required = false) String l_cal_t_nw,
//		@RequestParam(value = "l_cal_t_a", required = false) String l_cal_t_a,
//		@RequestParam(value = "l_cal_sw", required = false) String l_cal_sw,
//		@RequestParam(value = "l_cal_a", required = false) String l_cal_a,
//		@RequestParam(value = "l_cal_r", required = false) String l_cal_r,
//		@RequestParam(value = "mc_l_ht", required = false) String mc_l_ht,
//		@RequestParam(value = "mc_l_ht_name", required = false) String mc_l_ht_name,
//		@RequestParam(value = "low_price_settle") Integer low_price_settle,
//		@RequestParam(value = "settle_aim", required = false) String settle_aim,
//		@RequestParam(value = "clear_handle_fee_policy") Integer clear_handle_fee_policy,
//		@RequestParam(value = "l_plays", required = false) String l_plays,
//		@RequestParam(value = "l_playe", required = false) String l_playe,
//		@RequestParam(value = "mc_l_cwro", required = false) String mc_l_cwro,
//		@RequestParam(value = "l_pds", required = false) String l_pds,
//		@RequestParam(value = "l_pde", required = false) String l_pde,
//		@RequestParam(value = "mc_list", required = false) String mc_list
//	) {
//	if(!(low_price_settle.equals(1)||low_price_settle.equals(0))) {
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	if(!(clear_handle_fee_policy.equals(0)||clear_handle_fee_policy.equals(1))) {
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	
//	SysProjectInfo project = this.getSysProj();
//	MarketInfo market = null;
//	if(mc_id!=null&&mc_id>0l) {
//		market = marketService.get_market_by_id(mc_id);
//	}
//	
//	if(market==null) {
//		market = new MarketInfo();
//		market.setaTime(new Date());
//		market.setProject(project);
//		market.setMstatus("10");
//		market.setCg(0);
//		market.setCgu(this.getAdminUser().getId());
//		
//		mc_no = StringUtil.trim(mc_no);
//		if(StringUtil.isEmpty(mc_no)) {
//			mc_no = CPISCoderTools.generateCouponNum();
//		}
//		market.setDnum(mc_no);
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
//	MarketJsonRuleWithMC m_j_r = null;
//	try {
//		//校验两个时间
//		//校验价格策略
//		List<KV> price_stregy = MarketUtil.build_price_stregy(mc_p_streg);
//		//校验用户购票限制策略
//		String l_u = MarketUtil.build_lun_stregy(mc_l_u, mc_l_u_f, mc_l_u_v);
//		//开始校验支付方式
//		String l_pt = MarketUtil.build_lpt_stregy(mc_l_pt);
//		
//		m_j_r = new MarketJsonRuleWithMC();
//		if(mc_l_cs!=null&&!mc_l_cs.equals("")) {
//			List<KV> kvs = MarketUtil.build_kvs(mc_l_cs, mc_l_cs_name);
//			if(kvs!=null)
//				m_j_r.setL_cs(kvs);
//		}
//		if(mc_l_ms!=null&&!mc_l_ms.equals("")) {
//			List<KV> kvs = MarketUtil.build_kvs(mc_l_ms, mc_l_ms_name);
//			m_j_r.setL_ms(kvs);
//		}
//		if(mc_l_cts!=null&&!"".equals(mc_l_cts)){
//			List<KV> kvs = MarketUtil.build_kvs(mc_l_cts, mc_l_cts_name);
//			m_j_r.setL_cts(kvs);
//		}
//		if(mc_l_ht!=null&&!"".equals(mc_l_ht)){
//			List<KV> kvs = MarketUtil.build_kvs(mc_l_ht, mc_l_ht_name);
//			m_j_r.setL_ht(kvs);
//		}
//		if(l_pt!=null&&!l_pt.equals("")&&!l_pt.trim().equals("-1")) {
//			Set<SysProjectSupportConfig> supp_configs = project.getSupPayTypes();
//			List<KV> kvs = new ArrayList<KV>();
//			String[] arr_lpt = l_pt.split(",");
//			for(String k : arr_lpt) {
//				String v = null;
//				try {
//					for(SysProjectSupportConfig ssc : supp_configs) {
//						if(ssc.getScCode().equals(k)) {
//							v = ssc.getScName();
//						}
//					}
//				}catch(Exception e){
//				}
//				if(v!=null) {
//					KV kv = new KV();
//					kv.setK(k);
//					kv.setV(v);
//					kvs.add(kv);
//				}
//			}
//			if(!kvs.isEmpty()) {
//				m_j_r.setL_pts(kvs);
//			}
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
//		KV cal_sw = new KV();
//		cal_sw.setK(l_cal_sw.split(",")[0]);
//		cal_sw.setV(l_cal_sw.split(",")[1]);
//		m_j_r.setCal_sw(cal_sw);
//		
//		m_j_r.setCal_t_n(l_cal_t_n);
//		m_j_r.setCal_t_nw(l_cal_t_nw);
//		m_j_r.setCal_t_a(l_cal_t_a);
//		if(l_cal_a != null && !"".equals(l_cal_a)){
//			l_cal_a = new DecimalFormat("#").format(Double.valueOf(l_cal_a)*100);
//		}
//		m_j_r.setCal_a(l_cal_a);
//		m_j_r.setCal_r(l_cal_r);
//		
//		m_j_r.setL_pds(l_pds);
//		m_j_r.setL_pde(l_pde);
//		
//		KV l_pay = new KV();
//		l_pay.setK("l_pay");
//		l_pay.setV(mc_l_pay);
//		m_j_r.setL_pay(l_pay);
//		
//		//处理新增字段
//		m_j_r.setLow_price_settle(low_price_settle);
//		m_j_r.setSettle_aim(settle_aim);
//		m_j_r.setClear_handle_fee_policy(clear_handle_fee_policy);
//		
//		m_j_r.setL_plays(l_plays);
//		m_j_r.setL_playe(l_playe);
//		
//		m_j_r.setL_cwro(mc_l_cwro);
//		
//		//处理会员卡选择
//		if(mc_list!=null) {
//			List<KV> mc_list_longs = new ArrayList<KV>();
//			try {
//				String[] arr_mc_list = mc_list.split(",");
//				for(String s : arr_mc_list) {
//					MemberCardHierarchy mch = mcService.get_mch_by_id(Long.valueOf(s));
//					if(mch!=null) {
//						KV mch_kv = new KV();
//						mch_kv.setK(mch.getId().toString());
//						mch_kv.setV(mch.gethName());
//						mch_kv.setI(mch.getMcRule().getTitle());
//						mc_list_longs.add(mch_kv);
//					}
//				}
//			} catch(Exception e) {
//				log.info("",e);
//			}
//			if(mc_list_longs!=null&&!mc_list_longs.isEmpty()) {
//				m_j_r.setMc_list(mc_list_longs);
//			}
//		}
//	} catch(UnvalidValueException uvex) {
//		uvex.printStackTrace();
//		log.error("", uvex);
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	
//	market.setcTime(DataUtils.getCurrentTimestamp());
//	market.setEndTime(end_time);
//	market.setFlag(0);
//	market.setInfo(mc_info);
//	market.setMtype(mc_type);
//	market.setStartTime(start_time);
//	market.setTitle(mc_name);
//	
//	market.setSelectmode(0);
//	
//	market.setContent(mc_content);
//	MarketRule m_r = market.getRule();
//	if(m_r==null) {
//		m_r = new MarketRule();
//		m_r.setMarket(market);
//	}
//	m_r.setInul(mc_user_list);
//	m_r.setJsonRule(JackSonBeanMapper.toJson(m_j_r));
//	
//	
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
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String mc_type = request.getParameter("mc_type");//1
	String mc_name = request.getParameter("mc_name");//活动名称 1
	String mc_no = request.getParameter("mc_no");//编号1
	String mc_info = request.getParameter("mc_info");//活动描述1
	String mc_p_streg = request.getParameter("mc_p_streg");//价格策略
	String mc_start_time = request.getParameter("mc_start_time");//活动开始时间1
	String mc_end_time = request.getParameter("mc_end_time");//活动结束时间1
	String mc_user_list = request.getParameter("mc_user_list");//是否限制用户列表参加
	String mc_l_ms = request.getParameter("mc_l_ms");//限制影片value 1
	String mc_l_cs = request.getParameter("mc_l_cs");//限制影城value 1
	String mc_l_pt = request.getParameter("mc_l_pt");//限制支付方式1
	String mc_l_u = request.getParameter("mc_l_u");//具体购票频率值
	String mc_l_u_f = request.getParameter("mc_l_u_f");//购票频率限制
	String mc_l_u_v = request.getParameter("mc_l_u_v");//具体购票频率值
	String mc_content = request.getParameter("mc_content");//营销活动详细信息1
	String mc_l_f = request.getParameter("mc_l_f");//限制首单优惠1
	String mc_l_pay = request.getParameter("mc_l_pay");//限制支付账户1
	String mc_l_cts = request.getParameter("mc_l_cts");//限制城市 1
	String l_cal_t_n = request.getParameter("l_cal_t_n");//投放限制张数1
	String l_cal_t_a = request.getParameter("l_cal_t_a");//投放金额1
	String l_cal_sw = request.getParameter("l_cal_sw");//最低票价票补设置 1
	String l_cal_a = request.getParameter("l_cal_a");//结算金额1
	String l_cal_r = request.getParameter("l_cal_r");//第三方票补信息1
	String mc_l_ht = request.getParameter("mc_l_ht");//限制影厅value  1
	String low_price_settle = request.getParameter("low_price_settle");//是否最低票价结算1
	String settle_aim = request.getParameter("settle_aim");//结算对象 1
	String clear_handle_fee_policy = request.getParameter("clear_handle_fee_policy");//手续费政策1
	String l_cal_t_nw = request.getParameter("l_cal_t_nw");//投票限制模式1
	String l_pds = request.getParameter("l_pds");//放映开始日期1
	String l_pde = request.getParameter("l_pde");//放映结束日期1
	String l_plays = request.getParameter("l_plays");//放映时间段开始1
	String l_playe = request.getParameter("l_playe");//放映时间段结束1
	String mc_l_cwro = request.getParameter("mc_l_cwro");//是否锁座1
	String mc_list = request.getParameter("mc_list");//会员卡等级  1
	BigInteger pid = RemoteUtil.getProj().getId();
	//参数处理
	if(!(low_price_settle.equals("1")||low_price_settle.equals("0"))) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	if(!(clear_handle_fee_policy.equals("0")||clear_handle_fee_policy.equals("1"))) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	Date start_time = null, end_time = null;
	
	start_time = DateUtil.getDate(mc_start_time,"yyyy-MM-dd HH:mm:ss");
	end_time = DateUtil.getDate(mc_end_time,"yyyy-MM-dd HH:mm:ss");
	if(start_time.after(end_time)) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	if(start_time==null||end_time==null) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	
	MarketInfo mkInfo = new MarketInfo();
	mkInfo.mtype = mc_type;
	mkInfo.title = mc_name;
	mkInfo.dnum = mc_no==""?generate_coupon_no(pid):mc_no;
	mkInfo.info = mc_info;
	mkInfo.start_time = start_time;
	mkInfo.end_time = end_time;
	mkInfo.content = mc_content;
	mkInfo.pid = pid;
	//
	MarketJsonRuleWithMC mjrwc = new MarketJsonRuleWithMC();
	mjrwc.mc_p_streg = mc_p_streg;
	mjrwc.mc_user_list = mc_user_list;
	mjrwc.l_ms = mc_l_ms;
	mjrwc.l_cs = mc_l_cs;
	mjrwc.l_pts = mc_l_pt;
	mjrwc.mc_l_u= mc_l_u;
	mjrwc.mc_l_u_f = mc_l_u_f;
	mjrwc.mc_l_u_v = mc_l_u_v;
	mjrwc.l_f = mc_l_f;
	mjrwc.l_pay = mc_l_pay;
	mjrwc.l_cts = mc_l_cts;
	mjrwc.cal_t_n = l_cal_t_n;
	mjrwc.cal_t_a = l_cal_t_a;
	mjrwc.cal_sw = l_cal_sw;
	mjrwc.cal_a = l_cal_a;
	mjrwc.cal_r = l_cal_r;
	mjrwc.l_ht = mc_l_ht;
	mjrwc.low_price_settle = low_price_settle;
	mjrwc.settle_aim = settle_aim;
	mjrwc.clear_handle_fee_policy = clear_handle_fee_policy;
	mjrwc.cal_t_nw =l_cal_t_nw;
	mjrwc.l_pds = l_pds;
	mjrwc.l_pde = l_pde;
	mjrwc.l_plays = l_plays;
	mjrwc.l_playe = l_playe;
	mjrwc.l_cwro = mc_l_cwro;
	mjrwc.mc_list = mc_list;
	
	//保存__更新营销活动
	Integer nums = MarketRuleService.INSTANSE.saveItem(mkInfo, mjrwc);
	if(nums>0){
		result.setData(AvailabilityStatus.available());
		return result;
	}
}catch(Exception e){
	logger.error("",e.toString());
	return result.setData(AvailabilityStatus.notAvailable("新建或者更新营销活动失败"));
}

String generate_coupon_no(def pid) {
	String buffer = "yx" + pid.toString() + DateUtil.format_yyyyMMdd(new Date()) + this.nextInt(100, 999);
	buffer = buffer.replaceAll("\\-", "");
	return buffer;
}

int nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
