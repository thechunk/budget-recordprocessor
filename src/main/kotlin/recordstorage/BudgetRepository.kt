package recordstorage

interface BudgetRepository {
    fun processRecord(record: BudgetRecord, accessToken: String)
}