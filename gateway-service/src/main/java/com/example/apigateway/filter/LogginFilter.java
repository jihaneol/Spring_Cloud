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
public class LogginFilter extends AbstractGatewayFilterFactory<LogginFilter.Config> {

    public LogginFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(LogginFilter.Config config) {

        // Pre Filter
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging filter: request id -> {}",config.getBaseMessage());

            if (config.isPreLogger()) log.info("Logging start: request Id -> {}",request.getId());
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger())
                    log.info("Logging  end: response code -> {}",response.getStatusCode());
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
