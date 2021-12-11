package at.cnoize.contest.util

fun String.splitOnEmptyLine(): List<String> =
    this.split("\n\n")
        .filterNot(String::isNullOrEmpty)

fun String.splitOnNewLine(): List<String> =
    this.split("\n")
        .filterNot(String::isNullOrEmpty)

fun String.splitOnSpace(): List<String> =
    this.split(" ")
        .filterNot(String::isNullOrEmpty)

@Suppress("unused")
fun printlnIfNotNull(message: String?) {
    if (!message.isNullOrBlank()) {
        println(message)
    }
}

fun String.binaryToInt(): Int = Integer.parseInt(this, 2)

fun String.sorted(): String = this.toList().sorted().joinToString("")

fun String.containsAll(other: String): Boolean = other.all { this.contains(it) }

fun String.removeAll(other: String): String = this.filterNot(other::contains)

fun String.toListOfInt(): List<Int> = map { char -> char.toString().toInt() }
