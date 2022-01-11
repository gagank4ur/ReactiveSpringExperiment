package com.example.demo

import com.fasterxml.jackson.annotation.JsonAlias

data class BookDetails(
    val title: String,
    val publishers: List<String>,

    @JsonAlias("number_of_pages")
    val numberOfPages: Int,

    @JsonAlias("publish_date")
    val publishDate: String,
)