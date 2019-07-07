package saeed.spo.challenge.scheduling.common.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import saeed.spo.challenge.scheduling.common.application.exception.ValidationException
import saeed.spo.challenge.scheduling.common.application.service.OptimiseResourceService
import saeed.spo.challenge.scheduling.common.model.ApiResponse
import saeed.spo.challenge.scheduling.common.model.Building

@RestController
@RequestMapping("/api/optimiser")
class OptimiserController(private val service: OptimiseResourceService) {

    @Throws(ValidationException::class)
    @PostMapping
    fun optimiser(@RequestBody building: Building): ResponseEntity<ApiResponse> {
        return ResponseEntity(service.optimiseResource(building), HttpStatus.OK)
    }

}