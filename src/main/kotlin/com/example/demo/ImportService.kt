package com.example.demo

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class ImportService(val bookRepository: BookRepository) {

    fun importCSV(bufferedReader: BufferedReader) {

        val csvFormat = CSVFormat.Builder.create()
            .setHeader()
            .setIgnoreHeaderCase(true)
            .setTrim(true)
            .build()

        val csvParser = CSVParser(bufferedReader, csvFormat);

        for (csvRecord in csvParser) {
            val rawDate = csvRecord.get("Last Date Read")
            val rawRating = csvRecord.get("Star Rating")
            val book = Book(
                isbn = csvRecord.get("ISBN"),
                title = csvRecord.get("Title"),
                authors = csvRecord.get("Authors"),
                read = csvRecord.get("Read Status").equals("read"),
                owned = csvRecord.get("Owned?").equals("Yes"),
                dateRead = convertDate(rawDate),
                starRating = if (rawRating.isNotBlank()) rawRating.toDouble() else null,
            )



            bookRepository.save(book).subscribe {
                println(it.toString())
            }

        }
    }

    private fun convertDate(rawDate: String): LocalDate? =
        if (rawDate.isNotBlank())   LocalDate.parse(rawDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")) else null
}