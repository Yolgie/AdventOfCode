@file:Suppress("unused", "TooManyFunctions", "SpellCheckingInspection")

package at.cnoize.contest.util

// kotlin.collections

inline fun <T> Array<out T>.takeUntil(
    predicate: (T) -> Boolean
): T {
    return takeWhileInclusive { e: T -> !predicate(e) }.last()
}

inline fun ByteArray.takeUntil(
    predicate: (Byte) -> Boolean
): Byte {
    return takeWhileInclusive { e: Byte -> !predicate(e) }.last()
}

inline fun ShortArray.takeUntil(
    predicate: (Short) -> Boolean
): Short {
    return takeWhileInclusive { e: Short -> !predicate(e) }.last()
}

inline fun IntArray.takeUntil(
    predicate: (Int) -> Boolean
): Int {
    return takeWhileInclusive { e: Int -> !predicate(e) }.last()
}

inline fun LongArray.takeUntil(
    predicate: (Long) -> Boolean
): Long {
    return takeWhileInclusive { e: Long -> !predicate(e) }.last()
}

inline fun FloatArray.takeUntil(
    predicate: (Float) -> Boolean
): Float {
    return takeWhileInclusive { e: Float -> !predicate(e) }.last()
}

inline fun DoubleArray.takeUntil(
    predicate: (Double) -> Boolean
): Double {
    return takeWhileInclusive { e: Double -> !predicate(e) }.last()
}

inline fun BooleanArray.takeUntil(
    predicate: (Boolean) -> Boolean
): Boolean {
    return takeWhileInclusive { e: Boolean -> !predicate(e) }.last()
}

inline fun CharArray.takeUntil(
    predicate: (Char) -> Boolean
): Char {
    return takeWhileInclusive { e: Char -> !predicate(e) }.last()
}

inline fun <T> Iterable<T>.takeUntil(
    predicate: (T) -> Boolean
): T {
    return takeWhileInclusive { e: T -> !predicate(e) }.last()
}

// kotlin.sequences

fun <T> Sequence<T>.takeUntil(
    predicate: (T) -> Boolean
): T {
    return takeWhileInclusive { e: T -> !predicate(e) }.last()
}

// kotlin.text

inline fun CharSequence.takeUntil(
    predicate: (Char) -> Boolean
): Char {
    return takeWhileInclusive { e: Char -> !predicate(e) }.last()
}

inline fun String.takeUntil(
    predicate: (Char) -> Boolean
): Char {
    return takeWhileInclusive { e: Char -> !predicate(e) }.last()
}

// list

fun <T> Sequence<Iterable<T>>.takeUntilFirst(
    predicate: (T) -> Boolean
): T {
    return takeUntil { iterable: Iterable<T> -> iterable.any { predicate(it) } }
        .first { predicate(it) }
}
