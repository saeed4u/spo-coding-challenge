package saeed.spo.challenge.scheduling.common.validation

import org.springframework.validation.Errors
import org.springframework.validation.Validator
import saeed.spo.challenge.scheduling.common.model.Building
import saeed.spo.challenge.scheduling.util.*


class CleaningTaskValidator : Validator {

    override fun validate(task: Any, errors: Errors) {
        val cleaningTask = task as Building

        val roomMinErrors = cleaningTask.rooms.filter {
            it < MIN_ROOM_COUNT
        }

        if (roomMinErrors.isNotEmpty()) {
            errors.reject(MIN_ROOM_COUNT_ERROR_MSG)
        }

        val roomMaxErrors = cleaningTask.rooms.filter {
            it > MAX_ROOM_COUNT
        }

        if (roomMaxErrors.isNotEmpty()) {
            errors.reject(MAX_ROOM_COUNT_ERROR_MSG)
        }

        if (cleaningTask.seniorCleanerCapacity < MIN_CLEANING_CAPACITY) {
            errors.reject(MIN_CLEANING_CAPACITY_SENIOR_ERROR_MSG)
        }

        if (cleaningTask.juniorCleaningCapacity < MIN_CLEANING_CAPACITY) {
            errors.reject(MIN_CLEANING_CAPACITY_JUNIOR_ERROR_MSG)
        }

        if (cleaningTask.seniorCleanerCapacity <= cleaningTask.juniorCleaningCapacity) {
            errors.reject(JUNIOR_CLEANER_CAPACITY_GREATER_EQUAL_TO_SENIOR_CLEANER)
        }

    }

    override fun supports(clazz: Class<*>): Boolean {
        return Building::class.java.isAssignableFrom(clazz)
    }
}