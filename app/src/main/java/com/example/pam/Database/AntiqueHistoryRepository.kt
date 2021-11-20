package com.example.pam.Database

import androidx.annotation.WorkerThread
import androidx.room.Insert
import com.example.myapplication.Database.Antique

class AntiqueHistoryRepository(val dao: AntiqueHistoryDao) {

    @WorkerThread
    suspend fun getHistory(antiqueId : Int) = dao.get(antiqueId)

    //@Insert
    //suspend fun insert(entry: AntiqueHistory) = dao.insert(entry)

}