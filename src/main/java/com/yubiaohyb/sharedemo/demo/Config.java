package com.yubiaohyb.sharedemo.demo;

import java.util.Properties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Copyright(C) 2018 Toowel Network Technology Co., Ltd. All rights reserved.
 *
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/10/17 11:30
 */
@Component
public class Config implements InitializingBean {

  @Value("${name2}")
  private String name;

  private static Properties props = new Properties();

  @Override
  public void afterPropertiesSet() throws Exception {
//    UrlResource urlResource = new UrlResource("http://localhost/application.properties");
//    props.load(urlResource.getInputStream());

    System.out.println("======");
    System.out.println(this.name);
//    this.name = props.getProperty("name");
    System.out.println(this.name);
    System.out.println("======");
  }
}
