package saeed.spo.challenge.cleaningoptimization.service

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import saeed.spo.challenge.scheduling.common.application.dto.OptimiserResponse
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiser
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiserFactory
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiserType
import saeed.spo.challenge.scheduling.common.application.service.linear.LinearOptimiser
import saeed.spo.challenge.scheduling.common.model.Building


class LinearOptimiserTest {

    lateinit var linearOptimiser: ResourceOptimiser

    private val logger = LoggerFactory.getLogger(LinearOptimiser::class.java)

    @BeforeEach
    fun init() {
        linearOptimiser = ResourceOptimiserFactory.makeOptimiser(ResourceOptimiserType.LINEAR)
    }

    @Test
    fun testOptimiserExpect2_1_2_1() {
        val building = Building(listOf(24,28), 11, 6)
        val results = linearOptimiser.optimiseResource(building)
        assertThat(2).isEqualTo(results.size)
        val expectedResults = listOf(OptimiserResponse(2,1),OptimiserResponse(2,1))
        assertThat(expectedResults).isEqualTo(results)
    }

    @Test
    fun testOptimiserExpect3_1_1_2(){
        val building = Building(listOf(35,21), 10, 6)
        val results = linearOptimiser.optimiseResource(building)
        assertThat(2).isEqualTo(results.size)
        val expectedResults = listOf(OptimiserResponse(3,1),OptimiserResponse(1,2))
        assertThat(expectedResults).isEqualTo(results)
    }

    @Test
    fun testOptimiserExpect1_3(){
        val building = Building(listOf(28), 10, 6)
        val results = linearOptimiser.optimiseResource(building)
        assertThat(1).isEqualTo(results.size)
        val expectedResults = listOf(OptimiserResponse(1,3))
        assertThat(expectedResults).isEqualTo(results)
    }

    @Test
    fun testOptimiserExpectOneSenior(){
        val building = Building(listOf(8), 10, 6)
        val results = linearOptimiser.optimiseResource(building)
        assertThat(1).isEqualTo(results.size)
        val expectedResults = listOf(OptimiserResponse(1,0))
        assertThat(expectedResults).isEqualTo(results)
    }

    @Test
    fun testOptimiserExpectOneSeniorAndOneJunior(){
        val building = Building(listOf(16), 10, 6)
        val results = linearOptimiser.optimiseResource(building)
        assertThat(1).isEqualTo(results.size)
        val expectedResults = listOf(OptimiserResponse(1,1))
        assertThat(expectedResults).isEqualTo(results)
    }

    @Test
    fun testOptimiserOneRoomSizeExpectOneSenior(){
        val building = Building(listOf(1), 10, 6)
        val results = linearOptimiser.optimiseResource(building)
        assertThat(1).isEqualTo(results.size)
        val expectedResults = listOf(OptimiserResponse(1,0))
        assertThat(expectedResults).isEqualTo(results)
    }


}