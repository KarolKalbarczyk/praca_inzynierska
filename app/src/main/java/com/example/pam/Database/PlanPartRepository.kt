package com.example.pam.Database

import androidx.annotation.WorkerThread
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Transaction
import com.example.myapplication.Database.Antique

class PlanPartRepository(val dao: PlanPartDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    @Transaction
    fun getPlan() = dao.getPlannedAntiques()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    @Transaction
    fun reorder(higher: PlanPart, lower: PlanPart){
        higher.order += 1
        lower.order -= 1
        dao.updatePlanPart(higher)
        dao.updatePlanPart(lower)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    @Transaction
    fun addToPlan(antique : Antique): Long{
        val plan = getPlan().sortedBy { it.planPart.order }
        val planPart = PlanPart(
            planPartId = 0,
            order = if (plan.size > 0) plan[0].planPart.order - 1 else 0,
            done = false,
            antiqueId = antique.id,
        )
        return dao.insertPlanPart(planPart)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    @Transaction
    fun complete(id: Int){
        val plan = getPlan()
        val index = plan.indexOfFirst { it.planPart.planPartId == id }
        plan.take(index + 1).forEach {
            it.planPart.done = true
            dao.updatePlanPart(it.planPart)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    @Transaction
    fun remove(id: Int){
        val plan = getPlan()
        val index = plan.indexOfFirst { it.planPart.planPartId == id }
        plan.drop(index + 1).forEach {
            it.planPart.order -= 1
            dao.updatePlanPart(it.planPart)
        }
        dao.delete(plan[index].planPart)
    }

}