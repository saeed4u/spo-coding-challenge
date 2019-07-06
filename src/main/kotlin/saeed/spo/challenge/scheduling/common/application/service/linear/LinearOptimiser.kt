package saeed.spo.challenge.scheduling.common.application.service.linear

import saeed.spo.challenge.scheduling.common.application.dto.OptimiserResponse
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiser
import saeed.spo.challenge.scheduling.common.model.Building
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.min

class LinearOptimiser : ResourceOptimiser {


    override fun optimiseResource(buildings: List<Building>): List<List<OptimiserResponse>> {
        val response = arrayListOf<List<OptimiserResponse>>()
        buildings.forEach {
            response.add(findOptimisation(it))
        }
        return response
    }

    private fun findOptimisation(building: Building): List<OptimiserResponse> {
        val rooms = building.rooms
        val optimizedResult = arrayListOf<OptimiserResponse>()
        rooms.forEach {
            //senior capacity >=1
            val initialSeniorCount = ceil(it.toDouble() / building.seniorCleanerCapacity).toInt()
            //junior capacity >=0
            val initialJuniorCount = 0
            val results = optimise(building, getGreatestCommonDivisor(building.seniorCleanerCapacity, building.juniorCleaningCapacity), initialSeniorCount, initialJuniorCount, initialSeniorCount, initialJuniorCount)
            optimizedResult.add(OptimiserResponse(results[0], results[1]))
        }
        return optimizedResult
    }

    private fun getGreatestCommonDivisor(firstNum: Int, secondNum: Int): Int =
            if (secondNum == 0) firstNum else getGreatestCommonDivisor(secondNum, firstNum % secondNum)


    private fun optimise(building: Building, minInterval: Int, previousSeniorCount: Int, previousJuniorCount: Int, currentSeniorCount: Int, currentJuniorCount: Int): List<Int> {
        // a linear equation of senior and junior variables
        val staffCapacity = (building.seniorCleanerCapacity * currentSeniorCount) + (building.juniorCleaningCapacity * previousJuniorCount)
        val spaceLeft = abs(building.rooms.size - staffCapacity)

        //if we have over assignment, then reduce the number of senior cleaners
        if (staffCapacity > building.rooms.size && currentSeniorCount > 1) {
            return optimise(building, minInterval, previousSeniorCount, previousJuniorCount, currentSeniorCount - 1, currentJuniorCount)
        }

        //assign a junior cleaner if not optimal
        if (staffCapacity < building.rooms.size) {
            return optimise(building, minInterval, previousSeniorCount, previousJuniorCount, currentSeniorCount, currentJuniorCount + 1)
        }

        //do we have the space left lesser than what's allowed? and if so do we have equilibrium(staffCapacity >= room size)?
        if (spaceLeft <= minInterval && staffCapacity <= building.rooms.size) {
            return listOf(currentSeniorCount, currentJuniorCount)
        }


        if (spaceLeft > min(building.seniorCleanerCapacity, building.juniorCleaningCapacity) && staffCapacity < building.rooms.size) {
            return listOf(previousSeniorCount, previousJuniorCount)
        }
        return listOf(currentSeniorCount, currentJuniorCount)

    }

}