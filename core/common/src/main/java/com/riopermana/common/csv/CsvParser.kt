package com.riopermana.common.csv

import java.io.InputStream

/**
 * A base interface for parsing CSV (Comma-Separated Values) data from an input stream.
 * */
interface CsvParser {

    /**
     * Asynchronously parses CSV (Comma-Separated Values) data from the provided input stream and returns a list of arrays of strings.
     * The resulting data is returned as a list of arrays of strings started,
     * where each array represents a row in the CSV data,
     * and each string represents a value in the corresponding column.
     *
     * Don't call this function from Main Thread.
     *
     * @param stream The input stream containing the CSV data to be parsed.
     * @return A list of arrays of strings representing the parsed CSV data row in the CSV data.
     */
    suspend fun parse(stream: InputStream): List<Array<String>>
}