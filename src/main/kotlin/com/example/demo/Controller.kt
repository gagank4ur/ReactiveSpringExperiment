package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class Controller {

    @GetMapping
    fun hello(): Mono<String> {
        return Mono.just("Hello World")
    }
}