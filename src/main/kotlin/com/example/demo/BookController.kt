package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class BookController(val bookRepository: BookRepository) {

    @GetMapping("/books")
    fun getAllBooks(): Flux<Book> {
        return bookRepository.findAll()
    }
}