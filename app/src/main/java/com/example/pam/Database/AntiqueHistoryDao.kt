package com.example.pam.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.Database.Antique
import com.example.myapplication.Database.AntiqueHasGalleryPhoto

@Dao
interface AntiqueHistoryDao {
    @Query("Select * from AntiqueHistory where antiqueID = :antiqueId order by historyOrder asc")
    fun get(antiqueId: Int): AntiqueHistory

  //  @Insert
   // suspend fun insert(entry: AntiqueHistory) : AntiqueHistory

}