package moe.feng.common.kt

operator fun String.invoke(vararg args: Any) = this.format(*args)

// Format strings by {}-styles

infix fun String.formatBy(args: Map<String, Any>): String = this.apply {
    for ((key, value) in args) {
        replace("{$key}".toRegex(), value.toString())
    }
}