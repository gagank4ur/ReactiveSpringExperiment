package com.example.demo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration


internal class OpenLibraryClientTest {


    lateinit var openLibraryClient: OpenLibraryClient

    private val bookDetails = BookDetails(
        title = "The Poppy War",
        publishers = listOf("Harper Voyager"),
        numberOfPages = 544,
        publishDate = "2018",
    )


    @BeforeEach
    fun initialize() {
        val baseUrl = String.format(
            "http://localhost:%s",
            mockBackEnd.port
        )
        val webClient = WebClient.create(baseUrl)
        openLibraryClient = OpenLibraryClient(webClient)
    }

    @Test
    internal fun getsInfoForIsbn() {
        mockBackEnd.enqueue(
            MockResponse()
                .setBody(jacksonObjectMapper().writeValueAsString(bookDetails))
                .addHeader("Content-Type", "application/json")
        )

        val details: BookDetails = openLibraryClient.getDetails("9780062662590").block()!!
        assertThat(details).isEqualTo(bookDetails)

        val recordedRequest = mockBackEnd.takeRequest()

        assertThat(recordedRequest.method).isEqualTo("GET")
        assertThat(recordedRequest.path).isEqualTo("/isbn/9780062662590.json")

    }

    @Test
    internal fun returnsEmptyForErrors() {
        mockBackEnd.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(
                    "{\n" +
                            "    \"error\": \"notfound\",\n" +
                            "    \"key\": \"/isbn/B075DGHHQL\"\n" +
                            "}"
                )
        )

        val details: Mono<BookDetails> = openLibraryClient.getDetails("9780062662590")
        assertThat(details.hasElement().block()).isFalse

        val recordedRequest = mockBackEnd.takeRequest()

        assertThat(recordedRequest.method).isEqualTo("GET")
        assertThat(recordedRequest.path).isEqualTo("/isbn/9780062662590.json")

    }

    @Test
    internal fun returnsEmptyForBlankISBN() {
        val details: Mono<BookDetails> = openLibraryClient.getDetails("").timeout(Duration.ofSeconds(1))
        assertThat(details.hasElement().block()).isFalse

        assertThat(mockBackEnd.requestCount).isEqualTo(0)

    }


    companion object {
        lateinit var mockBackEnd: MockWebServer


        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            mockBackEnd = MockWebServer()
            mockBackEnd.start()
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            mockBackEnd.shutdown()

        }
    }
}