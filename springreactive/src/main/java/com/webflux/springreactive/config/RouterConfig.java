package com.webflux.springreactive.config;

import com.webflux.springreactive.dto.InputFailedValidationResponse;
import com.webflux.springreactive.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{no}", requestHandler::squareHandler)
                .GET("router/table/{input}", requestHandler::tableHandler)
                .POST("router/multiply", requestHandler::multiplyHandler)
                .GET("router/square/{no}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = InputFailedValidationResponse.builder()
                    .message(ex.getMessage())
                    .errorCode(ex.getErrorCode())
                    .input(ex.getInput())
                    .build();
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
