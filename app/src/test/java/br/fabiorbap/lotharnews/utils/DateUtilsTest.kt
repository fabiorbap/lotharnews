package br.fabiorbap.lotharnews.utils

import android.util.Log
import br.fabiorbap.lotharnews.common.util.formatIsoDate
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateUtilsTest {

    @Test
    fun formatIsoDate_regularDate_correctFormatReturned()  {
        val parameterDate = "2025-08-08T08:15:40Z"
        val formattedDate = parameterDate.formatIsoDate()
        val correctDate = "August 8th 2025"

        assertEquals(formattedDate, correctDate)
    }

    @Test
    fun formatIsoDate_dateEndingIn1_correctFormatReturned() {
        val parameterDate = "2025-08-01T08:15:40Z"
        val formattedDate = parameterDate.formatIsoDate()
        val correctDate = "August 1st 2025"

        assertEquals(formattedDate, correctDate)
    }

    @Test
    fun formatIsoDate_dateEndingIn2_correctReturnedFormat() {
        val parameterDate = "2025-08-02T08:15:40Z"
        val formattedDate = parameterDate.formatIsoDate()
        val correctDate = "August 2nd 2025"

        assertEquals(formattedDate, correctDate)
    }

    @Test
    fun formatIsoDate_unsupportedDateFormat_emptyStringReturned() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        val parameterDate = "2025-08-02T08:15:"
        val formattedDate = parameterDate.formatIsoDate()

        assertEquals(formattedDate, "")
    }

}