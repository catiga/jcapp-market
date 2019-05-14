package com.jeancoder.market.entry.figure

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.FigureInfo
import com.jeancoder.market.ready.service.FigureInfoService
import com.jeancoder.market.ready.util.GlobalHolder
import com.jeancoder.market.ready.util.StringUtil

import java.text.SimpleDateFormat

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	String type = request.getParameter("type");
	BigInteger pid = GlobalHolder.getProj().getId();
	if (type.equals('H5')) {
		//H5轮播图
		type = '0000';
	}else if(type.equals('counter')){
		//收银台轮播图
		type = '1000';
	}
	//分页查询处理
	if(StringUtil.isEmpty(pn_s)){
		pn_s = "1";
	}
	if(StringUtil.isEmpty(ps_s)){
		ps_s="10";
	}

	Integer pn = Integer.parseInt(pn_s);
	if(pn<1){
		pn=1;
	}

	Integer ps = Integer.parseInt(ps_s);

	JcPage<FigureInfo> page = new JcPage<FigureInfo>();
	page.setPn(pn);
	page.setPs(ps);

	//返回结果
	page = FigureInfoService.INSTANSE.get_figure_by_type(type, pid, page);
	if (page.result==null) {
		page.result = new ArrayList<FigureInfo>();
	}
	def img_root = GlobalHolder.getProj()?'//' + GlobalHolder.getProj().domain + '/img_server':'//';
	result.addObject('root_path', img_root);
	result.addObject("page", page)
	result.addObject("type", type)
	return result.setView('figure/list')
} catch (Exception e) {
	logger.error("",e.toString());
	return result.setData(AvailabilityStatus.notAvailable("查询轮播图失败"));
}