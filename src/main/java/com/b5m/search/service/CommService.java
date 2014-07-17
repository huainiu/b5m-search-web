package com.b5m.search.service;

import java.util.List;
import java.util.Map;

public interface CommService {
	
	List<Map<String, String>> getRecommandProduces(String docid, String keyword, Integer pageSize, String collection);
	
}
