package com.b5m.search.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.b5m.base.common.exception.CachedException;
import com.b5m.base.common.utils.cache.CachedProxyBase;

public class XMemcachedProxy extends CachedProxyBase{
	private final String name = "xmemcached ver1.3.8";
	private final MemcachedClient client;
	private volatile boolean start = true;
	
	public XMemcachedProxy(MemcachedClient client){
		this.client = client;
	}
	
	@Override
	public String getClientName() {
		return name;
	}

	@Override
	public <T> T getProxy(Class<T> type) {
		return type.cast(this.client);
	}

	@Override
	public void stop() throws IOException {
		if(!start) return ;
		client.shutdown();
		start = false;
	}

	@Override
	public long getCounter(String key) throws CachedException {
		try {
			return client.getCounter(key, defaultCountValue).get();
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (Exception e) {
			throw new CachedException(e);
		}
	}

	@Override
	public long increase(String key, long delta, long initValue)
			throws CachedException {
		try {
			return client.incr(key, delta, initValue);
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (Exception e) {
			throw new CachedException(e);
		}
	}

	@Override
	public long decrease(String key, long delta, long initValue)
			throws CachedException {
		try {
			return client.decr(key, delta, initValue);
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (Exception e) {
			throw new CachedException(e);
		}
	}

	@Override
	protected void add(String key, Object value, long expiredTime,
			long writeTimeout) throws CachedException {
		try {
			client.add(key, (int)expiredTime, value, writeTimeout);
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (InterruptedException e) {
			throw new CachedException(e);
		} catch (MemcachedException e) {
			throw new CachedException(e);
		} 
	}

	@Override
	public void put(String key, Object value, long expiredTime)
			throws CachedException {
		try {
			client.setWithNoReply(key, (int)expiredTime, value);
		} catch (Exception e) {
			throw new CachedException(e);
		} 
	}

	@Override
	public void delete(String key) throws CachedException {
		try {
			client.delete(key);
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (InterruptedException e) {
			throw new CachedException(e);
		} catch (MemcachedException e) {
			throw new CachedException(e);
		} 
	}

	@Override
	protected <T> T get(String key, Class<T> type, long retriveTimeout)
			throws CachedException {
		try {
			Object value = client.get(key, retriveTimeout);
			if(null != value) return type.cast(value);
			return null;
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (InterruptedException e) {
			throw new CachedException(e);
		} catch (MemcachedException e) {
			throw new CachedException(e);
		} 
	}
	
	@Override
	public void remove(String key) throws CachedException {
		try {
			client.delete(key);
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (InterruptedException e) {
			throw new CachedException(e);
		} catch (MemcachedException e) {
			throw new CachedException(e);
		} 
	}

	@Override
	protected <T> Map<String, T> gets(List<String> keyCollections, Class<T> type, long timeout) throws CachedException {
		Map<String, Object> map = gets(keyCollections, timeout);
		if(map == null) return new HashMap<String, T>(0);
		for(String key : map.keySet()){
			Object value = map.get(key);
			if(value == null) return null;
			map.put(key, type.cast(value));
		}
		return (Map<String, T>) map;
	}

	@Override
	protected Map<String, Object> gets(List<String> keyCollections, long timeout) throws CachedException {
		try {
			return client.get(keyCollections, timeout);
		} catch (TimeoutException e) {
			throw new CachedException(e);
		} catch (InterruptedException e) {
			throw new CachedException(e);
		} catch (MemcachedException e) {
			throw new CachedException(e);
		} 
	}

}
