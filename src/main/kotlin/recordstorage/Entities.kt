package recordstorage

import java.math.BigDecimal
import java.util.*

class Amount(
    var value: BigDecimal,
    var currency: Currency = Currency.getInstance("HKD")
)

class BudgetRecord(
    var description: String,
    var amount: Amount,
    var paymentMethod: String,
    var category: String
)

class ProcessRecordRequest(
    var data: BudgetRecord,
    var accessToken: String
)

class SuccessResponse(
    var code: Int = 0,
    var message: String
)