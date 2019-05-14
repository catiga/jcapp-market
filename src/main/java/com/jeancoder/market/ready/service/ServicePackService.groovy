package com.jeancoder.market.ready.service

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.ServicePack

class ServicePackService {
	private static final JCLogger LOGGER = LoggerSource.getLogger(ServicePackService.class);
	static final ServicePackService INSTANSE = new ServicePackService();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();

	//保存服务名称
	public Integer saveItem(ServicePack sp){
		ServicePack item = new ServicePack();
		item.spno = sp.spno;
		item.spname = sp.spname;
		item.pid = sp.pid;
		item.a_time = new Date();
		return jcTemplate.save(item);
	}

	public Integer updateItem(ServicePack sp){
		ServicePack spack = getServicePackBy_id(sp.id);
		if(spack==null){
			return "";
		}
		spack.spno = sp.spno;
		spack.spname = sp.spname;
		return jcTemplate.update(spack);
	}
	
	public Integer switch_biz(ServicePack sp){
		if(sp.flag==0){
			sp.flag=1;
			return jcTemplate.update(sp);
		}
		if(sp.flag==1){
			sp.flag=0;
			return jcTemplate.update(sp);
		}
	}

	public ServicePack getServicePackBy_id(BigInteger id){
		String sql = "select * from ServicePack where id=? and flag!=?";
		return jcTemplate.get(ServicePack.class, sql, id,-1);
	}

	public JcPage<ServicePack> getList(BigInteger pid, JcPage<ServicePack> page) {
		StringBuffer sql = new StringBuffer(" select * from ServicePack where pid=? and flag!=? order by a_time desc");
		return jcTemplate.find(ServicePack.class, page, sql.toString(),pid,-1);
	}
	
	//获取服务列表
	public List<ServicePack> getList(BigInteger pid){
		StringBuffer sql = new StringBuffer(" select * from ServicePack where pid=? and flag!=? order by a_time desc");
		return jcTemplate.find(ServicePack.class, sql.toString(),pid,-1);
	}
}
