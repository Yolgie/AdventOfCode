@file:Suppress("unused", "TooManyFunctions", "SpellCheckingInspection")

package at.cnoize.contest.util

/*
 * @author Juan Vimberg, @jivimberg
 * Source: https://jivimberg.io/blog/2018/06/02/implementing-takewhileinclusive-in-kotlin/
 * Source: https://gist.github.com/jivimberg/ff5aad3f5c6315deb420fd508a145c61
 */

// kotlin.collections

inline fun <T> Array<out T>.takeWhileInclusive(
        predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun ByteArray.takeWhileInclusive(
        predicate: (Byte) -> Boolean
): List<Byte> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun ShortArray.takeWhileInclusive(
        predicate: (Short) -> Boolean
): List<Short> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun IntArray.takeWhileInclusive(
        predicate: (Int) -> Boolean
): List<Int> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun LongArray.takeWhileInclusive(
        predicate: (Long) -> Boolean
): List<Long> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun FloatArray.takeWhileInclusive(
        predicate: (Float) -> Boolean
): List<Float> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun DoubleArray.takeWhileInclusive(
        predicate: (Double) -> Boolean
): List<Double> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun BooleanArray.takeWhileInclusive(
        predicate: (Boolean) -> Boolean
): List<Boolean> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun CharArray.takeWhileInclusive(
        predicate: (Char) -> Boolean
): List<Char> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun <T> Iterable<T>.takeWhileInclusive(
        predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

// kotlin.sequences

fun <T> Sequence<T>.takeWhileInclusive(
        predicate: (T) -> Boolean
): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

// kotlin.text

inline fun CharSequence.takeWhileInclusive(
        predicate: (Char) -> Boolean
): CharSequence {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

inline fun String.takeWhileInclusive(
        predicate: (Char) -> Boolean
): String {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}
