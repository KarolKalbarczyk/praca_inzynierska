package com.example.pam.Database

import androidx.room.*
import com.example.myapplication.Database.Antique
import com.example.myapplication.Database.GalleryPhoto

@Entity(tableName = "PlanPart")
class PlanPart(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "PlanPartId") val planPartId: Int,
               @ColumnInfo(name = "order") var order: Int,
               @ColumnInfo(name = "isDone") var done: Boolean,
               @ColumnInfo(name = "AntiqueId") var antiqueId: Int,
               //@ForeignKey(entity = Antique::class, parentColumns = ["id"], childColumns = [""]) @ColumnInfo(name = "antiqueId") val antiqueId: Int,
)

data class PlanPartHasAntique(
    @Embedded val planPart: PlanPart,
    @Relation(
        parentColumn = "AntiqueId",
        entityColumn = "Id"
    ) val antique: Antique)