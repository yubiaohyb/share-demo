package com.yubiaohyb.sharedemo.controller;

import com.alibaba.fastjson.JSON;
import com.yubiaohyb.sharedemo.annotation.DateEndTime;
import com.yubiaohyb.sharedemo.annotation.ResponseHeader;
import com.yubiaohyb.sharedemo.demo.WriteExcel;
import com.yubiaohyb.sharedemo.form.DateEndTimeTestForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  测试控制器
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
@RestController
@RequestMapping("/test1")
@Slf4j
public class TestController1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController1.class);

    @Autowired(required = false)
    private RestHighLevelClient restHighLevelClient;

    private List<String> oomList = new ArrayList<>();


    @PostMapping("/curl")
    public String curl(HttpServletRequest request) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            reader.lines().forEach(a -> {
                try {
                    log.info(new String(Base64.getDecoder().decode(a), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });

           // reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //log.info(JSON.toJSONString(request.getAttributeNames()));
        return "success";
    }

    @GetMapping("/oom")
    public boolean oom() {
        System.out.println("======开始=======");
        IntStream.range(0, Integer.MAX_VALUE).forEach(a -> oomList.add(a + ""));
        System.out.println("======结束=======");
        return true;
    }

    @GetMapping("/elasticsearch/ping")
    public boolean get() {
        try {
            return restHighLevelClient.ping(RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.info("ping elasticsearch 异常", e);
        }
        return false;
    }

    @GetMapping("/session")
    public String session(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        HttpSession session = request.getSession();

        for (Cookie cookie : cookies) {
            System.out.println(cookie);
        }
        System.out.println(JSON.toJSONString(session));
        session.setAttribute("testAttr", "this is a test content.");
        return "SUCCESS";

    }

    /**
     *
     * @return
     */
    @GetMapping("/test1")
    public String test1() {
        LOGGER.debug("invoke test1");
        return "successs";
    }

    @GetMapping("/li")
    public List<Integer> li() {
        LOGGER.debug("invoke li");
        return Arrays.asList(1, 2, 3);
    }

    /**
     *
     * @param form
     * @param result
     * @return
     */
    @GetMapping("/test2")
    public String test2(@Validated DateEndTimeTestForm form, BindingResult result) {
        LOGGER.debug("invoke test2");
        for (ObjectError objErr : result.getAllErrors()) {
            System.out.println(objErr.getDefaultMessage());
        }
        return "successs";
    }

    @GetMapping("/test3")
    public void test3(@DateEndTime Date date) {
        LOGGER.debug("invoke test3");
        System.out.println(date);
    }

    @ResponseHeader(fileName = "543.xls")
    @GetMapping("/test4")
    public HSSFWorkbook test4() {
        LOGGER.debug("invoke test4");
        return WriteExcel.getWorkbook();
    }

    @GetMapping("/test5")
    public void test5(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", String.format("attachment;fileName=%s.xls", "123"));
        WriteExcel.getWorkbook().write(response.getOutputStream());
    }
}
