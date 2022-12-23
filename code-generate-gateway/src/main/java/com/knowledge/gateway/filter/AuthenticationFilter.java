package com.knowledge.gateway.filter;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final String[] white = { "/api/v1/user/register" };
    /**
     * 定义验证请求头是否带有 Authorization
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 响应对象
        ServerHttpResponse response = exchange.getResponse();
        // /question/api/question/1
        String path = request.getPath().pathWithinApplication().value();

        // 公开api接口进行放行，无需认证
        if(StringUtils.indexOfAny(path, white) != -1) {
            // 直接放行
            return chain.filter(exchange);
        }

        // 请求头信息
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isEmpty(authorization)) {
            // 没有带authorization请求头，则响应错误信息
            // 封装响应信息
            JSONObject message = new JSONObject();
            message.put("code", 1401);
            message.put("msg", "缺少身份凭证");
            message.put("data", "");
            // 转换响应消息内容对象为字节
            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            // 设置响应对象状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 设置响应对象内容并且指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE,  "application/json;charset=UTF-8");
            // 返回响应对象
            return response.writeWith( Mono.just(buffer) );
        }
        // 如果请求头不为空，则验证通过，放行此过滤器
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        //过滤器执行顺序，越小越优先执行
        return 0;
    }
}
