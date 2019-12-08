package com.yubiaohyb.sharedemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
