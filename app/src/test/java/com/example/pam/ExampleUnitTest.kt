package com.example.pam

import com.example.pam.Database.PlanPart
import com.example.pam.Database.PlanPartDao
import com.example.pam.Database.PlanPartRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
/*
class ExampleUnitTest : StringSpec({
    val daoMock = mockk<PlanPartDao>()
    every { daoMock.updatePlanPart(any()) } just Runs

    val repository = PlanPartRepository(daoMock)
    val toHigher = PlanPart(planPartId = 1, order = 1, done = false, antiqueId = 1)
    val toLower = PlanPart(planPartId = 2, order = 2, done = false, antiqueId = 2)
    repository.reorder(toHigher, toLower)

    verify(exactly = 1) { daoMock.updatePlanPart(toHigher) }
    verify(exactly = 1) { daoMock.updatePlanPart(toLower) }

    toHigher.order shouldBe 2
    toLower.order shouldBe 1
})

*/