package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList

@AutoConfigureWebTestClient
@SpringBootTest
internal class BookControllerTest {

    @Autowired
    lateinit var webClient: WebTestClient

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
}
