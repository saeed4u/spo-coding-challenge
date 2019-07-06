package saeed.spo.challenge.scheduling.common.application.service

import saeed.spo.challenge.scheduling.common.application.service.linear.LinearOptimiser

interface ResourceOptimiserFactory {

    companion object {
        fun makeOptimiser(type: ResourceOptimiserType): ResourceOptimiser = when (type) {
            ResourceOptimiserType.LINEAR -> LinearOptimiser()
            else -> throw IllegalArgumentException("Sorry we do not know how to create optimiser of type $type")
        }
    }

}