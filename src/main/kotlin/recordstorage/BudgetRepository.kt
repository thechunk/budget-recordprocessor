package recordstorage

import common.entities.BudgetRecord

interface BudgetRepository {
    fun processRecord(record: BudgetRecord, accessToken: String)
}