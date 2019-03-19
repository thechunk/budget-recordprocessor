package recordstorage

import org.springframework.stereotype.Service

@Service
class GoogleSheetsBudgetService(override val budgetRepository: GoogleSheetsBudgetRepository) : BudgetService