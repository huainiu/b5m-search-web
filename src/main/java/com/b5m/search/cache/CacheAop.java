package com.b5m.search.cache;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.alibaba.fastjson.JSON;
import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.cache.MemCachedUtils;

@Aspect
public class CacheAop {
	private static LocalCache localCache = new LocalCache();
	
	//com.b5m.service.www.SearchRecommendService
	@Around(value = "execution(* com.b5m.*.*.impl.*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		Method method = getMethod(pjp);
		Cache cache = method.getAnnotation(Cache.class);
		if(cache == null){
			return pjp.proceed();
		}
		String key = cache.key();
		Object result = null;
		if(StringUtils.isEmpty(key)){
			key = DigestUtils.md5Hex(createKey(pjp));
		}
		if(cache.localCache()){
			result = localCache.get(key);
		}
		if(result == null){
			result = MemCachedUtils.getCache(key);
		}
		if(result == null){
			synchronized (key) {
				result = pjp.proceed();
				if(!cache.cacheEmpty()){
					if(result == null){
						return result;
					}
					if(Lang.isList(result) && ((List<?>) result).isEmpty()){
						return result;
					}
					if(Lang.isMap(result) && ((Map) result).isEmpty()){
						return result;
					}
				}
				MemCachedUtils.setCache(key, result, (int)cache.timeout());
				if(cache.localCache()){
					localCache.put(key, result, cache.timeout() * 1000000);
				}
			}
		}
        return result;  
    }
	
	private String createKey(ProceedingJoinPoint pjp){
		String className = pjp.getTarget().getClass().getSimpleName();
		String methodName = pjp.getSignature().getName();
		Object[] args = pjp.getArgs();
		StringBuilder key = new StringBuilder();
		key.append(className);
		key.append("_");
		key.append(methodName);
		key.append("_");
		for(Object arg : args){
			if(arg instanceof HttpServletRequest){
				continue;
			}
			if(arg instanceof HttpServletResponse){
				continue;
			}
			key.append(JSON.toJSONString(arg));
			key.append("_");
		}
		return key.toString();
	}
	
	private Method getMethod(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		String methodName = pjp.getSignature().getName();
		Method[] methods = pjp.getTarget().getClass().getMethods();
		for(Method method : methods){
			if(method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
				return method;
			}
		}
		return null;
	}
	
}
