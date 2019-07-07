package saeed.spo.challenge.scheduling.common.application.service

import saeed.spo.challenge.scheduling.common.application.dto.OptimiserResponse
import saeed.spo.challenge.scheduling.common.model.Building

interface ResourceOptimiser {

    fun optimiseResource(building: Building): List<OptimiserResponse>

}