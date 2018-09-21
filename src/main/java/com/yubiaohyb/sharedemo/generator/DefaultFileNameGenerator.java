package com.yubiaohyb.sharedemo.generator;

/**
 * Copyright(C) 2018 Toowel Network Technology Co., Ltd. All rights reserved.
 *
 * description  -  默认文件名称生成器（暂时无用）
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/21 16:55
 */
public class DefaultFileNameGenerator extends AbstractFileNameGenerator {

    public DefaultFileNameGenerator() {}


    @Override
    public String generateFileName() {
      return "3333.xls";
    }
}
