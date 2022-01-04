package com.example.pam

import android.content.Context
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
import androidx.room.Room
import com.example.myapplication.Database.Antique
import com.example.myapplication.Database.AppDatabase
import com.example.pam.AntiqueList.AntiqueListFragment
import com.example.pam.Database.PlanPart
import com.example.pam.Database.PlanPartDao
import com.example.pam.Database.PlanPartRepository
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var planPartDao: PlanPartDao
    /*private lateinit var stringToBetyped: String

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
    }*/

    /*@Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        planPartDao = db.planDao()
        val antiqueDao = db.antiqueDao()
        val antique1 = Antique(1, 1.0, 1.0, R.mipmap.hala1_foreground, R.string.Hala, R.string.hala_desc)
        val antique2 = Antique(2, 2.0 , 2.0, R.mipmap.katedra_foreground, R.string.Katedra, R.string.katedra_desc)
        runBlocking {
            antiqueDao.insert(antique1)
            antiqueDao.insert(antique2)
            planPartDao.insertPlanPart(PlanPart(1, 3, false, 1))
            planPartDao.insertPlanPart(PlanPart(2, 2, false, 2))
        }
    }

    @Test
    fun testPartPlanRepository(){
        val repo = PlanPartRepository(planPartDao)
        val antique3 = Antique(3, 2.0 , 2.0, R.mipmap.pomnik1_foreground, R.string.Katedra, R.string.katedra_desc)

        runBlocking {
            repo.addToPlan(antique3)
            val plan = repo.getPlan().sortedByDescending { it.planPart.order }
            plan.last().antique.id shouldBe 3
            repo.remove(plan.last().planPart.planPartId)
        }
    }
*/

}