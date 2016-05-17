package io.polymorphicpanda.kspec.tag

class Tag(val name: String) {
    init {
        if (name.isEmpty()) {
            throw IllegalArgumentException("Tag name can't be empty.")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Tag

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int{
        return name.hashCode()
    }
}
