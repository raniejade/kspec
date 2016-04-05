package io.polymorphicpanda.kspec.tag

import java.util.*

class Tag(val name: String, val data: Map<String, Any> = mapOf()) {
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

inline fun tag(name: String, block: MutableMap<String, Any>.() -> Unit): Tag {
    if (name.isEmpty()) {
        throw IllegalArgumentException("Tag name can't be empty.")
    }

    val data = HashMap<String, Any>()
    block.invoke(data)
    return Tag(name.toLowerCase(), data);
}
