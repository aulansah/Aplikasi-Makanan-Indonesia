package com.auldy.makananindonesia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auldy.makananindonesia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photo = intent.getIntExtra("photo", 0)
        val nama = intent.getStringExtra("nama").orEmpty()
        val detail = intent.getStringExtra("detail").orEmpty()

        supportActionBar?.title = nama
        binding.makananPhoto.setImageResource(photo)
        binding.makananNama.text = nama
        binding.makananDetail.text = detail

        binding.btnBack.setOnClickListener { finish() }
    }
}

