package com.riopermana.network.datasource

import com.riopermana.common.csv.OpenCsvParser
import com.riopermana.common.exception.CsvParsingException
import com.riopermana.network.ApiService
import com.riopermana.network.dto.CompanyListingDto
import com.riopermana.network.fake.ErrorCsvParser
import com.riopermana.network.fake.IOErrorCsvParser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit


@OptIn(ExperimentalCoroutinesApi::class)
class StockRemoteDataSourceTest {

    private lateinit var dataSource: RemoteDataSource
    private lateinit var apiService: ApiService
    private val server = MockWebServer()


    @Before
    fun setup() {
        server.start(8080)
        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .build()
            .create(ApiService::class.java)
        val csvParser = OpenCsvParser()
        dataSource = StockRemoteDataSource(
            csvParser, apiService
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `test getCompanyListing with response 200 return data`() = runTest {
        val expected = listOf(
            CompanyListingDto(
                "A", "Agilent Technologies Inc", "NYSE"
            )
        )
        server.enqueue(
            MockResponse().setBody(
                """
                symbol,name,exchange,assetType,ipoDate,delistingDate,status
                A,Agilent Technologies Inc,NYSE,Stock,1999-11-18,null,Active
            """.trimIndent()
            )
        )

        val actual = dataSource.getCompanyListing()
        assertEquals(expected, actual)
    }

    @Test
    fun `test getCompanyListing with response 404 throws HttpException`() = runTest {
        server.enqueue(
            MockResponse().setResponseCode(404)
        )

        assertThrows(HttpException::class.java) {
            runBlocking {
                dataSource.getCompanyListing()
            }
        }
    }

    @Test
    fun `test getCompanyListing with response 200 throws CsvParsingException`() = runTest {
        val errorCsvParser = ErrorCsvParser()
        val dataSourceError = StockRemoteDataSource(errorCsvParser, apiService)

        server.enqueue(
            MockResponse().setResponseCode(200)
        )

        assertThrows(CsvParsingException::class.java) {
            runBlocking {
                dataSourceError.getCompanyListing()
            }
        }
    }

    @Test
    fun `test getCompanyListing with response 200 throws IOException`() = runTest {
        val errorCsvParser = IOErrorCsvParser()
        val dataSourceError = StockRemoteDataSource(errorCsvParser, apiService)

        server.enqueue(
            MockResponse().setResponseCode(200)
        )

        assertThrows(IOException::class.java) {
            runBlocking {
                dataSourceError.getCompanyListing()
            }
        }
    }
}