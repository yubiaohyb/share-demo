package com.yubiaohyb.sharedemo;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2021-01-16 23:32
 */
public class ElasticSearchTest implements Printer{
    private RestHighLevelClient restHighLevelClient;

    @Before
    public void init() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("es-cn-zz11okudw000t6re4.elasticsearch.aliyuncs.com", 9200))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(1000)
                        .setSocketTimeout(1000)
                        .setConnectionRequestTimeout(500));
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("search", "GY*&^%$yujnbvf45678ijn"));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        restHighLevelClient = new RestHighLevelClient(builder);
    }

    @After
    public void destroy() {
        try {
            if (null == restHighLevelClient) {
                return ;
            }
            restHighLevelClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test3() {
        try {
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            SearchRequest searchRequest = new SearchRequest("search_c_car_choice"); // 新建索引搜索请求
            searchRequest.scroll(scroll);

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("categoryId", "203"));
            searchSourceBuilder.query(QueryBuilders.termQuery("positionId", "29"));
            searchSourceBuilder.fetchSource("kzSkuCodes", null);

            searchRequest.source(searchSourceBuilder);
            int pageSize = 100;
            searchSourceBuilder.size(pageSize);

            SearchResponse searchResponse = null;
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            int page = 0 ;
            Set<String> set = new HashSet<>();
            SearchHit[] hits = searchResponse.getHits().getHits();
            page++;
            System.out.println("-----第"+ page +"页-----");
            if (Objects.nonNull(hits)) {
                for (SearchHit hit : hits) {
                    List<String> kzSkuCodes = (List<String>) hit.getSourceAsMap().get("kzSkuCodes");
                    set.addAll(kzSkuCodes);
                }
            }
            //遍历搜索命中的数据，直到没有数据
            String scrollId = searchResponse.getScrollId();
            while (Objects.nonNull(hits) && hits.length > 0) {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
                hits = searchResponse.getHits().getHits();
                if (Objects.nonNull(hits) && hits.length > 0) {
                    page++;
                    System.out.println("-----第"+ page +"页-----");
                    for (SearchHit hit : hits) {
                        List<String> kzSkuCodes = (List<String>) hit.getSourceAsMap().get("kzSkuCodes");
                        set.addAll(kzSkuCodes);
                    }
                }
            }
            //清除滚屏
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);//也可以选择setScrollIds()将多个scrollId一起使用
            ClearScrollResponse clearScrollResponse = null;
            clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            boolean succeeded = clearScrollResponse.isSucceeded();
            System.out.println("succeeded:" + succeeded);
            System.out.println("kzSkuCodes:");
            print(set);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2() {
        try {
            GetResponse document = restHighLevelClient.get(new GetRequest("people").id("O3eCC3cBlIRmyliwGnhy"), RequestOptions.DEFAULT);
            if (document.isExists()) {
                print(document.getSource());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        try {
            print(restHighLevelClient.info(RequestOptions.DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test0() {
        try {
            boolean ping = restHighLevelClient.ping(RequestOptions.DEFAULT);
            print(ping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
