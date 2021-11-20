package com.example.myapplication.Database

import androidx.annotation.WorkerThread
import androidx.room.Transaction

class AntiqueRepository(val dao: AntiqueDao) {
    val allEntries = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(Antique: Antique) {
        dao.insert(Antique)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    @Transaction
    suspend fun getAntique(id: Int) = dao.getWithPhotos(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(Antique: Antique){
        dao.delete(Antique)
    }
}