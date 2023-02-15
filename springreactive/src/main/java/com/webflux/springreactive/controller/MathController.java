package com.webflux.springreactive.controller;

import com.webflux.springreactive.dto.MulitiplyRequestDto;
import com.webflux.springreactive.dto.Response;
import com.webflux.springreactive.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MathController {

    @Autowired
    private MathService mathService;

    @GetMapping("/square/{no}")
    public Mono<Response> calculateSquare(@PathVariable int no) {
        return mathService.findSquare(no);
    }

    @GetMapping(value = "/table/{input}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> mulTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @PostMapping(value = "/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MulitiplyRequestDto> mulitiplyRequestDtoMono) {
        return mathService.multiplication(mulitiplyRequestDtoMono);
    }
}
