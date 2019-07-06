package saeed.spo.challenge.scheduling.common.application.service

import saeed.spo.challenge.scheduling.common.application.dto.OptimiserResponse
import saeed.spo.challenge.scheduling.common.model.Building

interface ResourceOptimiser {

    fun optimiseResource(buildings: List<Building>): List<List<OptimiserResponse>>

}