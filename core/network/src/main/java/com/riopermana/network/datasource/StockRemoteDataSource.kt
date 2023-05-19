package com.riopermana.network.datasource

import com.riopermana.common.csv.CsvParser
import com.riopermana.common.exception.CsvParsingException
import com.riopermana.network.ApiService
import com.riopermana.network.BuildConfig
import com.riopermana.network.dto.CompanyListingDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Remote data source for fetching stock market data.
 * This class is responsible for fetching data from a remote data source.
 * It provides methods to retrieve information from Restful-API.
 */
@Singleton
class StockRemoteDataSource @Inject constructor(
    private val csvParser: CsvParser,
    private val apiService: ApiService,
) : RemoteDataSource {

    /**
     * Suspended function for fetching a list of company listings from a remote data source.
     *
     * @return A list of CompanyListingDto objects representing the company listings.
     * @see CompanyListingDto
     */
    @Throws(IOException::class, CsvParsingException::class, HttpException::class)
    override suspend fun getCompanyListing(): List<CompanyListingDto> {
        val parsedResponse = apiService
            .getListings(BuildConfig.API_KEY)
            .byteStream()
            .use { stream ->
                csvParser.parse(stream).drop(1)
            }

        return parsedResponse.map {
            CompanyListingDto(
                symbol = it[0],
                name = it[1],
                exchange = it[2],
            )
        }
    }
}