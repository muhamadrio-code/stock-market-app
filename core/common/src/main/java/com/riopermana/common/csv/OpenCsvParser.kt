package com.riopermana.common.csv

import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import com.riopermana.common.exception.CsvParsingException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * CSV parser backed by OpenCSV.
 * **/
class OpenCsvParser : CsvParser {

    /**
     * Parses CSV (Comma-Separated Values) data from the provided input stream and returns a list of arrays of strings.
     * The resulting data is returned as a list of arrays of strings started row in the CSV data,
     * where each array represents a row in the CSV data,
     * and each string represents a value in the corresponding column.
     *
     * Don't call this function from Main Thread.
     *
     * @param stream The input stream containing the CSV data to be parsed.
     * @return A list of arrays of strings representing the parsed CSV data row in the CSV data.
     * @throws IOException If an error occurs while reading or parsing the CSV data.
     * @throws CsvParsingException If an error occurs while reading or parsing the CSV data.
     */
    @Throws(IOException::class, CsvParsingException::class)
    override suspend fun parse(stream: InputStream): List<Array<String>> {
        return try {
            CSVReader(InputStreamReader(stream)).readAll()
        } catch (e: IOException) {
            throw e
        } catch (e: CsvException) {
            throw CsvParsingException(e)
        }
    }
}