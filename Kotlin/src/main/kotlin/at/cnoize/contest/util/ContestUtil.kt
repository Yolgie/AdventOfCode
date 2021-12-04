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

fun <T> Iterable<Iterable<T>>.transpose(): List<List<T>> {
        val matrix = this.map(Iterable<T>::toList)
        val cols = matrix.first().size
        val transposed = Array(cols) { emptyList<T>().toMutableList() }
        for (row in this) {
                for ((j, element) in row.withIndex()) {
                        transposed[j].add(element)
                }
        }
        return transposed.toList()
}

fun <T> Iterable<T>.zipWithNext(count: Int): List<List<T>> {
        return if (count == 1) {
                this.zipWithNext().map { pair -> pair.toList() }
        } else {
                this.zipWithNext(count-1)
                        .zip(this.drop(count))
                        .map { (pair, single) -> pair.toList() + single }
        }
}

