package com.usian.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.usian.ElasticsearchApp;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ElasticsearchApp.class})
public class IndexWriterTest {
	@Autowired
    private RestHighLevelClient restHighLevelClient;

   //创建索引库
    @Test
    public void testCreateIndex() throws IOException {
        //创建“创建索引请求”对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("java1906");
        //设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards",2).
                                    put("number_of_replicas",0));
        createIndexRequest.mapping("course", "{\r\n" + 
        		"  \"_source\": {\r\n" + 
        		"    \"excludes\":[\"description\"]\r\n" + 
        		"  }, \r\n" + 
        		" 	\"properties\": {\r\n" + 
        		"           \"name\": {\r\n" + 
        		"              \"type\": \"text\",\r\n" + 
        		"              \"analyzer\":\"ik_max_word\",\r\n" + 
        		"              \"search_analyzer\":\"ik_smart\"\r\n" + 
        		"           },\r\n" + 
        		"           \"description\": {\r\n" + 
        		"              \"type\": \"text\",\r\n" + 
        		"              \"analyzer\":\"ik_max_word\",\r\n" + 
        		"              \"search_analyzer\":\"ik_smart\"\r\n" + 
        		"           },\r\n" + 
        		"           \"studymodel\": {\r\n" + 
        		"              \"type\": \"keyword\"\r\n" + 
        		"           },\r\n" + 
        		"           \"price\": {\r\n" + 
        		"              \"type\": \"float\"\r\n" + 
        		"           },\r\n" + 
        		"           \"timestamp\": {\r\n" + 
        		"          		\"type\":   \"date\",\r\n" + 
        		"          		\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\r\n" + 
        		"        	}\r\n" + 
        		"  }\r\n" + 
        		"}", XContentType.JSON);
        //创建响应对象	返回索引操作客户端
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest);
        //得到响应结果
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

	//删除索引库
	@Test
	public void testDeleteIndex() throws IOException {
		//创建“删除索引请求”对象
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("java1906");
		//创建索引操作客户端
		IndicesClient indices = restHighLevelClient.indices();
		//创建响应对象
		DeleteIndexResponse deleteIndexResponse =
				indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		//得到响应结果
		boolean acknowledged = deleteIndexResponse.isAcknowledged();
		System.out.println(acknowledged);
	}

  }