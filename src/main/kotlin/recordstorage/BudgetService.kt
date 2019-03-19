package recordstorage

import common.BudgetRecord

interface BudgetService {
    val budgetRepository: BudgetRepository

    fun processRecord(record: BudgetRecord, accessToken: String) {
        budgetRepository.processRecord(record, accessToken)
    }
}