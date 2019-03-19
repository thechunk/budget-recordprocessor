package recordstorage

import common.BudgetRecord

interface BudgetRepository {
    fun processRecord(record: BudgetRecord, accessToken: String)
}