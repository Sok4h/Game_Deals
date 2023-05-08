

package com.sok4h.benchmark
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {


    @get:Rule
    val rule = BaselineProfileRule()


    //Baseline profile for improving startup time performance
    @Test
    fun generate() = rule.collectBaselineProfile(
        "com.sok4h.game_deals",

        ) {
        pressHome()
        startActivityAndWait()

    }


    @Test
    fun scrollTest() = rule.collectBaselineProfile(
        packageName =
        "com.sok4h.game_deals",
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