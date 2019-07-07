package saeed.spo.challenge.cleaningoptimization.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import saeed.spo.challenge.scheduling.CleaningOptimizationApplication
import saeed.spo.challenge.scheduling.common.application.dto.OptimiserResponse
import saeed.spo.challenge.scheduling.common.application.exception.ValidationException
import saeed.spo.challenge.scheduling.common.application.service.OptimiseResourceService
import saeed.spo.challenge.scheduling.common.model.Building


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [CleaningOptimizationApplication::class])
class OptimiseResourceServiceTest {

    @Autowired
    private lateinit var service: OptimiseResourceService


    @Test
    fun testForWhenPayloadIsValidExpectSuccess() {
        val building = Building(listOf(24, 28), 11, 6)
        val apiResponse = service.optimiseResource(building)
        assertThat(apiResponse).isNotNull
        assertThat(false).isEqualTo(apiResponse.error)
        assertThat("Successfully optimised resources").isEqualTo(apiResponse.message)

        val results = apiResponse.results
        assertThat(results).isNotNull
        assertThat(2).isEqualTo(results!!.size)
        val expected = listOf(OptimiserResponse(2,1), OptimiserResponse(2,1))
        assertThat(expected).isEqualTo(results)
    }

    @Test(expected = ValidationException::class)
    fun testForWhenPayloadIsNotValidExpectException(){
        val building = Building(listOf(), 11, 6)
        service.optimiseResource(building)
    }

}

