package com.example.myapplication.Database

import androidx.room.*
import java.util.*

@Entity(tableName = "Antique")
class Antique( @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
               @ColumnInfo(name = "Longitude") val latitude: Double,
               @ColumnInfo(name = "Latitude") val longitude: Double,
               @ColumnInfo(name = "MainPhoto") val mainPhotoId: Int,
               @ColumnInfo(name = "Name")  val nameId: Int,
               @ColumnInfo(name = "Description")  val descriptionId: Int,
)

@Entity(tableName = "GalleryPhoto")
class GalleryPhoto(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
                   @ColumnInfo(name = "Photo") val photoId: Int,
                   @ColumnInfo(name = "AntiqueId") val antiqueId: Int,
)

data class AntiqueHasGalleryPhoto(
    @Embedded val antique: Antique,
    @Relation(
        parentColumn = "Id",
        entityColumn = "AntiqueId"
    )
    val photos: List<GalleryPhoto>,
)