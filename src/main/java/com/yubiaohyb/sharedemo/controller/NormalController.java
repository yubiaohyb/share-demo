package com.yubiaohyb.sharedemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.internal.spi.CheckResult;
import com.taobao.api.internal.spi.SpiUtils;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019-07-16 10:01
 */
@RestController
@RequestMapping
@Slf4j
public class NormalController {

    @RequestMapping("/index")
    public String index() {
        log.info("index-----------");
        return "/index.html";
    }

    @PostMapping("/qimen/canary/appServiceReserveFacade/orderServicesReservable")
    @ResponseBody
    public QmResult<Boolean> orderServicesReservable(HttpServletRequest request, OrderServicesReservableQueryRo queryRo) {

        try {
            CheckResult checkResult = SpiUtils.checkSign(request, "be27c6a01d8f33db32e5f8956fba2863");
            if(checkResult.isSuccess()){
                log.info(checkResult.getRequestBody());
//                ObjectMapper objectMapper = JSONUtils.getObjectMapper();
//                String key = objectMapper.readTree(checkResult.getRequestBody()).findPath("k").asText();
//                ValueOperations<String , JSONObject> operations=redisTemplate.opsForValue();
                return QmResults.newSuccessResult(true);
            }else {
                return QmResults.newFailResult("sign-check-failure","Illegal request");
            }
        } catch (Exception e) {
            log.error("异常", e);
        }
        log.info("==========\n{}\n", queryRo);
        return QmResults.newSuccessResult(true);

    }

}
