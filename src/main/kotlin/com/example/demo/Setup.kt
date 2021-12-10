package com.example.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths

@Component
class Setup(val importService: ImportService) : CommandLineRunner {

    @Value("\${seed.data.path}")
    lateinit var path: String


    override fun run(vararg args: String?) {
        val bufferedReader = Files.newBufferedReader(Paths.get(path));
        importService.importCSV(bufferedReader)
    }
}