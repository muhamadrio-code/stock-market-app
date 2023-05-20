package fake

import com.riopermana.network.datasource.RemoteDataSource
import com.riopermana.network.dto.CompanyListingDto

class FakeStockRemoteDataSource :RemoteDataSource {
    override suspend fun getCompanyListing(): List<CompanyListingDto> {
        return listOf(
            CompanyListingDto("A", "Agilent Technologies Inc", "NYSE"),
            CompanyListingDto("AA", "Alcoa Corp", "NYSE"),
            CompanyListingDto("AAA", "AAX FIRST PRIORITY CLO BOND ETF", "NYSE ARCA"),
            CompanyListingDto("AEAE", "AltEnergy Acquisition Corp - Class A", "NASDAQ"),
            CompanyListingDto("ADXN", "Addex Therapeutics Ltd", "NASDAQ"),
            CompanyListingDto("AA", "Aether Ltd", "NASDAQ"),
        )
    }
}