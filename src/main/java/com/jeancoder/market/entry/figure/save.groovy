package com.jeancoder.market.entry.figure

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import org.apache.commons.io.FileUtils;
import com.jeancoder.core.util.MD5Util
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.FigureInfo
import com.jeancoder.market.ready.service.FigureInfoService
import com.jeancoder.market.ready.util.GlobalHolder
import com.jeancoder.market.ready.util.StringUtil

import org.apache.commons.fileupload.FileItem
def cdn_root_path = '/data/cdn/jc';
def rel_path = 'market/figure';
def title = JC.request.param('title')?.trim();
def info = JC.request.param('info')?.trim();
def url_msg = JC.request.param('url_msg')?.trim();
def type = JC.request.param('type')?.trim();
def figure_type = JC.request.param('figure_type')?.trim();
Result result = new Result();
JCLogger logger=LoggerSource.getLogger(this.getClass().getName());
BigInteger pid = GlobalHolder.getProj().getId();
try {
	def logo_file = null;
	JCRequest req = JC.request.get();
	List<FileItem> items = req.getFormItems();
	if(items) {
		FileItem file = items.get(0);
		String contentType = file.getContentType();
		if (contentType.indexOf("image") < 0 ) {
			return result.setData(AvailabilityStatus.notAvailable("只支持上传*.jpg、*.gif、*.png类型的文件"));
		}
		def define_name = MD5Util.getStringMD5(file.getName() + nextInt(1000, 9999));
		def filename = 'pl' + '_' + define_name;

		def file_path = cdn_root_path + '/' + rel_path;	// + '/' + filename;
		File file_obj = new File(file_path);
		if(!file_obj.exists()) {
			file_obj.mkdir();
		}
		file_path = file_path + '/' + filename;
		logo_file = rel_path + '/' + filename;
		FileUtils.writeByteArrayToFile(new File(file_path), file.get());
	}
	if (StringUtil.isEmpty(title)) {
		return result.setData(AvailabilityStatus.notAvailable("标题不能为空"));
	}
	FigureInfo item = new FigureInfo();
	item.picture = logo_file;
	item.title = title;
	item.info = info;
	item.pid = pid;
	item.flag = 0;
	item.type = type;
	item.url_msg = url_msg;
	item.figure_type = figure_type;
	def num = FigureInfoService.INSTANSE.save(item);
	if (num>0) {
		result.setData(AvailabilityStatus.available());
		return result;
	}
} catch (Exception e) {
	logger.error("",e.toString());
	return result.setData(AvailabilityStatus.notAvailable("添加轮播图失败"));
}
def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}