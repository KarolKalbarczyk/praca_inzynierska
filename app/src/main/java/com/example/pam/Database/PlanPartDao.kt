package com.example.pam.Database

import androidx.room.*
import kotlinx.coroutines.internal.RemoveFirstDesc

@Dao
interface PlanPartDao {

    @Transaction
    @Query("SELECT * FROM PlanPart")
    fun getPlannedAntiques(): List<PlanPartHasAntique>

    @Update
    fun updatePlanPart(part: PlanPart) : Unit

    @Insert
    fun insertPlanPart(part: PlanPart) : Long

    @Delete
    fun delete(part: PlanPart) : Unit

    @Query("delete from PlanPart")
    fun delete(): Unit

}