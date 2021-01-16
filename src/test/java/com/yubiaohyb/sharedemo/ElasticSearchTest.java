package com.yubiaohyb.sharedemo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

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
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(1000)
                        .setSocketTimeout(1000)
                        .setConnectionRequestTimeout(500));
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
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("name", "黄"));
            SearchResponse searchResponse = restHighLevelClient.search(new SearchRequest("people").source(searchSourceBuilder), RequestOptions.DEFAULT);
            searchResponse.getHits().iterator().forEachRemaining(a -> print(a.getSourceAsString()));
        } catch (IOException e) {
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
