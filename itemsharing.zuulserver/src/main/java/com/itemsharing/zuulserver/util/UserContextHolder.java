package com.itemsharing.zuulserver.util;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component  // if this annotation is missing, Springframework will loose this Filter class and no intercepting will happen
public class UserContextHolder {

	private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();
	
	public static final UserContext getContext(){
		UserContext context = userContext.get();
		
		if(context == null) {
			context = createEmptyContext();
			userContext.set(context);
		}
		return userContext.get();
	}

	private static UserContext createEmptyContext() {
		// TODO Auto-generated method stub
		return new UserContext();
	}
	
	public static final void setContext( UserContext context) {
		Assert.notNull(context, "Only non-null UserContext instance is permitted");
		userContext.set(context);
	}
	
	
	
	
	
}
