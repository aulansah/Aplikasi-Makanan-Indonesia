package com.auldy.makananindonesia.data.repository

import com.auldy.makananindonesia.data.local.MakananData
import com.auldy.makananindonesia.data.local.entity.MakananEntity
import com.auldy.makananindonesia.data.local.room.MakananDao
import com.auldy.makananindonesia.data.model.Makanan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class MakananRepositoryImpl(
    private val makananDao: MakananDao
) : MakananRepository {

    override fun getAllMakanan(): Flow<List<Makanan>> {
        return makananDao.getAllMakanan()
            .onStart {
                if (makananDao.getCount() == 0) {
                    makananDao.insertAll(MakananData.initialMakananList)
                }
            }
            .map { entities ->
                entities.map { it.toDomainModel() }
            }
    }

    override fun getMakananById(id: Int): Flow<Makanan?> {
        return makananDao.getMakananById(id).map { it?.toDomainModel() }
    }

    override fun getFavoriteMakanan(): Flow<List<Makanan>> {
        return makananDao.getFavoriteMakanan().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun searchMakanan(query: String): Flow<List<Makanan>> {
        return makananDao.searchMakanan(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun setFavorite(id: Int, isFavorite: Boolean) {
        makananDao.updateFavoriteStatus(id, isFavorite)
    }

    override suspend fun insertMakanan(makanan: Makanan): Long {
        return makananDao.insert(MakananEntity.fromDomainModel(makanan))
    }

    override suspend fun deleteMakanan(makanan: Makanan) {
        makananDao.delete(MakananEntity.fromDomainModel(makanan))
    }
}
