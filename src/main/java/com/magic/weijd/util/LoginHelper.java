package com.magic.weijd.util;

import com.magic.weijd.entity.Admins;
import com.magic.weijd.entity.User;
import com.magic.weijd.enums.Common;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.cache.RedisCache;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginHelper {


	public static final String TOKEN = "weijd_token";

	public static boolean isLogin=false;

	/**SESSION USER*/
	public static final String SESSION_USER = "weijd_wechat_user";

	/**SESSION USER ADMIN*/
	public static final String SESSION_USER_ADMIN = "weijd_admin_user";
	

	public static void delObject(String token){
		RedisCache.remove(token);
	}

	public static Admins getCurrentAdmin(){
		HttpServletRequest req = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
		Object obj = req.getSession().getAttribute(SESSION_USER_ADMIN);
		if(null == obj){
			String token = req.getHeader(TOKEN);
			Object admin = RedisCache.get(token);
			if(null == admin){
				return null;
			}else{
				return (Admins) admin;
			}
		}
		return (Admins)obj;
	}

	public static User getCurrentUser(){
		HttpServletRequest req = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
		Object obj = req.getSession().getAttribute(SESSION_USER);
		if(null == obj){
			String token = req.getHeader(TOKEN);
			Object user = RedisCache.get(token);
			if(null == user){
				return null;
			}else{
				return (User) user;
			}
		}
		return (User)obj;
	}


	public static User getCurrentUserOfAPI() throws Exception{
		HttpServletRequest req = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
		String token = req.getHeader(TOKEN);
		User user = (User)RedisCache.get(token);
		if(null == user){
			throw new InterfaceCommonException(StatusConstant.NOTLOGIN,"未登录");
		}
		if(Common.NO.ordinal() == user.getIsValid()){
			throw new InterfaceCommonException(StatusConstant.ACCOUNT_FROZEN,"帐号无效");
		}
		return user;

	}






	public static User getCurrentUser(String token){
		return (User)RedisCache.get(token);
	}
	
	public static void clearToken(String token){
		HttpServletRequest req = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
		if(null == token){
			req.getSession().invalidate();
		}else{
			RedisCache.remove(token);
		}
	}

//	public static String addToken(User user){
//		String token = null;
//		// 用户
//		if(user.getToken() != null){
//			Object tempObj = MemcachedUtil.getInstance().get(user.getToken());
//			if(null != tempObj){
//				MemcachedUtil.getInstance().delObj(user.getToken());
//			}
//		}
//		token = UUID.randomUUID().toString().replaceAll("-", "");
//		user.setToken(token);
//		redisCache.put(token, user);
//		return token;
//	}
	
	public static boolean  replaceToken(String token,Object obj){
		return RedisCache.put(token, obj);
	}


	
}
