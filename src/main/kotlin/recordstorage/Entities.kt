package recordstorage

import common.entities.BudgetRecord

class ProcessRecordRequest(
    var data: BudgetRecord,
    var accessToken: String
)

class SuccessResponse(
    var code: Int = 0,
    var message: String
)