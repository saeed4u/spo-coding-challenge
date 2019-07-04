package saeed.spo.challenge.scheduling.model

interface Result {
    fun wasSuccessful(): Boolean

    fun failed(): Boolean {
        return !wasSuccessful()
    }
}