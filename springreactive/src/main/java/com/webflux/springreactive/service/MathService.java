package com.webflux.springreactive.service;

import com.webflux.springreactive.dto.MulitiplyRequestDto;
import com.webflux.springreactive.dto.Response;
import com.webflux.springreactive.utils.SleepUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathService {

    @Autowired
    private SleepUtils sleepUtils;

    public Mono<Response> findSquare(int data) {
        return Mono.fromSupplier(() -> data * data).map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1,10)
                .doOnNext(i -> sleepUtils.sleepSeconds(1000))
                .doOnNext(i -> System.out.println("Processing data :" + i))
                .map(i -> new  Response(i*input));
    }

    public Mono<Response> multiplication(Mono<MulitiplyRequestDto> mulitiplyRequestDtoMono) {
        return mulitiplyRequestDtoMono
                .map(dto-> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }
}
