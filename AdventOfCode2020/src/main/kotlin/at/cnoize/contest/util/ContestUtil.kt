package at.cnoize.contest.util

import java.math.BigInteger

fun <RETURN_TYPE> String.asResource(work: (String) -> RETURN_TYPE): RETURN_TYPE {
    return Thread.currentThread().contextClassLoader
            .getResource(this)
            ?.readText()
            ?.let { work(it) }
            ?: throw IllegalArgumentException("Resource not found: $this")
}

fun Int.zeroPad(length: Int): String = this.toString().padStart(length, '0')

fun <T> Iterable<T>.update(index: Int, element: T) =
        take(index) + element + drop(index + 1)

fun Collection<BigInteger>.sum(): BigInteger =
        this.sumOf { it }

fun <T> Iterable<T>.second(): T = this.drop(1).first()
