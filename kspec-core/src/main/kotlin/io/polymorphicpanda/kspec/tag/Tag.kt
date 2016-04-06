package io.polymorphicpanda.kspec.tag

data class Tag(val name: String, val data: Map<String, Any> = mapOf()) {

    init {
        if (name.isEmpty()) {
            throw IllegalArgumentException("Tag name can't be empty.")
        }
    }

    inline operator fun <reified T> get(key: String): T = data[key] as T
}
