package com.auldy.makananindonesia.data.repository

import com.auldy.makananindonesia.data.model.Makanan
import kotlinx.coroutines.flow.Flow

interface MakananRepository {
    fun getAllMakanan(): Flow<List<Makanan>>
    fun getMakananById(id: Int): Flow<Makanan?>
    fun getFavoriteMakanan(): Flow<List<Makanan>>
    fun searchMakanan(query: String): Flow<List<Makanan>>
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
    suspend fun insertMakanan(makanan: Makanan): Long
    suspend fun deleteMakanan(makanan: Makanan)
}
