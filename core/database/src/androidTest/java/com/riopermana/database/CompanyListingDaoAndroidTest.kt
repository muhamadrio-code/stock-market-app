package com.riopermana.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.riopermana.database.entities.CompanyListingEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@MediumTest
class CompanyListingDaoAndroidTest {

    private lateinit var database: StockDatabase
    private lateinit var dao: CompanyListingDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, StockDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = database.companyListingDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_insert_CompanyListingsEntity_ignore_on_conflict() = runTest {
        val input = listOf(
            CompanyListingEntity(
                symbol = "A", name = "Agilent Technologies Inc", exchange = "NYSE"
            )
        )

        val expected = listOf(
            CompanyListingEntity("A", "Agilent Technologies Inc", "NYSE"),
        )

        dao.insertOrIgnoreCompanyListings(input)

        val actual1 = dao.getCompanyListings().first()
        assertThat(actual1).isEqualTo(expected)

        dao.insertOrIgnoreCompanyListings(input)

        val actual2 = dao.getCompanyListings().first()
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun test_get_all_company_listings_return_data() = runTest {
        val input = listOf(
            CompanyListingEntity(
                symbol = "A", name = "Agilent Technologies Inc", exchange = "NYSE"
            ),
        )

        val expected = listOf(
            CompanyListingEntity("A", "Agilent Technologies Inc", "NYSE"),
        )

        dao.insertOrIgnoreCompanyListings(input)

        val actual = dao.getCompanyListings().first()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun test_get_company_listings_by_query_return_data() = runTest {
        val inputData = listOf(
            CompanyListingEntity("A", "Agilent Technologies Inc", "NYSE"),
            CompanyListingEntity("AA", "Alcoa Corp", "NYSE"),
            CompanyListingEntity("AAA", "AAX FIRST PRIORITY CLO BOND ETF", "NYSE ARCA"),
            CompanyListingEntity("AEAE", "AltEnergy Acquisition Corp - Class A", "NASDAQ"),
            CompanyListingEntity("ADXN", "Addex Therapeutics Ltd", "NASDAQ"),
            CompanyListingEntity("AA", "Aether Ltd", "NASDAQ"),
        )

        val expected1 = listOf(
            CompanyListingEntity("A", "Agilent Technologies Inc", "NYSE"),
        )

        val expected2 = listOf(
            CompanyListingEntity("AAA", "AAX FIRST PRIORITY CLO BOND ETF", "NYSE ARCA"),
        )

        val expected3 = listOf(
            CompanyListingEntity("AA", "Aether Ltd", "NASDAQ"),
        )

        val expected5 = listOf(
            CompanyListingEntity("AA", "Alcoa Corp", "NYSE"),
            CompanyListingEntity("AAA", "AAX FIRST PRIORITY CLO BOND ETF", "NYSE ARCA"),
            CompanyListingEntity("AA", "Aether Ltd", "NASDAQ"),
        )

        val query1 = "gil"
        val query2 = "AAA"
        val query3 = "Ae"
        val query4 = "A"
        val query5 = "AA"

        dao.insertOrIgnoreCompanyListings(inputData)

        val actual1 = dao.getCompanyListings(query1).first()
        val actual2 = dao.getCompanyListings(query2).first()
        val actual3 = dao.getCompanyListings(query3).first()
        val actual4 = dao.getCompanyListings(query4).first()
        val actual5 = dao.getCompanyListings(query5).first()

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
        assertThat(actual3).isEqualTo(expected3)
        assertThat(actual4).isEqualTo(inputData)
        assertThat(actual5).isEqualTo(expected5)
    }

}