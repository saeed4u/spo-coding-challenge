package saeed.spo.challenge.scheduling.common.application.service.linear

import saeed.spo.challenge.scheduling.common.application.dto.OptimiserResponse
import saeed.spo.challenge.scheduling.common.application.service.ResourceOptimiser
import saeed.spo.challenge.scheduling.common.model.Building
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.min

class LinearOptimiser : ResourceOptimiser {


    override fun optimiseResource(building: Building): List<OptimiserResponse> {
        return findOptimisation(building)
    }

    private fun findOptimisation(building: Building): List<OptimiserResponse> {
        val rooms = building.rooms
        val optimizedResult = arrayListOf<OptimiserResponse>()
        rooms.forEach {
            //senior capacity >=1
            val initialSeniorCount = ceil(it.toDouble() / building.senior).toInt()
            //junior capacity >=0
            val initialJuniorCount = 0
            val results = optimise(building,it, getGreatestCommonDivisor(building.senior, building.junior), initialSeniorCount, initialJuniorCount, initialSeniorCount, initialJuniorCount)
            optimizedResult.add(OptimiserResponse(results[0], results[1]))
        }
        return optimizedResult
    }

    private fun getGreatestCommonDivisor(firstNum: Int, secondNum: Int): Int =
            if (secondNum == 0) firstNum else getGreatestCommonDivisor(secondNum, firstNum % secondNum)


    private fun optimise(building: Building,roomSize: Int, minInterval: Int, previousSeniorCount: Int, previousJuniorCount: Int, currentSeniorCount: Int, currentJuniorCount: Int): List<Int> {
        // a linear equation of senior and junior variables
        val staffCapacity = calculateStaffCapacity(building.senior,currentSeniorCount,building.junior,currentJuniorCount)
        val spaceLeft = abs(roomSize - staffCapacity)

        //do we have the space left lesser than what's allowed? and if so do we have equilibrium(staffCapacity >= room size)?
        if (spaceLeft <= minInterval && staffCapacity >= roomSize) {
            if (staffCapacity > roomSize){
                val currentStaffCapacityWithOnlySeniors = calculateStaffCapacity(building.senior,currentSeniorCount)
                if (currentSeniorCount > 1 && currentStaffCapacityWithOnlySeniors > roomSize){
                    return optimise(building,roomSize, minInterval, currentSeniorCount, currentJuniorCount, currentSeniorCount - 1, currentJuniorCount + 1)
                }
            }
            return listOf(currentSeniorCount, currentJuniorCount)
        }

        if (spaceLeft > min(building.senior, building.junior) && staffCapacity < roomSize) {
            return listOf(previousSeniorCount, previousJuniorCount)
        }

        //if we have over assignment, then reduce the number of senior cleaners
        if (staffCapacity > roomSize && currentSeniorCount > 1) {
            return optimise(building,roomSize, minInterval, currentSeniorCount, currentJuniorCount, currentSeniorCount - 1, currentJuniorCount)
        }

        //assign a junior cleaner if not optimal
        if (staffCapacity < roomSize) {
            return optimise(building,roomSize, minInterval, currentSeniorCount, currentJuniorCount, currentSeniorCount, currentJuniorCount + 1)
        }

        return listOf(currentSeniorCount, currentJuniorCount)

      //  return listOf(1,3)
    }

    private fun calculateStaffCapacity(seniorCapacity: Int, seniorCount: Int,juniorCapacity: Int =0, juniorCount: Int =0): Int{
        return (seniorCapacity * seniorCount) + (juniorCapacity * juniorCount)
    }

}