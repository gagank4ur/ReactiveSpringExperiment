package com.example.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class Config {

    @Value("\${openlibrary.client.path}")
    lateinit var basePath: String


    @Bean
    fun getOpenLibraryWebClient(): WebClient {
        return WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create().followRedirect(true)
                )
            )
            .baseUrl(basePath)
            .build()
    }
}