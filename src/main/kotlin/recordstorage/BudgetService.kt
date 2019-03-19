package recordstorage

import common.entities.BudgetRecord

interface BudgetService {
    val budgetRepository: BudgetRepository

    fun processRecord(record: BudgetRecord, accessToken: String) {
        budgetRepository.processRecord(record, accessToken)
    }
}