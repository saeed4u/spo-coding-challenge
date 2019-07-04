package saeed.spo.challenge.scheduling.model.validation

import org.slf4j.LoggerFactory
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import saeed.spo.challenge.scheduling.model.Building
import saeed.spo.challenge.scheduling.util.*


class CleaningTaskValidator: Validator {

    private val logger = LoggerFactory.getLogger(CleaningTaskValidator::class.java)


    override fun validate(task: Any, errors: Errors) {
        logger.info("Validating a Building{}", task)
        val cleaningTask = task as Building

        val roomMinErrors = cleaningTask.rooms.filter{
            it < MIN_ROOM_COUNT
        }

        if (roomMinErrors.isNotEmpty()){
            errors.reject(MIN_ROOM_COUNT_ERROR_MSG)
        }

        val roomMaxErrors = cleaningTask.rooms.filter {
            it > MAX_ROOM_COUNT
        }

        if(roomMaxErrors.isNotEmpty()){
            errors.reject(MAX_ROOM_COUNT_ERROR_MSG)
        }


        if(cleaningTask.seniorCleanerCapacity < MIN_CLEANING_CAPACITY){
            errors.reject(MIN_CLEANING_CAPACITY_SENIOR_ERROR_MSG)
        }

        if(cleaningTask.juniorCleaningCapacity < MIN_CLEANING_CAPACITY){
            errors.reject(MIN_CLEANING_CAPACITY_JUNIOR_ERROR_MSG)
        }
    }

    override fun supports(clazz: Class<*>): Boolean {
        return Building::class.java.isAssignableFrom(clazz)
    }
}