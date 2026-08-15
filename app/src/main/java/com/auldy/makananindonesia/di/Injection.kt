package com.auldy.makananindonesia.di

import android.content.Context
import com.auldy.makananindonesia.data.local.room.MakananDatabase
import com.auldy.makananindonesia.data.repository.MakananRepository
import com.auldy.makananindonesia.data.repository.MakananRepositoryImpl

object Injection {
    fun provideRepository(context: Context): MakananRepository {
        val database = MakananDatabase.getInstance(context)
        return MakananRepositoryImpl(database.makananDao())
    }
}
