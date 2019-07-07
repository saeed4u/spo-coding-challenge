package saeed.spo.challenge.scheduling.common.validation

import org.springframework.validation.DataBinder
import saeed.spo.challenge.scheduling.common.model.Building
import saeed.spo.challenge.scheduling.common.model.Message
import saeed.spo.challenge.scheduling.util.MAX_BUILDING_COUNT
import saeed.spo.challenge.scheduling.util.MAX_BUILDING_COUNT_ERROR_MSG
import saeed.spo.challenge.scheduling.util.MIN_BUILD_COUNT
import saeed.spo.challenge.scheduling.util.MIN_BUILDING_COUNT_ERROR_MSG

class BuildingValidator(private val building: Building) {


    fun validate(): ValidationResult {

        val validationResult = ValidationResult()

        //we first validate for building count

        //min check
        if (building.rooms.size < MIN_BUILD_COUNT) {
            return validationResult.addError(Message(MIN_BUILDING_COUNT_ERROR_MSG))
        }

        //max check
        if (building.rooms.size > MAX_BUILDING_COUNT) {
            return validationResult.addError(Message.message(MAX_BUILDING_COUNT_ERROR_MSG))
        }
        //we use Spring's validation engine to validate cleaning tasks
        val dataBinder = DataBinder(building)
        dataBinder.addValidators(CleaningTaskValidator())
        dataBinder.validate()
        val bindingResult = dataBinder.bindingResult
        if (bindingResult.hasErrors()) {
            bindingResult.allErrors.forEach { error ->
                validationResult.addError(Message.message(error.code!!))
            }
        }
        return validationResult

    }

}