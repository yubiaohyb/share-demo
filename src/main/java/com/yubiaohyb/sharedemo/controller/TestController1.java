package com.yubiaohyb.sharedemo.controller;

import com.yubiaohyb.sharedemo.annotation.DateEndTime;
import com.yubiaohyb.sharedemo.form.DateEndTimeTestForm;
import com.yubiaohyb.sharedemo.format.DateEndTimeFormatter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/test1")
public class TestController1 {

    public static void main(String[] args) {
        DateEndTimeFormatter format = new DateEndTimeFormatter();
        try {
            System.out.println(format.parse("2018-08-01", null));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @GetMapping("/test1")
    public String test1() {
        return "successs";
    }

    @GetMapping("/test2")
    public String test2(@Validated DateEndTimeTestForm form, BindingResult result) {
        for (ObjectError objErr : result.getAllErrors()) {
            System.out.println(objErr.getDefaultMessage());
        }
        return "successs";
    }

    @GetMapping("/test3")
    public String test3(@DateEndTime Date date) {
        System.out.println(date);
        return "successs";
    }

}
