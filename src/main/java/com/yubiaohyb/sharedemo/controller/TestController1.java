package com.yubiaohyb.sharedemo.controller;

import com.yubiaohyb.sharedemo.annotation.DateEndTime;
import com.yubiaohyb.sharedemo.form.DateEndTimeTestForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test1")
public class TestController1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController1.class);

    @GetMapping("/test1")
    public String test1() {
        LOGGER.debug("invoke test1");
        return "successs";
    }

    @GetMapping("/test2")
    public String test2(@Validated DateEndTimeTestForm form, BindingResult result) {
        LOGGER.debug("invoke test2");
        for (ObjectError objErr : result.getAllErrors()) {
            System.out.println(objErr.getDefaultMessage());
        }
        return "successs";
    }

    @GetMapping("/test3")
    public String test3(@DateEndTime Date date) {
        LOGGER.debug("invoke test1");
        System.out.println(date);
        return "successs";
    }

}
