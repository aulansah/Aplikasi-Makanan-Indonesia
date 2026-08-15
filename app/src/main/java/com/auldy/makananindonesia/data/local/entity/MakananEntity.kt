package com.auldy.makananindonesia.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.auldy.makananindonesia.data.model.Makanan

@Entity(tableName = "makanan")
data class MakananEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "detail")
    val detail: String,

    @ColumnInfo(name = "photo")
    val photo: Int,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
) {
    fun toDomainModel(): Makanan = Makanan(
        id = id,
        nama = nama,
        detail = detail,
        photo = photo,
        isFavorite = isFavorite
    )

    companion object {
        fun fromDomainModel(makanan: Makanan): MakananEntity = MakananEntity(
            id = makanan.id,
            nama = makanan.nama,
            detail = makanan.detail,
            photo = makanan.photo,
            isFavorite = makanan.isFavorite
        )
    }
}
