package com.jeancoder.market.interceptor.project

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.market.ready.dto.sys.SysProjectInfo
import com.jeancoder.market.ready.dto.sys.AuthRole
import com.jeancoder.market.ready.dto.sys.AuthToken
import com.jeancoder.market.ready.dto.sys.AuthUser
import com.jeancoder.market.ready.util.GlobalHolder
import com.jeancoder.market.ready.util.NativeUtil
import com.jeancoder.market.ready.util.RemoteUtil


/**
 * @urlmapped("/")
*
 */

GlobalHolder.remove();
JCRequest request = RequestSource.getRequest();
String domain = request.getServerName();
SysProjectInfo project = NativeUtil.connect(SysProjectInfo.class, 'project', '/incall/project', ["domain":domain]);
GlobalHolder.setProj(project);
request.setAttribute("current_project", project);
request.setAttribute('pub_bucket', 'https://cdn.iplaysky.com/static/');
return true;