package recordstorage

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*
import java.util.Arrays.asList

@Component
@Profile("!test")
open class GoogleSheetsBudgetRepository(deps: GoogleDependenciesProvider) : BudgetRepository {
    val logger = LoggerFactory.getLogger(GoogleSheetsBudgetRepository::class.java)!!
    val credential = GoogleCredential()
    val sheetsServiceBuilder = Sheets.Builder(deps.transport, deps.jsonFactory, null)
        .setApplicationName("Budget")!!

    open val sheet = "1jBs4Obb0hCPUm-3H6dUl-WttFj-AnEUqZWV3vS4Wrzg"
    open val range = "'Form Responses 2'!A1:ZZ"
    open var sheetsService: Sheets? = null
    val dateFormat = SimpleDateFormat("MM/dd/yy HH:mm:ss")

    override fun processRecord(record: BudgetRecord, accessToken: String) {
        credential.accessToken = accessToken
        sheetsServiceBuilder.httpRequestInitializer = credential
        sheetsService = sheetsServiceBuilder.build()

        val value = asList(asList(
            dateFormat.format(Date()),
            record.description,
            record.category,
            record.amount.value.toString(),
            record.paymentMethod
        )) as List<MutableList<String>>?
        val values = ValueRange().setValues(value)
        val res = sheetsService!!.spreadsheets().values().append(sheet, range, values)
            .setValueInputOption("USER_ENTERED")
            .execute()
        logger.info("%d updated".format(res.updates.updatedCells))
    }
}

@Component
@Profile("test")
class TestGoogleSheetsBudgetRepository(deps: TestGoogleDependenciesProvider) : GoogleSheetsBudgetRepository(deps) {
    override val sheet = "1jBs4Obb0hCPUm-3H6dUl-WttFj-AnEUqZWV3vS4Wrzg"
    override val range = "'Form Responses 2'!A1:ZZ"
    override var sheetsService: Sheets? = Sheets.Builder(deps.transport, deps.jsonFactory, deps.httpRequestInitializer)
        .setApplicationName("Google Sheets API Java Quickstart")
        .build()!!

    override fun processRecord(record: BudgetRecord, accessToken: String) {
    }
}