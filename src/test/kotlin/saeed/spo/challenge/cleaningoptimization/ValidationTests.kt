package saeed.spo.challenge.cleaningoptimization

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import saeed.spo.challenge.scheduling.common.model.Building
import saeed.spo.challenge.scheduling.common.model.Message
import saeed.spo.challenge.scheduling.common.validation.BuildingValidator
import saeed.spo.challenge.scheduling.util.*
import kotlin.random.Random

class ValidationTests {

    private lateinit var buildings: ArrayList<Building>

    val random = Random(100)

    @BeforeEach
    fun init() {
        buildings = arrayListOf()
        for (i in 1..random.nextInt(50, 95)) {
            buildings.add(Building(listOf(1, 2, 4, 5, 4, 2), 4, 3))
        }
    }

    private fun generateRoomList(size: Int = 50, minRoom: Int = 1, maxRoom: Int = 50): List<Int> {
        val rooms = arrayListOf<Int>()
        for (i in 1..size) {
            rooms.add(random.nextInt(minRoom, maxRoom))
        }
        return rooms
    }

    @Test
    fun testForWhenThereIsTooManyBuildings() {
        val generatedBuildingSize = buildings.size
        for (i in generatedBuildingSize..101) {
            buildings.add(Building(listOf(1, 2, 4, 5, 4, 2), 4, 3))
        }
        val validator = BuildingValidator(buildings)
        val validationResult = validator.validate()
        val buildingTooManyError = validationResult.getErrors().filter {
            it == Message.message(MAX_BUILDING_COUNT_ERROR_MSG)
        }
        assertThat(1).isEqualTo(buildingTooManyError.size)
        assertThat(Message.message(MAX_BUILDING_COUNT_ERROR_MSG)).isEqualTo(buildingTooManyError.first())
    }

    @Test
    fun testForWhenThereIsNoBuilding() {
        buildings.clear()
        val validator = BuildingValidator(buildings)
        val validationResult = validator.validate()
        val noBuildingError = validationResult.getErrors().filter {
            it == Message.message(MIN_BUILDING_COUNT_ERROR_MSG)
        }
        assertThat(1).isEqualTo(noBuildingError.size)
        assertThat(Message.message(MIN_BUILDING_COUNT_ERROR_MSG)).isEqualTo(noBuildingError.first())
    }

    @Test
    fun testForWhenBuildingHasNoRoom() {
        buildings.clear()
        buildings.add(Building(listOf(), 5, 4))
        val validator = BuildingValidator(buildings)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(1).isEqualTo(errors.size)
        assertThat(Message.message(NO_ROOM_IN_BUILDING_ERROR_MSG)).isEqualTo(errors.first())

    }

    @Test
    fun testForWhenRoomCountIsMoreThanMax() {
        val rooms = generateRoomList(2, 101, 105)
        val generatedBuildingSize = buildings.size
        for (i in generatedBuildingSize..99) {
            buildings.add(Building(rooms, 4, 3))
        }
        val validator = BuildingValidator(buildings)
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
        val rooms = generateRoomList(2, -1, 0)
        val generatedBuildingSize = buildings.size
        for (i in generatedBuildingSize..98) {
            buildings.add(Building(rooms, 4, 3))
        }
        val validator = BuildingValidator(buildings)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(1).isEqualTo(errors.size)
        assertThat(Message.message(MIN_ROOM_COUNT_ERROR_MSG)).isEqualTo(errors.first())

    }

    @Test
    fun testForWhenSeniorCapacityIsLessThanMin() {
        val rooms = generateRoomList(2, 10, 95)
        val generatedBuildingSize = buildings.size
        for (i in generatedBuildingSize..99) {
            buildings.add(Building(rooms, 0, 3))
        }
        val validator = BuildingValidator(buildings)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(1).isEqualTo(errors.size)
        assertThat(Message.message(MIN_CLEANING_CAPACITY_SENIOR_ERROR_MSG)).isEqualTo(errors.first())
    }

    @Test
    fun testForWhenCapacityIsLessThanMin() {
        val rooms = generateRoomList(2, 10, 95)
        val generatedBuildingSize = buildings.size
        for (i in generatedBuildingSize..99) {
            buildings.add(Building(rooms, 0, 0))
        }
        val validator = BuildingValidator(buildings)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(3).isEqualTo(errors.size)
        assertThat(Message.message(MIN_CLEANING_CAPACITY_SENIOR_ERROR_MSG)).isIn(errors)
        assertThat(Message.message(MIN_CLEANING_CAPACITY_JUNIOR_ERROR_MSG)).isIn(errors)
        assertThat(Message.message(JUNIOR_CLEANER_CAPACITY_GREATER_EQUAL_TO_SENIOR_CLEANER)).isIn(errors)
    }

    @Test
    fun testForWhenInputDataIsValidAndCorrect() {
        val validator = BuildingValidator(buildings)
        val validationResult = validator.validate()
        val errors = validationResult.getErrors()
        assertThat(0).isEqualTo(errors.size)
    }

}