package recordstorage

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/budget")
class HttpControllers (val budgetService: GoogleSheetsBudgetService) {
    @PostMapping("/record")
    fun processRecord(@RequestBody json: ProcessRecordRequest): ResponseEntity<SuccessResponse> {
        budgetService.processRecord(json.data, json.accessToken)
        return ResponseEntity(SuccessResponse(message = "Success"), HttpStatus.OK)
    }
}