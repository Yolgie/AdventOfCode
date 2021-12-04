package at.cnoize.contest.util

import org.testng.Assert.*
import org.testng.annotations.Test

class ContestUtilTest {

    @Test
    fun testUpdateList() {
        val original = listOf(1,2,3,4,5,6)
        val expected = listOf(1,2,3,8,5,6)
        assertEquals(expected, original.update(3, 8))
    }

    @Test
    fun testTranspose() {
        val original = listOf(listOf(0, 1, 2), listOf(3, 4, 5))
        val expected = listOf(listOf(0, 3), listOf(1, 4), listOf(2, 5))
        println(original)
        println(original.transpose())
        println(expected)
        assertEquals(expected, original.transpose())
    }
}
