package com.example.pam.Database

import androidx.room.*
import com.example.myapplication.Database.Antique
import com.example.myapplication.Database.GalleryPhoto

enum class HistoryFragmentKind{
    Text,
    Video,
    Photo,
}

enum class Placement{
    Right,
    Left,
    Entire,
}

@Entity(tableName = "AntiqueHistory")
data class AntiqueHistory(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
                            @ColumnInfo(name = "Kind") val kind : Int,
                            @ColumnInfo(name = "Placement") val placement : Int,
                            @ColumnInfo(name = "Resource") val resourceId : Int,
                            @ColumnInfo(name = "HistoryOrder") val historyOrder : Int,
                            @ForeignKey(entity = Antique::class, parentColumns = arrayOf("id"), childColumns = arrayOf(""))
                                @ColumnInfo(name = "antiqueId") val antiqueId: Int,
){
    val place : Placement
    get() = when(placement){
        0 -> Placement.Right
        1 -> Placement.Left
        else -> Placement.Entire
    }

    val mediaKind : HistoryFragmentKind
        get() = when(kind){
            0 -> HistoryFragmentKind.Photo
            1 -> HistoryFragmentKind.Text
            else -> HistoryFragmentKind.Video
        }


}

