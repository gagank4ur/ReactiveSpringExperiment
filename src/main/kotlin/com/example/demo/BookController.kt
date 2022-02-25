package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
class BookController(
    val bookRepository: BookRepository,
    val openLibraryClient: OpenLibraryClient,
    val totalsService: TotalsService,
) {

    @GetMapping("/books")
    fun getAllBooks(): Flux<Book> {
        return bookRepository.findAll()
    }

    @GetMapping("/books/read")
    fun getReadBooks(): Flux<BookWithDetails> {
        return bookRepository.findBooksByRead(true).flatMap { book ->
            getDetails(book)
        }
    }

    @GetMapping("/books/readWithPages")
    fun getReadBooksWithPages(): Mono<BookResponse> {
        val books: Flux<BookWithDetails> = bookRepository.findBooksByRead(true).flatMap { book ->
            getDetails(book)
        }
        return totalsService.calculateTotals(books)
    }

    @GetMapping("/books/read/parallel")
    fun getReadBooksParallel(): Flux<BookWithDetails> {
        return bookRepository.findBooksByRead(true).parallel().runOn(Schedulers.boundedElastic()).flatMap { book ->
            getDetails(book)
        }.sequential()
    }

    private fun getDetails(book: Book): Mono<BookWithDetails> {
        return openLibraryClient.getDetails(book.isbn).map {
            BookWithDetails(
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
                BookWithDetails(
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
    }
}