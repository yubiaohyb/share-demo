package com.yubiaohyb.sharedemo.demo;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Copyright(C) 2018 Toowel Network Technology Co., Ltd. All rights reserved.
 *
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/10/17 14:06
 */

@Configuration
@PropertySource(value="http://localhost/application-a.properties")
@Conditional(value=MyCondition.class)
public class MyConfig {

}
