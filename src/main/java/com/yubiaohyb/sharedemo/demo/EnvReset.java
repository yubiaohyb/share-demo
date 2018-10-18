package com.yubiaohyb.sharedemo.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * Copyright(C) 2018 Toowel Network Technology Co., Ltd. All rights reserved.
 *
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/10/17 14:33
 */
@Component
public class EnvReset implements InitializingBean {

  private ConfigurableApplicationContext ctc;
  @Autowired
  private ConfigurableEnvironment env;


  @Override
  public void afterPropertiesSet() throws Exception {
  }
}
