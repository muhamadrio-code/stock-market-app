package com.riopermana.common.exception

class CsvParsingException(
    cause: Throwable = Exception("Something went wrong when parsing CSV file")
) : Exception(cause)