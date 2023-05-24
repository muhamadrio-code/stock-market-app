package fake

import com.riopermana.database.CompanyListingDao
import com.riopermana.database.entities.CompanyListingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class FakeCompanyListingsDao : CompanyListingDao {
    private val companyListings = mutableSetOf(
        CompanyListingEntity(1, "A", "Agilent Technologies Inc", "NYSE"),
        CompanyListingEntity(2, "AA", "Alcoa Corp", "NYSE"),
        CompanyListingEntity(3, "AAA", "AAX FIRST PRIORITY CLO BOND ETF", "NYSE ARCA"),
        CompanyListingEntity(4, "AEAE", "AltEnergy Acquisition Corp - Class A", "NASDAQ"),
        CompanyListingEntity(5, "ADXN", "Addex Therapeutics Ltd", "NASDAQ"),
        CompanyListingEntity(6, "AA", "Aether Ltd", "NASDAQ"),
    )

    override suspend fun clearCompanyListings() {
        companyListings.clear()
    }

    override suspend fun insertCompanyListingsOrAbort(list: List<CompanyListingEntity>) {
        companyListings.addAll(list)
    }

    override fun getCompanyListings(): Flow<List<CompanyListingEntity>> {
        return flowOf(companyListings.toList())
    }

    override fun getCompanyListings(query: String): Flow<List<CompanyListingEntity>> {
        return emptyFlow()
    }
}