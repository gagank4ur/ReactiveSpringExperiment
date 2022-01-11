package com.example.demo

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class OpenLibraryClient(val webClient: WebClient) {


    fun getDetails(isbn: String): Mono<BookDetails> {
        if (isbn.isBlank()) {
            return Mono.empty()
        }
        return webClient.get().uri("/isbn/${isbn}.json")
            .retrieve()
            .bodyToMono(BookDetails::class.java)
            .onErrorResume { Mono.empty() }
    }

}

