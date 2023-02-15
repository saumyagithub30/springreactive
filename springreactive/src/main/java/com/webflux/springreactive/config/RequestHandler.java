package com.webflux.springreactive.config;

import com.webflux.springreactive.dto.InputFailedValidationResponse;
import com.webflux.springreactive.dto.MulitiplyRequestDto;
import com.webflux.springreactive.dto.Response;
import com.webflux.springreactive.exception.InputValidationException;
import com.webflux.springreactive.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
    @Autowired
    private MathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int no = Integer.parseInt(serverRequest.pathVariable("no"));
        Mono<Response> responseMono = this.mathService.findSquare(no);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int no = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = this.mathService.multiplicationTable(no);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MulitiplyRequestDto> mulitiplyRequestDtoMono = serverRequest.bodyToMono(MulitiplyRequestDto.class);
        Mono<Response> responseMono = this.mathService.multiplication(mulitiplyRequestDtoMono);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        int no = Integer.parseInt(serverRequest.pathVariable("no"));
        if(no<10 || no>20) {
            return Mono.error(new InputValidationException(no));
        }
        Mono<Response> responseMono = this.mathService.findSquare(no);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
}
