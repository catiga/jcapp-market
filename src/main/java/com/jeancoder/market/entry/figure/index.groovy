package com.jeancoder.market.entry.figure

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.FigureInfo
import com.jeancoder.market.ready.service.FigureInfoService
import com.jeancoder.market.ready.util.GlobalHolder
import com.jeancoder.market.ready.util.JackSonBeanMapper


Result result = new Result();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
BigInteger pid = GlobalHolder.getProj().getId();
try {
	//得到所有的轮播图
	List<FigureInfo> info = FigureInfoService.INSTANSE.get_all_figure(pid);
	//根据type 将轮播图分类 0000为H5 1000为收银台
	List<FigureInfo> H5_info = new ArrayList<FigureInfo>();
	List<FigureInfo> counter_info = new ArrayList<FigureInfo>();
	if (info!=null) {
		Integer i = 0;
		Integer j = 0;
		for(FigureInfo item : info){
			if (item.type.equals("0000")) {//H5
				if (i >= 5) {//目前在后台展示前5张
					continue;
				}
				H5_info.add(item);
				i++;
			}else if(item.type.equals("1000")){//收银台
				if (j >= 5) {
					continue;
				}
				counter_info.add(item);
				j++;

			}
		}
	}
	//将第一条记录挑出来
	FigureInfo first_h5_info = new FigureInfo();
	FigureInfo first_counter_info = new FigureInfo();
	if (H5_info!=null&&H5_info.size()>0) {
		first_h5_info = H5_info.get(0);
		H5_info.remove(0);
	}
	if (counter_info!=null&&counter_info.size()>0) {
		first_counter_info = counter_info.get(0);
		counter_info.remove(0);
	}
	if (H5_info == null||H5_info.size() == 0) {
		H5_info = new ArrayList<FigureInfo>();
		result.addObject("H5_info", H5_info);
	}else{
		String h5_list = JackSonBeanMapper.listToJson(H5_info);
		result.addObject("H5_info", h5_list);
	}
	if (counter_info == null||counter_info.size() == 0) {
		counter_info = new ArrayList<FigureInfo>();
		result.addObject("counter_info", counter_info);
	}else{
		String counter_list= JackSonBeanMapper.listToJson(counter_info);
		result.addObject("counter_info", counter_list);
	}
	def img_root = GlobalHolder.getProj()?'//' + GlobalHolder.getProj().domain + '/img_server':'//';
	result.addObject('root_path', img_root);
	result.addObject("first_h5_info",first_h5_info);
	result.addObject("first_counter_info", first_counter_info);
	return result.setView('figure/index');
} catch (Exception e) {
	logger.error("",e.toString());
	return result.setData(AvailabilityStatus.notAvailable("查询轮播图失败"));
}
