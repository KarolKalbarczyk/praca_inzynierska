package com.example.pam

import android.app.Application
import com.example.myapplication.Database.AntiqueRepository
import com.example.myapplication.Database.AppDatabase
import com.example.pam.Database.PlanPartRepository


class SightseeingApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AntiqueRepository(database.antiqueDao()) }
    val planRepository by lazy { PlanPartRepository(database.planDao()) }
}