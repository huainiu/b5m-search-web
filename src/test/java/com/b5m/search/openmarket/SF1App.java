package com.b5m.search.openmarket;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.httpclient.HttpClientFactory;
import com.b5m.sf1api.dto.res.GroupTree;
import com.b5m.sf1api.dto.res.SearchDTO;
import com.b5m.sf1api.utils.Sf1DataHelper;

public class SF1App {

	private static final String DOCID = "4489ad1498a2d7b35eb80943b46434c5";

	@Test
	public void sf1Get() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		String url = "http://10.10.99.188:8888/sf1r/documents/get";
		// String url = "http://10.10.99.188:8888/sf1r/documents/search";
		String json = IOUtils.toString(this.getClass().getResourceAsStream("jsonpget.json"));
		PostMethod method = createPostMethod(url, json);
		int statusCode = httpClient.executeMethod(method);
		String resultMsg = method.getResponseBodyAsString().trim();
		System.out.println(resultMsg);
		// FileUtils.write(new java.io.File("/home/echo/jsonpsearchdata.txt"),
		// resultMsg, "UTF-8");
	}
	
	@Test
	public void testDoPost() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		String url = "http://localhost:8080/B5MCMS/b5mcmsyagoods.do";
		// String url = "http://10.10.99.188:8888/sf1r/documents/search";
		String json = IOUtils.toString(this.getClass().getResourceAsStream("jsonpget.json"));
		PostMethod method = createPostMethod(url, json);
		int statusCode = httpClient.executeMethod(method);
		String resultMsg = method.getResponseBodyAsString().trim();
		System.out.println(resultMsg);
	}
	
	@Test
	public void testPost() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		PostMethod method = new PostMethod("http://10.10.100.9:18815/s/s/relGoods.html?t=1405047258348&title=%E9%9E%8B%E5%AD%90%E7%94%B7");
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		int statusCode = httpClient.executeMethod(method);
		String resultMsg = method.getResponseBodyAsString().trim();
		System.out.println(resultMsg);
	}

	@Test
	public void sf1Search() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		 String url = "http://10.10.99.188:8888/sf1r/documents/search";
//		String url = "http://10.10.96.188:8888/sf1r/documents/search";
//		String url = "http://10.10.99.177:8888/sf1r/documents/search";
//		String url = "http://10.10.99.177:8888/sf1r/documents/search";
		String json = IOUtils.toString(this.getClass().getResourceAsStream("jsonpsearch.json"));
		PostMethod method = createPostMethod(url, json);
		int statusCode = httpClient.executeMethod(method);
		System.out.println("statusCode : " + statusCode);
		String resultMsg = method.getResponseBodyAsString().trim();
		System.out.println(resultMsg);
//		category(resultMsg);
		// System.out.println(resultMsg);2b15be1b8583ad61a9c4715e6abda405
//		FileUtils.write(new java.io.File("/home/echo/jsonpsearchdata.txt"), resultMsg, "UTF-8");
		System.out.println("查询完成.......");
		
		File file = new File("//");
	}

	private static PostMethod createPostMethod(String URL, String content) throws UnsupportedEncodingException {
		PostMethod method = new PostMethod(URL);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.setRequestEntity(new StringRequestEntity(content, "application/json", "UTF-8"));
		method.setRequestHeader("Connection", "Keep-Alive");
		return method;
	}
	
	public void category(String resultMsg){
		JSONObject jsonObject = JSONObject.parseObject(resultMsg);
		SearchDTO searchDTO = new SearchDTO();
		Sf1DataHelper.setCategoryAndGroupInfo(searchDTO, jsonObject);
		
		GroupTree categoryGroupTree = searchDTO.getCategoryTree();
		List<GroupTree> groupTrees = categoryGroupTree.getGroupTree();
		for(GroupTree groupTree : groupTrees){
			String firstGroupName = groupTree.getGroup().getGroupName();
			System.out.println(firstGroupName);
			for(GroupTree second : groupTree.getGroupTree()){
				String secondGroupName = second.getGroup().getGroupName();
				System.out.println(firstGroupName + ">" + secondGroupName);
				for(GroupTree third : second.getGroupTree()){
					String thirdGroupName = second.getGroup().getGroupName();
					System.out.println(firstGroupName + ">" + secondGroupName + ">" + thirdGroupName);
				}
			}
			System.out.println("");
		}
	}
	
	@Test
	public void testRandom(){
//		c7e9954a-63ce-4681-b03c-ba25e214ea70
		//a-z 0-9 -   1+2+4+8+2+10 236
		String random = UUID.randomUUID().toString();
		System.out.println(random);
		
	}
}
