package com.yubiaohyb.sharedemo.demo;

import org.springframework.beans.factory.InitializingBean;
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

  private String name;

  @Override
  public void afterPropertiesSet() throws Exception {

  }
}
