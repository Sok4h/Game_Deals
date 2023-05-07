package com.sok4h.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()


    @Test
    fun startupModeNone() = startup(CompilationMode.None())

    @Test
    fun startupModePartial() = startup(CompilationMode.Partial())

    fun startup(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.sok4h.game_deals",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        compilationMode= compilationMode,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }


    @Test
    fun scrollPartial() =scrollTest(CompilationMode.Partial())

    @Test
    fun scrollNone() =scrollTest(CompilationMode.None())
    fun scrollTest(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName =
        "com.sok4h.game_deals",
        metrics = listOf(FrameTimingMetric()),
        iterations = 4,
        compilationMode=compilationMode,
        startupMode = StartupMode.WARM,

        ) {
        pressHome()
        startActivityAndWait()

        device.wait(
            Until.hasObject(By.res("lazygrid")),
                    TimeUnit.SECONDS.toMillis(4)
        )
        val list = device.findObject(By.res("lazygrid"))
        list.setGestureMargin(device.displayWidth/ 5)

        list.fling(Direction.DOWN)

    }
}