package com.yubiaohyb.sharedemo;

import com.alibaba.fastjson.JSON;
import com.yubiaohyb.sharedemo.dao.mongo.ParentRepository;
import com.yubiaohyb.sharedemo.model.ParentDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019-05-25 20:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ParentRepository parentRepository;

    @Test
    public void test() {
        System.out.println(JSON.toJSONString(mongoTemplate.findAll(ParentDo.class)));
    }

    @Test
    public void test2() {
        System.out.println(JSON.toJSONString(parentRepository.findAll()));
    }
}
