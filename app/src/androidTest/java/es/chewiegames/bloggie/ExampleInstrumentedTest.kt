package es.chewiegames.bloggie

import androidx.test.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("es.chewiegames.bloggie", appContext.packageName)
    }
}
