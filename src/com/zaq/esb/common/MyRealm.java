package com.zaq.esb.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.zaq.esb.service.impl.AdminUserService;

public class MyRealm extends AuthorizingRealm {  
	@Autowired
	private AdminUserService adminUserService;
    /** 
     * 为当前登录的Subject授予角色和权限 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
//    	 String username = (String) principals.getPrimaryPrincipal();
//         if (StringUtils.isNotEmpty(username)) {
//             SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
//             return authenticationInfo;
//         }
    	//暂时不需要角色分配
        return null;  
    }  
   
       
    /** 
     * 验证当前登录的Subject 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {  
        //获取基于用户名和密码的令牌  
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
        //两个token的引用都是一样的
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;  
        System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));  
        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息  
        //说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码
        BaseModel model = adminUserService.getByUserName(token.getUsername());
		
		if(null!=model){
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(model,model.getStr("password"), model.getStr("username"));  
            return authcInfo; 
		}
		
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常  
        return null;  
    }  
}