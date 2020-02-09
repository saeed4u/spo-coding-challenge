package saeed.spo.challenge.scheduling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiser
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiserFactory
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiserType

@SpringBootApplication
class CleaningOptimizationApplication {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val input = arrayOf(arrayOf(0, 1), arrayOf(0, 3), arrayOf(4, 5), arrayOf(5, 6), arrayOf(4, 10))
            println(numOfPrizes(4, arrayOf(2, 2, 3, 4, 5)))
        }

        fun numOfPrizes(k: Int, marks: Array<Int>): Int {
            marks.sort()
            val ranks = arrayListOf<Int>()
            var previous = 1
            var rank = 0
            var noOfDup = 0
            var index = 0
            for (i in marks) {
                if (i == 0) {
                    continue
                }
                if (previous != i) {
                    rank++

                } else {
                    noOfDup++
                }
                if (ranks.size > 1 && ranks[index] == ranks[index-1]){
                    rank++
                }
                previous = i
                ranks.add(rank)
                index++
            }
            var noOfStudents = 0
            ranks.forEach {
                if (noOfStudents < k) {
                    noOfStudents
                }
            }
            return noOfStudents + noOfDup
        }
    }

    @Bean
    fun linearOptimiser(): ResourceOptimiser {
        return ResourceOptimiserFactory.makeOptimiser(ResourceOptimiserType.LINEAR)
    }
}