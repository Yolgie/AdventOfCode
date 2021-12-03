package at.cnoize.contest.util

fun <RETURN_TYPE> String.asResource(work: (String) -> RETURN_TYPE): RETURN_TYPE {
    return Thread.currentThread().contextClassLoader
            .getResource(this)
            ?.readText()
            ?.let { work(it) }
            ?: throw IllegalArgumentException("Resource not found: $this")
}

fun Int.zeroPad(length: Int): String = this.toString().padStart(length, '0')
