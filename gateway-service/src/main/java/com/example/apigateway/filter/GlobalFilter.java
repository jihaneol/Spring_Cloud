package com.example.apigateway.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(GlobalFilter.Config config) {

        // Pre Filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            ServerHttpResponse response = exchange.getResponse();


            log.info("Custom PRE filter: request ip -> {}",request.getRemoteAddress());

            if (config.isPreLogger()) log.info("global start: request Id -> {}",request.getId());
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger())
                    log.info("global end: response code -> {}",response.getStatusCode());
            }));
        });
    }

    @Getter
    @Setter
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
