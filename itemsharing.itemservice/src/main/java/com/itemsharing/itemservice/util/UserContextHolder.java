package com.itemsharing.itemservice.util;

import org.springframework.util.Assert;

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
