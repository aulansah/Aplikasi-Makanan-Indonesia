package com.auldy.makananindonesia.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.auldy.makananindonesia.data.local.MakananData
import com.auldy.makananindonesia.data.local.entity.MakananEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [MakananEntity::class], version = 1, exportSchema = false)
abstract class MakananDatabase : RoomDatabase() {

    abstract fun makananDao(): MakananDao

    companion object {
        @Volatile
        private var INSTANCE: MakananDatabase? = null

        fun getInstance(context: Context): MakananDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MakananDatabase::class.java,
                    "makanan_database.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.makananDao()?.insertAll(MakananData.initialMakananList)
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
