package com.auldy.makananindonesia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.auldy.makananindonesia.databinding.ActivityDetailBinding
import com.auldy.makananindonesia.ui.common.UiState
import com.auldy.makananindonesia.ui.common.ViewModelFactory
import com.auldy.makananindonesia.ui.detail.DetailViewModel
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodId = intent.getIntExtra(EXTRA_ID, 0)
        val initialPhoto = intent.getIntExtra(EXTRA_PHOTO, 0)
        val initialNama = intent.getStringExtra(EXTRA_NAMA).orEmpty()
        val initialDetail = intent.getStringExtra(EXTRA_DETAIL).orEmpty()

        // Populate initial UI from Intent
        if (initialNama.isNotEmpty()) {
            supportActionBar?.title = initialNama
            binding.makananNama.text = initialNama
            binding.makananDetail.text = initialDetail
            if (initialPhoto != 0) {
                binding.makananPhoto.setImageResource(initialPhoto)
            }
        }

        binding.btnBack.setOnClickListener { finish() }

        if (foodId > 0) {
            observeDetail(foodId)
        }
    }

    private fun observeDetail(id: Int) {
        viewModel.getMakananById(id)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.makananState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            // Loading state
                        }
                        is UiState.Success -> {
                            val makanan = state.data
                            supportActionBar?.title = makanan.nama
                            binding.makananNama.text = makanan.nama
                            binding.makananDetail.text = makanan.detail
                            if (makanan.photo != 0) {
                                binding.makananPhoto.setImageResource(makanan.photo)
                            }
                        }
                        is UiState.Error -> {
                            Toast.makeText(this@DetailActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "nama"
        const val EXTRA_PHOTO = "photo"
        const val EXTRA_DETAIL = "detail"
    }
}
