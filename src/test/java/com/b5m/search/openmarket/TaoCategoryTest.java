package com.b5m.search.openmarket;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class TaoCategoryTest {
	
	@Test
	public void testConsistCategory() throws IOException{
		/*List<String> lines = IOUtils.readLines(TaoCategoryTest.class.getResourceAsStream("cat.txt"));
		JSONObject jsonObject = new JSONObject();
		for(String line : lines){
			if(StringUtils.isEmpty(line)) continue;
			String[] cats = line.split("==");
			jsonObject.put(cats[0], cats[1]);
			jsonObject.put(cats[1], cats[0]);
		}
		System.out.println(jsonObject.toJSONString());*/
	}
	
}
