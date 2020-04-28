package com.yubiaohyb.sharedemo.netty.tomcat;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/28 17:17
 */
public class DefaultRequest implements Request {
    private final HttpRequest httpRequest;

    public DefaultRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public String getUri() {
        return httpRequest.uri();
    }

    @Override
    public String getPath() {
        return new QueryStringDecoder(httpRequest.uri()).path();
    }

    @Override
    public String getMethod() {
        return httpRequest.method().name();
    }

    @Override
    public Map<String, List<String>> getParameters() {
        return new QueryStringDecoder(httpRequest.uri()).parameters();
    }

    @Override
    public List<String> getParameters(String paramName) {
        return new QueryStringDecoder(httpRequest.uri()).parameters().get(paramName);
    }

    @Override
    public String getParameter(String paramName) {
        return Optional.ofNullable(getParameters(paramName))
            .orElse(Collections.emptyList()).stream().findFirst().orElse(null);
    }
}
