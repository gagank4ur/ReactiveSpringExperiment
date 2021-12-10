package com.example.demo

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class Book(
    val isbn: String,
    val title: String,
    val authors:String,
    val read: Boolean,
    val owned: Boolean,
    val dateRead: LocalDate? = null,
    val starRating: Double?,
) {
    @Id
    var id:Long? = null
}