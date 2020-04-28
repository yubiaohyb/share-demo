package com.yubiaohyb.sharedemo.netty.tomcat;

import java.io.UnsupportedEncodingException;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/28 17:11
 */
public interface Response {
    /** 写入内容 */
    void write(String content) throws UnsupportedEncodingException, Exception;
}
