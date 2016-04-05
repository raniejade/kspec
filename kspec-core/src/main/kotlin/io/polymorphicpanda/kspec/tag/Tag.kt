package io.polymorphicpanda.kspec.tag

class Tag(name: String, val data: Map<String, Any> = mapOf()) {

    val name = name.toLowerCase()

    init {
        if (name.isEmpty()) {
            throw IllegalArgumentException("Tag name can't be empty.")
        }
    }

    inline operator fun <reified T> get(key: String): T = data[key] as T

    override fun equals(other: Any?): Boolean{
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
