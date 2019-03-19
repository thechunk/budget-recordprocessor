package recordstorage

interface BudgetService {
    val budgetRepository: BudgetRepository

    fun processRecord(record: BudgetRecord, accessToken: String) {
        budgetRepository.processRecord(record, accessToken)
    }
}