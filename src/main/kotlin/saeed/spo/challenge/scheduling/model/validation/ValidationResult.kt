package saeed.spo.challenge.scheduling.model.validation

import saeed.spo.challenge.scheduling.model.Message
import saeed.spo.challenge.scheduling.model.Result

class ValidationResult : Result {

   private val errors: HashSet<Message> = LinkedHashSet()

    override fun wasSuccessful(): Boolean {
        return errors.isEmpty()
    }

    fun addError(error: Message): ValidationResult {
        System.out.println("Adding an error")
        this.errors.add(error)
        return this
    }

    fun getErrors(): HashSet<Message>{
        return errors
    }

    override fun toString(): String {
        return Message.concatErrors(errors)
    }
}