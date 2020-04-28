package com.yubiaohyb.sharedemo.netty.tomcat;

import java.util.List;
import java.util.Map;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/28 17:03
 */
public interface Request {
    /** 获取请求uri，包含?后请求参数 */
    String getUri();

    /** 获取请求路径，不包含?后请求参数 */
    String getPath();

    /** 获取请求方法 */
    String getMethod();

    /** 获取所有请求参数映射集合 */
    Map<String, List<String>> getParameters();

    /** 获取指定参数名参数值列表 */
    List<String> getParameters(String paramName);

    /** 获取指定参数名对应参数值 */
    String getParameter(String paramName);
}
