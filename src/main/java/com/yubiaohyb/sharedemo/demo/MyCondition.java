package com.yubiaohyb.sharedemo.demo;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;

/**
 * Copyright(C) 2018 Toowel Network Technology Co., Ltd. All rights reserved.
 *
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/10/17 17:30
 */
public class MyCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Class clazz = null;
    try {
      clazz = Class.forName(((AnnotationMetadataReadingVisitor) metadata).getClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    PropertySource propertySource = (PropertySource) clazz.getAnnotation(PropertySource.class);
    String values = propertySource.value()[0];
    return "http://localhost/application-a.properties".equals(values);
  }
}
