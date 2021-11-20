package com.example.pam

import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.navigation.testing.*
import androidx.test.core.app.ApplicationProvider
import androidx.fragment.app.testing.*
import androidx.lifecycle.Lifecycle
import com.example.pam.AntiqueList.AntiqueListFragment

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var stringToBetyped: String

    @Rule // third parameter is set to false which means the activity is not started automatically
    val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext())

    private lateinit var scenario: FragmentScenario<AntiqueListFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testNavigationToInGameScreen() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val titleScenario = launchFragmentInContainer<AntiqueListFragment>()
        titleScenario.moveToState(Lifecycle.State.CREATED)

        onView(withId(R.id.sort)).perform(click())
        titleScenario.onFragment { fragment ->
            val array = fragment.viewAdapter.dataset
            for (i in 1..array.size - 1){
                assert(array[i - 1].distance < array[i].distance)
            }
        }
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso"
    }

    @Test
    fun changeText_sameActivity() {

    }
}