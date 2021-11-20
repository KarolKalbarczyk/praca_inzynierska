package com.example.myapplication.Database

import androidx.core.app.NotificationCompat
import androidx.room.*
import com.example.pam.Database.AntiqueHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface AntiqueDao {

    @Query("Select * from Antique")
    fun getAll(): List<Antique>

    @Query("Select * from Antique where Id = :id")
    fun getAntique(id: Int): Antique


    @Query("Select * from Antique a left join GalleryPhoto g on a.id = g.antiqueId  where a.Id = :id ")
    fun getWithPhotos(id: Int): AntiqueHasGalleryPhoto

    @Query("Select * from GalleryPhoto where antiqueId = :id")
    fun getPhotos(id: Int): List<GalleryPhoto>

    @Insert
    fun insert(entry: Antique)

    @Query("Select * from AntiqueHistory where antiqueID = :antiqueId order by historyOrder asc")
    fun getHistory(antiqueId: Int): List<AntiqueHistory>

    @Insert
    fun insert(entry: GalleryPhoto)

    @Insert
    fun insert(entry: AntiqueHistory)

    @Query("delete from GalleryPhoto")
    fun deleteGallery()

    @Query("delete from Antique")
    fun delete()

    @Query("delete from AntiqueHistory")
    fun deleteHistory()



    @Delete
    fun delete(entry: Antique)
}