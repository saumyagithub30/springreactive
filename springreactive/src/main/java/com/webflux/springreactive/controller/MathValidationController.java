package com.webflux.springreactive.controller;

import com.webflux.springreactive.dto.Response;
import com.webflux.springreactive.exception.InputValidationException;
import com.webflux.springreactive.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MathValidationController {

    @Autowired
    private MathService mathService;

    @GetMapping("/square/{no}/throw")
    public Mono<Response> calculateSquare(@PathVariable int no) {
        if(no<10 || no>20)
            throw new InputValidationException(no);
        return mathService.findSquare(no);
    }

    //Exception Handling in Reactive pipeline
    @GetMapping("/square/{no}/mono-error")
    public Mono<Response> calculateSquareMonoError(@PathVariable int no) {
        return Mono.just(no)
                .handle((integer, sink) -> {
                    if(integer >=10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(integer));
                }).cast(Integer.class)
                        .flatMap(i-> this.mathService.findSquare(i));
    }

    @GetMapping("/square/{no}/assignment")
    public Mono<ResponseEntity<Response>> assignment(@PathVariable int no) {
        return Mono.just(no)
                .filter(i -> i >=10 && i<=20)
                .flatMap(i-> this.mathService.findSquare(i))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
