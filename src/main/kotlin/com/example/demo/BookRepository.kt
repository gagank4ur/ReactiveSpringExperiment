package com.example.demo

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import java.time.Year

interface BookRepository : R2dbcRepository<Book, Long> {


    fun findBooksByRead(isRead: Boolean) : Flux<Book>

}
