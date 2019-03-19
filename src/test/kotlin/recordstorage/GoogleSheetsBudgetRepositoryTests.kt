package recordstorage

import com.google.api.client.auth.oauth2.Credential
import common.Amount
import common.BudgetRecord
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.math.BigDecimal

class GoogleSheetsBudgetRepositoryTests {
    val logger = LoggerFactory.getLogger(GoogleSheetsBudgetRepositoryTests::class.java)!!

    @Test
    fun `Connects to Google Sheets after AuthN`() {
        val deps = TestGoogleDependenciesProvider()
        val repository = TestGoogleSheetsBudgetRepository(deps)
        val record = BudgetRecord("desc", Amount(BigDecimal(1.2)), "credit", "groceries")
        repository.processRecord(record, "")

        val accessToken = (deps.httpRequestInitializer as Credential).accessToken
        assert(accessToken.isNotEmpty())
        logger.debug("accessToken: %s".format(accessToken))

        val service = repository.sheetsService
        val response = service!!.spreadsheets().values()
            .get(repository.sheet, repository.range)
            .execute()
        assert(response.getValues().count() > 0)
    }

}