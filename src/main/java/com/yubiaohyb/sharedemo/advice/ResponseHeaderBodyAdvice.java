package com.yubiaohyb.sharedemo.advice;

import com.yubiaohyb.sharedemo.annotation.ResponseHeader;
import org.apache.poi.POIDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 *     借助ResponseBodyAdvice实现对响应结果的自定义。
 *     这里结合注解ResponseHeader实现对响应头部分contentType和contentPosition属性的设置，
 *     并完成响应体的数据流写入。
 * </p>
 *
 * description  -  响应自定义切面
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/21 10:43
 */

@RestControllerAdvice(basePackages="com.yubiaohyb.sharedemo.controller")
public class ResponseHeaderBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHeaderBodyAdvice.class);

    private static ResponseHeader getResponseHeader(MethodParameter returnType) {
        return returnType.getMethodAnnotation(ResponseHeader.class);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ResponseHeader responseHeader = getResponseHeader(returnType);
        return null != responseHeader;
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter returnType, MediaType mediaType,
        Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Assert.notNull(obj, "ResponseHeader注解的方法返回值不允许为null");
        ResponseHeader responseHeader = getResponseHeader(returnType);
        String mediaTypeValue = responseHeader.mediaTypeValue();
        String fileName = responseHeader.fileName();
        if (mediaTypeValue.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
            serverHttpResponse.getHeaders().setContentDispositionFormData("attachment", fileName);
            if (obj instanceof POIDocument) {
                try {
                    ((POIDocument) obj).write(serverHttpResponse.getBody());
                    return null;
                } catch (IOException e) {
                    LOGGER.error("数据写入响应体失败", e);
                }
            }
        }
        return obj;
    }
}
