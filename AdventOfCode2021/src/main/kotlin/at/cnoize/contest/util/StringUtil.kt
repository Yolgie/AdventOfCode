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

fun printlnIfNotNull(message: String?) {
    if (!message.isNullOrBlank()) {
        println(message)
    }
}
