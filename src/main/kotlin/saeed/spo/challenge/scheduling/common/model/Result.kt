package saeed.spo.challenge.scheduling.common.model

interface Result {
    fun wasSuccessful(): Boolean

    fun failed(): Boolean {
        return !wasSuccessful()
    }
}