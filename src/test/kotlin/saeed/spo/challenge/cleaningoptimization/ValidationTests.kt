package saeed.spo.challenge.cleaningoptimization

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import saeed.spo.challenge.scheduling.model.Building
import saeed.spo.challenge.scheduling.model.Message
import saeed.spo.challenge.scheduling.model.validation.BuildingValidator
import saeed.spo.challenge.scheduling.util.MAX_BUILDING_COUNT_ERROR_MSG
import saeed.spo.challenge.scheduling.util.MIN_BUILDING_COUNT_ERROR_MSG
import kotlin.random.Random

class ValidationTests {

    private lateinit var buildings: ArrayList<Building>

    val random = Random(100)

    @BeforeEach
    fun init() {
        buildings = arrayListOf()
        for (i in 1..random.nextInt(50, 98)) {
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
        val buildingTooManyError = validationResult.getErrors().filter {
            it == Message.message(MIN_BUILDING_COUNT_ERROR_MSG)
        }
        assertThat(1).isEqualTo(buildingTooManyError.size)
        assertThat(Message.message(MIN_BUILDING_COUNT_ERROR_MSG)).isEqualTo(buildingTooManyError.first())
    }

}