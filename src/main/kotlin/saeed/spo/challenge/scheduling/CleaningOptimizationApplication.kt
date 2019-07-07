package saeed.spo.challenge.scheduling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiser
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiserFactory
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiserType

@SpringBootApplication
class CleaningOptimizationApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<CleaningOptimizationApplication>(*args)
        }
    }

    @Bean
    fun linearOptimiser(): ResourceOptimiser {
        return ResourceOptimiserFactory.makeOptimiser(ResourceOptimiserType.LINEAR)
    }
}