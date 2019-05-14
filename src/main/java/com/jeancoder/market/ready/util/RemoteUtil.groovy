package com.jeancoder.market.ready.util

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.market.ready.dto.sys.SysProjectInfo
import com.jeancoder.market.ready.dto.sys.AuthToken;
import com.jeancoder.jdbc.JcPage

public class RemoteUtil {

	public static <T> T connect(Class<T> mapclass, def point, def address) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		T obj = JackSonBeanMapper.fromJson(ret, mapclass);
		return obj;
	}
	
	public static <T> T connectNative(Class<T> mapclass, def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicatorNative(point);
		def ret = systemCaller.doworkAsString(address, params);
		T obj = JackSonBeanMapper.fromJson(ret, mapclass);
		return obj;
	}
	
	
	
	public static <T> T connect(Class<T> mapclass, def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		T obj = JackSonBeanMapper.fromJson(ret, mapclass);
		return obj;
	}
	
	
	
	
	public static <T> List<T>  connectList(Class<T> mapclass, def point, def address) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		List<T> obj = JackSonBeanMapper.jsonToList(ret, mapclass)
		return obj;
	}
	
	
	public static <T> List<T>  connectList(Class<T> mapclass, def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		List<T> obj = JackSonBeanMapper.jsonToList(ret, mapclass)
		return obj;
	}
	
	public static <T> JcPage<T>  connectPage(Class<T> mapclass, def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		JcPage<T> obj = JackSonBeanMapper.fromJson(ret, new JcPage<T>().getClass());
		return obj;
	}
	
	public static <T> T connectAsArray(Class<T> mapclass, def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		T obj = JackSonBeanMapper.jsonToList(ret,mapclass);
		return obj;
	}
	
	public static SysProjectInfo getProj() {
		return GlobalHolder.getProj();
	}
	
	public static AuthToken getAuthToken() {
		return GlobalHolder.getToken();
	}
	
	
	
}
