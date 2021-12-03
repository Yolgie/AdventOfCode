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
}
