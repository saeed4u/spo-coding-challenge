package saeed.spo.challenge.scheduling.model.validation

import saeed.spo.challenge.scheduling.model.Message
import saeed.spo.challenge.scheduling.model.Result

class ValidationResult : Result {

    private val errors: Set<Message> = LinkedHashSet()

    override fun wasSuccessful(): Boolean {
        return errors.isEmpty()
    }

    fun addError(error: Message): ValidationResult {
        this.errors.plus(error)
        return this
    }

    override fun toString(): String {
        return Message.concatErrors(errors)
    }
}