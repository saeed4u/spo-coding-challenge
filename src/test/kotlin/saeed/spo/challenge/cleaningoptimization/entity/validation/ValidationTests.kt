package saeed.spo.challenge.cleaningoptimization.entity.validation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import saeed.spo.challenge.scheduling.common.model.Building
import saeed.spo.challenge.scheduling.common.model.Message
import saeed.spo.challenge.scheduling.common.validation.BuildingValidator
import saeed.spo.challenge.scheduling.util.*
import kotlin.random.Random

class ValidationTests {

    private lateinit var building: Building

    val random = Random(100)

    private fun generateBuilding(size: Int = 50, minRoom: Int = 1, maxRoom: Int = 50, seniorCapacity: Int = 10, juniorCapacity: Int = 6) {
        val rooms = arrayListOf<Int>()
        for (i in 1..size) {
            rooms.add(random.nextInt(minRoom, maxRoom))
        }
        building = Building(rooms,seniorCapacity,juniorCapacity)
    }

    @Test
    fun testForWhenThereIsTooManyBuildings() {
        generateBuilding(102)
        val validator = BuildingValidator(building)
        val validationResult = validator.validate()
        val buildingTooManyError = validationResult.getErrors().filter {
            it == Message.message(MAX_BUILDING_COUNT_ERROR_MSG)
        }
        assertThat(1).isEqualTo(buildingTooManyError.size)
        assertThat(Message.message(MAX_BUILDING_COUNT_ERROR_MSG)).isEqualTo(buildingTooManyError.first())
    }

    @Test
    fun testForWhenThereIsNoBuilding() {
        generateBuilding(0)
        val validator = BuildingValidator(building)
        val validationResult = validator.validate()
        val noBuildingError = validationResult.getErrors().filter {
            it == Message.message(MIN_BUILDING_COUNT_ERROR_MSG)
        }
        assertThat(1).isEqualTo(noBuildingError.size)
        assertThat(Message.message(MIN_BUILDING_COUNT_ERROR_MSG)).isEqualTo(noBuildingError.first())
    }

    @Test
    fun testForWhenRoomCountIsMoreThanMax() {
        generateBuilding(minRoom = 100, maxRoom = 104)
        val validator = BuildingValidator(building)
        val validationResult = validator.validate()
        System.out.println(validationResult)
        val errors = validationResult.getErrors()
        assertThat(1).isEqualTo(errors.size)
        val roomTooManyError = errors.filter {
            it == Message.message(MAX_ROOM_COUNT_ERROR_MSG)
        }
        assertThat(1).isEqualTo(roomTooManyError.size)

    }

    @Test
    fun testForWhenRoomCountIsLessThanMin() {
        generateBuilding(minRoom = 0,maxRoom = 1)
        val validator = BuildingValidator(building)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(1).isEqualTo(errors.size)
        assertThat(Message.message(MIN_ROOM_COUNT_ERROR_MSG)).isEqualTo(errors.first())

    }

    @Test
    fun testForWhenSeniorCapacityIsLessThanMin() {
        generateBuilding(seniorCapacity = 0,juniorCapacity = 1)
        val validator = BuildingValidator(building)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(2).isEqualTo(errors.size)
        assertThat(Message.message(MIN_CLEANING_CAPACITY_SENIOR_ERROR_MSG)).isEqualTo(errors.first())
    }

    @Test
    fun testForWhenCapacityIsLessThanMin() {
        generateBuilding(seniorCapacity = 0,juniorCapacity = 0)
        val validator = BuildingValidator(building)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(3).isEqualTo(errors.size)
        assertThat(Message.message(MIN_CLEANING_CAPACITY_SENIOR_ERROR_MSG)).isIn(errors)
        assertThat(Message.message(MIN_CLEANING_CAPACITY_JUNIOR_ERROR_MSG)).isIn(errors)
        assertThat(Message.message(JUNIOR_CLEANER_CAPACITY_GREATER_EQUAL_TO_SENIOR_CLEANER)).isIn(errors)
    }

    @Test
    fun testForWhenInputDataIsValidAndCorrect() {
        generateBuilding()
        val validator = BuildingValidator(building)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(0).isEqualTo(errors.size)
    }

}