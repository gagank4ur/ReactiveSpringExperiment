package com.example.demo

import java.time.LocalDate

data class BookWithDetails(
    val isbn: String,
    val title: String,
    val authors: List<String>,
    val read: Boolean,
    val owned: Boolean,
    val dateRead: LocalDate? = null,
    val starRating: Double?,
    val publishers: List<String>?,
    val numberOfPages: Int?,
    val publishDate: String?,
)