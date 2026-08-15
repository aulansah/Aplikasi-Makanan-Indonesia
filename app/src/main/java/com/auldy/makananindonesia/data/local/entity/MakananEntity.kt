package com.auldy.makananindonesia.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.auldy.makananindonesia.data.local.PhotoResolver
import com.auldy.makananindonesia.data.model.Makanan

@Entity(tableName = "makanan")
data class MakananEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "detail")
    val detail: String,

    /** Stable string key (e.g. "bakso") — NOT a raw resource Int. See PhotoResolver. */
    @ColumnInfo(name = "photo_key")
    val photoKey: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "asal_daerah")
    val asalDaerah: String,

    @ColumnInfo(name = "estimasi_kalori")
    val estimasiKalori: Int,

    @ColumnInfo(name = "waktu_masak_menit")
    val waktuMasakMenit: Int,

    @ColumnInfo(name = "jumlah_ulasan")
    val jumlahUlasan: String,

    @ColumnInfo(name = "harga_estimasi")
    val hargaEstimasi: Int,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "is_featured")
    val isFeatured: Boolean = false
) {
    fun toDomainModel(): Makanan = Makanan(
        id = id,
        nama = nama,
        detail = detail,
        photo = PhotoResolver.resolve(photoKey),
        category = category,
        rating = rating,
        asalDaerah = asalDaerah,
        estimasiKalori = estimasiKalori,
        waktuMasakMenit = waktuMasakMenit,
        jumlahUlasan = jumlahUlasan,
        hargaEstimasi = hargaEstimasi,
        isFavorite = isFavorite,
        isFeatured = isFeatured
    )

    companion object {
        fun fromDomainModel(makanan: Makanan): MakananEntity = MakananEntity(
            id = makanan.id,
            nama = makanan.nama,
            detail = makanan.detail,
            photoKey = PhotoResolver.keyFor(makanan.photo),
            category = makanan.category,
            rating = makanan.rating,
            asalDaerah = makanan.asalDaerah,
            estimasiKalori = makanan.estimasiKalori,
            waktuMasakMenit = makanan.waktuMasakMenit,
            jumlahUlasan = makanan.jumlahUlasan,
            hargaEstimasi = makanan.hargaEstimasi,
            isFavorite = makanan.isFavorite,
            isFeatured = makanan.isFeatured
        )
    }
}
