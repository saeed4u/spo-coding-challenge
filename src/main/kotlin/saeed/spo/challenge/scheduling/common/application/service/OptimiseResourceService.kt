package saeed.spo.challenge.scheduling.common.application.service

import org.springframework.stereotype.Service
import saeed.spo.challenge.scheduling.common.application.exception.ValidationException
import saeed.spo.challenge.scheduling.common.model.ApiResponse
import saeed.spo.challenge.scheduling.common.model.Building
import saeed.spo.challenge.scheduling.common.validation.BuildingValidator

@Service
class OptimiseResourceService(private val linearOptimiser: ResourceOptimiser) {


    @Throws(ValidationException::class)
    fun optimiseResource(building: Building): ApiResponse{

        val validationResults = BuildingValidator(building).validate()
        if (validationResults.wasSuccessful()) {
            return ApiResponse(false, "Successfully optimised resources", linearOptimiser.optimiseResource(building))
        }
        throw ValidationException(validationResults.toString())
    }

}