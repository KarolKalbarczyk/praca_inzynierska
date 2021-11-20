package com.example.myapplication.Database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pam.Database.*
import java.util.concurrent.Executors
import com.example.pam.R



@Database(entities = arrayOf(Antique::class, GalleryPhoto::class, AntiqueHistory::class, PlanPart::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun antiqueDao(): AntiqueDao
    abstract fun antiqueHistoryDao(): AntiqueHistoryDao
    abstract fun planDao(): PlanPartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: getDatabase(context).also { INSTANCE = it }
            }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "antique_database"
                )
                    //.addMigrations(MIGRATION_1_2)
                    .addCallback(object: RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadScheduledExecutor().execute{
                                var yourDao = getInstance(context).antiqueDao()
                                yourDao.insert(Antique(0, 1.0, 1.0, R.mipmap.hala1, R.string.Hala, R.string.hala_desc))
                                yourDao.insert(Antique(1, 2.0 , 2.0, R.mipmap.katedra, R.string.Katedra, R.string.katedra_desc))
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE GalleryPhoto RENAME COLUMN 'ID' TO 'GalleryID'")
    }
}