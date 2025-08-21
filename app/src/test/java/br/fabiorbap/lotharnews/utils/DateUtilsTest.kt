package br.fabiorbap.lotharnews.utils

import android.util.Log
import br.fabiorbap.lotharnews.common.util.formatIsoDate
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateUtilsTest {

    @Test
    fun `pass date and return correct format`()  {
        val parameterDate = "2025-08-08T08:15:40Z"
        val formattedDate = parameterDate.formatIsoDate()
        val correctDate = "August 8th 2025"

        assert(formattedDate == correctDate)
    }

    @Test
    fun `pass date with day ending in 1 and return correct format`() {
        val parameterDate = "2025-08-01T08:15:40Z"
        val formattedDate = parameterDate.formatIsoDate()
        val correctDate = "August 1st 2025"

        assert(formattedDate == correctDate)
    }

    @Test
    fun `pass date with day ending in 2 and return correct format`() {
        val parameterDate = "2025-08-02T08:15:40Z"
        val formattedDate = parameterDate.formatIsoDate()
        val correctDate = "August 2nd 2025"

        assert(formattedDate == correctDate)
    }

    @Test
    fun `pass date with unsupported format and return exception`() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        val parameterDate = "2025-08-02T08:15:"
        val formattedDate = parameterDate.formatIsoDate()

        assert(formattedDate == "")
    }

}