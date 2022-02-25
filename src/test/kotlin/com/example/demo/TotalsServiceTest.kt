package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.LocalDate

internal class TotalsServiceTest {
    val wiseMansFear = BookWithDetails(
        isbn = "9780756404734",
        title = "The Wise Man's Fear",
        authors = listOf("Patrick Rothfuss"),
        read = true,
        owned = false,
        dateRead = LocalDate.of(2016, 5, 4),
        starRating = 5.0,
        publishers = listOf("Daw Books"),
        numberOfPages = 994,
        publishDate = "2011-03"
    )
    val fifthSeason = BookWithDetails(
        isbn = "9780316229296",
        title = "The Fifth Season",
        authors = listOf("N.K. Jemisin"),
        read = true,
        owned = false,
        dateRead = LocalDate.of(2017, 11, 7),
        starRating = 5.0,
        publishers = listOf("Orbit"),
        numberOfPages = 498,
        publishDate = "2015"
    )

    @Test
    internal fun calculatesTotals() {
        val totalsService = TotalsService()

        val response = totalsService.calculateTotals(Flux.just(wiseMansFear, fifthSeason)).block()!!

        assertThat(response.totalPages).isEqualTo(wiseMansFear.numberOfPages!!.plus(fifthSeason.numberOfPages!!))
        assertThat(response.books).isEqualTo(listOf(wiseMansFear, fifthSeason))

    }
}