package com.b5m.search.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
	
	String key() default "";
	
	//单位秒
	long timeout() default 7200;
	
	boolean localCache() default false;
	
	boolean cacheEmpty() default false;
}
