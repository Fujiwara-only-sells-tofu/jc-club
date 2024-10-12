package com.jcclub.gateway.filter;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName：LoginFilter
 * @Author: gouteng
 * @Date: 2024/10/9 16:29
 * @Description: 网关登录过滤器
 */

@Component
@Slf4j
public class LoginFilter implements GlobalFilter {
    @Override
    @SneakyThrows
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //无需构建新的请求  来修改请求的属性
        ServerHttpRequest.Builder mutate = request.mutate();
        String url = request.getURI().getPath();
        log.info("LoginFilter.filter,=.url:{}",url);
        if(url.equals("/auth/user/doLogin")){
            chain.filter(exchange);
        }
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        String loginId = (String) tokenInfo.getLoginId();
        if(StringUtils.isEmpty(loginId)){
            throw new Exception("未获取到用户信息");
        }
        mutate.header("loginId",loginId);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }
}
