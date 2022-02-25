package com.example.demo

import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TotalsService {

    @NewSpan("calculateTotals")
    fun calculateTotals(bookWithDetails: Flux<BookWithDetails>): Mono<BookResponse> {
        return bookWithDetails.collectList().map {
        var totalPages = 0
            it.forEach { book ->
                totalPages += book.numberOfPages ?: 0
            }
            return@map BookResponse(totalPages, it)
        }
    }
}