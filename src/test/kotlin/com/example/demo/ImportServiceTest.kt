package com.example.demo

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import java.nio.file.Files
import java.nio.file.Paths

@ExtendWith(MockitoExtension::class)
internal class ImportServiceTest {

    @Mock
    lateinit var bookRepository: BookRepository;

    lateinit var importService: ImportService

    @BeforeEach
    internal fun setUp() {
        importService = ImportService(bookRepository)
        `when`(bookRepository.save(any())).thenReturn(Mono.empty())
    }

    @Test
    internal fun testSetup() {
        val bufferedReader = Files.newBufferedReader(Paths.get("src/test/resources/testBooks.csv"));


        importService.importCSV(bufferedReader);

        verify(bookRepository).save(
            Book(
                isbn = "9781529118742",
                title = "Sorrowland",
                authors = "Rivers Solomon",
                read = false,
                owned = true,
                dateRead = null,
                starRating = null,
            )
        )
        verify(bookRepository, times(3)).save(any())
    }
}