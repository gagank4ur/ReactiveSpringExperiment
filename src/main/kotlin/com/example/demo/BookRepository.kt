package com.example.demo

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface BookRepository : R2dbcRepository<Book, Long> {
}
