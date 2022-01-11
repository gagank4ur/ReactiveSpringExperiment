package com.example.demo

import org.reactivestreams.Publisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class BookController(
    val bookRepository: BookRepository,
    val openLibraryClient: OpenLibraryClient
) {

    @GetMapping("/books")
    fun getAllBooks(): Flux<Book> {
        return bookRepository.findAll()
    }

    @GetMapping("/books/read")
    fun getReadBooks(): Flux<BookResponse> {
        return bookRepository.findBooksByRead(true).flatMap(fun(book: Book): Publisher<out BookResponse> =
            openLibraryClient.getDetails(book.isbn).map {
                BookResponse(
                    isbn = book.isbn,
                    title = book.title,
                    authors = book.authors.split(",").map { it.trim() },
                    read = book.read,
                    owned = book.owned,
                    dateRead = book.dateRead,
                    starRating = book.starRating,
                    publishers = it?.publishers,
                    numberOfPages = it?.numberOfPages,
                    publishDate = it?.publishDate
                )
            }.switchIfEmpty(
                Mono.just(
                    BookResponse(
                        isbn = book.isbn,
                        title = book.title,
                        authors = book.authors.split(",").map { it.trim() },
                        read = book.read,
                        owned = book.owned,
                        dateRead = book.dateRead,
                        starRating = book.starRating,
                        publishers = null,
                        numberOfPages = null,
                        publishDate = null
                    )
                )
            )
        )
    }
}