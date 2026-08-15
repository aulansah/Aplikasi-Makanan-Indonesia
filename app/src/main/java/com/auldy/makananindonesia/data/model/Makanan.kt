package com.auldy.makananindonesia.data.model

import java.io.Serializable

data class Makanan(
    val id: Int = 0,
    val nama: String = "",
    val detail: String = "",
    val photo: Int = 0,
    val isFavorite: Boolean = false
) : Serializable
