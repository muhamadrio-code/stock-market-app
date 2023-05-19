package com.riopermana.network.fake

import com.riopermana.common.csv.CsvParser
import com.riopermana.common.exception.CsvParsingException
import java.io.IOException
import java.io.InputStream

/**
 * CSV parser implementation for test purpose.
 */
internal class ErrorCsvParser : CsvParser {
    override suspend fun parse(stream: InputStream): List<Array<String>> {
        throw CsvParsingException()
    }
}

/**
 * CSV parser implementation for test purpose.
 */
internal class IOErrorCsvParser : CsvParser {
    override suspend fun parse(stream: InputStream): List<Array<String>> {
        throw IOException()
    }
}