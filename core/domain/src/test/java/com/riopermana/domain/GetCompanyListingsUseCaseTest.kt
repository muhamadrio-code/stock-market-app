package com.riopermana.domain

import com.google.common.truth.Truth.*
import com.riopermana.common.Resource
import com.riopermana.common.doWhen
import com.riopermana.common.helper.ResourcesHelper
import com.riopermana.common.isLoading
import com.riopermana.common.isSuccess
import com.riopermana.data.model.CompanyListings
import com.riopermana.data.repository.contract.CompanyListingsRepository
import com.riopermana.domain.test.TestStringResourcesHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private lateinit var resourcesHelper: ResourcesHelper<String>

    @Before
    fun setUp() {
        companyListingsRepository = mockk()
        resourcesHelper = TestStringResourcesHelper()
        sut = GetCompanyListingsUseCase(companyListingsRepository, resourcesHelper)
    }

    @Test
    fun `test get CompanyListings with empty database return resources loading`() = runTest {
        val expected = emptyList<CompanyListings>()
        every { companyListingsRepository.getCompanyListings() } returns flowOf(expected)

        val resources = sut().toList()
        val firstState = resources.first()
        val lastState = resources.last()

        verify(exactly = 1) { companyListingsRepository.getCompanyListings() }

        assertThat(firstState.isLoading).isTrue()
        assertThat(lastState.isLoading).isTrue()
    }

    @Test
    fun `test GetCompanyListings with null query return resources success`() = runTest {
        every { companyListingsRepository.getCompanyListings() } returns flowOf(
            companyListingsTestData
        )

        val resources = sut(query = null).toList()
        val firstState = resources.first()
        val lastState = resources.last()

        verify(exactly = 1) { companyListingsRepository.getCompanyListings() }

        assertThat(firstState.isLoading).isTrue()
        assertThat(lastState.isSuccess).isTrue()

        // verify data
        lastState.doWhen(success = { resultData ->
            assertEquals(companyListingsTestData, resultData)
        })
    }

    @Test
    fun `test GetCompanyListings with valid query return resources success`() = runTest {
        val query = "agil"
        every { companyListingsRepository.getCompanyListings(query) } returns flowOf(
            companyListingsTestData
        )

        val resources = sut(query = query).toList()
        val firstState = resources.first()
        val lastState = resources.last()

        verify(exactly = 1) { companyListingsRepository.getCompanyListings(query) }

        // assert that result is success
        assertThat(firstState.isLoading).isTrue()
        assertThat(lastState.isSuccess).isTrue()

        // verify data
        lastState.doWhen(success = { resultData ->
            assertEquals(companyListingsTestData, resultData)
        })
    }

    @Test
    fun `test GetCompanyListings with valid query return resources error with no data`() = runTest {
        val query = "agil"
        every { companyListingsRepository.getCompanyListings(query) } returns flowOf(emptyList())

        val resources = sut(query = query).toList()
        val firstState = resources.first()
        val lastState = resources.last()

        verify(exactly = 1) { companyListingsRepository.getCompanyListings(query) }

        assertThat(firstState.isLoading).isTrue()
        assertThat(lastState).isInstanceOf(Resource.Error::class.java)

        // verify data
        lastState.doWhen(error = { message, data, _ ->
            assertEquals(emptyList<CompanyListings>(), data)
            // ensure error messages are consistent with the design system
            assertEquals("${R.string.no_matches_query} \"$query\"", message)
        })
    }


    @Test
    fun `test GetCompanyListings with empty string query return resources success`() = runTest {
        val query = ""
        every { companyListingsRepository.getCompanyListings() } returns flowOf(
            companyListingsTestData
        )

        val resources = sut(query = query).toList()
        val firstState = resources.first()
        val lastState = resources.last()

        verify(exactly = 1) { companyListingsRepository.getCompanyListings() }

        assertThat(firstState.isLoading).isTrue()
        assertThat(lastState.isSuccess).isTrue()

        // verify data
        lastState.doWhen(success = { resultData ->
            assertEquals(companyListingsTestData, resultData)
        })
    }
}

val companyListingsTestData = listOf(
    CompanyListings("A", "Agilent Technologies Inc", "NYSE"),
    CompanyListings("AA", "Oragil Corp", "NYSE"),
)