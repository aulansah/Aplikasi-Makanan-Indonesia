package com.auldy.makananindonesia.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.auldy.makananindonesia.data.local.entity.MakananEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MakananDao {
    @Query("SELECT * FROM makanan ORDER BY id ASC")
    fun getAllMakanan(): Flow<List<MakananEntity>>

    @Query("SELECT * FROM makanan WHERE id = :id LIMIT 1")
    fun getMakananById(id: Int): Flow<MakananEntity?>

    @Query("SELECT * FROM makanan WHERE is_favorite = 1 ORDER BY nama ASC")
    fun getFavoriteMakanan(): Flow<List<MakananEntity>>

    @Query("SELECT * FROM makanan WHERE nama LIKE '%' || :query || '%' OR detail LIKE '%' || :query || '%'")
    fun searchMakanan(query: String): Flow<List<MakananEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(makananList: List<MakananEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(makanan: MakananEntity): Long

    @Update
    suspend fun update(makanan: MakananEntity)

    @Query("UPDATE makanan SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Delete
    suspend fun delete(makanan: MakananEntity)

    @Query("SELECT COUNT(*) FROM makanan")
    suspend fun getCount(): Int
}
