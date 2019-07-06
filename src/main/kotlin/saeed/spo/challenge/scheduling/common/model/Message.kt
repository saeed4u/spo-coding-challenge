package saeed.spo.challenge.scheduling.common.model

import org.apache.commons.lang3.StringUtils

class Message(private val code: String) {

    override fun hashCode(): Int {
        return code.hashCode()
    }

    override fun toString(): String {
        return code
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (code != other.code) return false

        return true
    }

    companion object {
        @JvmStatic
        fun message(code: String) = Message(code)

        @JvmStatic
        fun concatErrors(errors: Collection<Message>): String {
            return StringUtils.join(errors.map { m -> m.code }, ", ")
        }

    }
}