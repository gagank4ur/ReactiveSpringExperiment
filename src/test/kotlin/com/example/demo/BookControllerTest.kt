package com.example.demo

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Mono
import java.time.LocalDate

@AutoConfigureWebTestClient
@SpringBootTest
internal class BookControllerTest {

    @Autowired
    lateinit var webClient: WebTestClient

    @MockBean
    lateinit var openLibraryClient: OpenLibraryClient

    @Test
    internal fun getsAllBooks() {
        webClient.get().uri("/books")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList<Book>().hasSize(3)
            .contains( Book(
                isbn = "9781529118742",
                title = "Sorrowland",
                authors = "Rivers Solomon",
                read = false,
                owned = true,
                dateRead = null,
                starRating = null,
            ))
    }

    @Test
    internal fun getsReadBooks() {
        Mockito.`when`(openLibraryClient.getDetails("9780756404734")).thenReturn(
            Mono.just(BookDetails(
            "The Wise Man's Fear",
            listOf("Daw Books"),
            994,
            "2011-03"
        )))

        webClient.get().uri("/books/read")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList<BookResponse>().hasSize(1)
            .contains( BookResponse(
                isbn = "9780756404734",
                title = "The Wise Man's Fear",
                authors = listOf("Patrick Rothfuss"),
                read = true,
                owned = false,
                dateRead = LocalDate.of(2016, 5,4),
                starRating = 5.0,
                publishers = listOf("Daw Books"),
                numberOfPages = 994,
                publishDate = "2011-03"
            ))
    }

    @Test
    internal fun getReadBooksHandlesNullResponses() {
        Mockito.`when`(openLibraryClient.getDetails("9780756404734")).thenReturn(
            Mono.empty())

        webClient.get().uri("/books/read")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList<BookResponse>().hasSize(1)
            .contains( BookResponse(
                isbn = "9780756404734",
                title = "The Wise Man's Fear",
                authors = listOf("Patrick Rothfuss"),
                read = true,
                owned = false,
                dateRead = LocalDate.of(2016, 5,4),
                starRating = 5.0,
                publishers = null,
                numberOfPages = null,
                publishDate = null
            ))
    }
}
