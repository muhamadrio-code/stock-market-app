package com.riopermana.domain

import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetCompanyListingsUseCaseTest {

    private lateinit var companyListingsRepository: CompanyListingsRepository
    private lateinit var sut: GetCompanyListingsUseCase

    @Before
    fun setUp() {
        companyListingsRepository = mockk()
        sut = GetCompanyListingsUseCase(companyListingsRepository)
    }

    @Test
    fun `test GetCompanyListings with null query return data`() = runTest {
        val expected = listOf(
            CompanyListings(1, "A", "Agilent Technologies Inc", "NYSE"),
            CompanyListings(2, "AA", "Alcoa Corp", "NYSE"),
        )
        every { companyListingsRepository.getCompanyListings() } returns flowOf(expected)

        val actual = sut().first()

        verify(exactly = 1) { companyListingsRepository.getCompanyListings() }

        assertEquals(expected, actual)
    }

    @Test
    fun `test GetCompanyListings with valid query return data`() = runTest {
        val expected = listOf(
            CompanyListings(1, "A", "Agilent Technologies Inc", "NYSE"),
            CompanyListings(2, "AA", "Oragil Corp", "NYSE"),
        )

        val query = "agil"
        every { companyListingsRepository.getCompanyListings(query) } returns flowOf(expected)

        val actual = sut(query).first()

        verify(exactly = 1) { companyListingsRepository.getCompanyListings(query) }

        assertEquals(expected, actual)
    }

    @Test
    fun `test GetCompanyListings with empty string query return data`() = runTest {
        val expected = listOf(
            CompanyListings(1, "A", "Agilent Technologies Inc", "NYSE"),
            CompanyListings(2, "AA", "Oragil Corp", "NYSE"),
        )

        val query = ""
        every { companyListingsRepository.getCompanyListings() } returns flowOf(expected)

        val actual = sut(query).first()
        verify(exactly = 1) { companyListingsRepository.getCompanyListings() }
        assertEquals(expected, actual)
    }
}