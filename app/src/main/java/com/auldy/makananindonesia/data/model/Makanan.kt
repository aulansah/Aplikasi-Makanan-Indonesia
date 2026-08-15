package com.auldy.makananindonesia.data.model

import java.io.Serializable

data class Makanan(
    val id: Int = 0,
    val nama: String = "",
    val detail: String = "",
    val photo: Int = 0,
    val category: String = "",
    val rating: Double = 0.0,
    val asalDaerah: String = "",
    val estimasiKalori: Int = 0,
    val waktuMasakMenit: Int = 0,
    val jumlahUlasan: String = "",
    val hargaEstimasi: Int = 0,
    val isFavorite: Boolean = false,
    val isFeatured: Boolean = false
) : Serializable
