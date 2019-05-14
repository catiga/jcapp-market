package com.jeancoder.market.ready.util

import com.jeancoder.market.ready.dto.sys.AuthToken
import com.jeancoder.market.ready.dto.sys.SysProjectInfo

class GlobalHolder {

	private static ThreadLocal<SysProjectInfo> _sys_project_ = new ThreadLocal<SysProjectInfo>();
	
	private static ThreadLocal<AuthToken> _token_ = new ThreadLocal<AuthToken>();
	
	public static void setProj(SysProjectInfo e) {
		_sys_project_.set(e);
	}
	
	public static SysProjectInfo getProj() {
		return _sys_project_.get();
		
	}
	
	public static void setToken(AuthToken token) {
		_token_.set(token);
	}
	
	public static AuthToken getToken() {
		return _token_.get();
	}
	
	
	public static remove() {
		_sys_project_.remove();
		_token_.remove();
	}
}
