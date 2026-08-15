package com.auldy.makananindonesia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.auldy.makananindonesia.data.model.Makanan
import com.auldy.makananindonesia.databinding.ActivityDetailBinding
import com.auldy.makananindonesia.ui.common.UiState
import com.auldy.makananindonesia.ui.common.ViewModelFactory
import com.auldy.makananindonesia.ui.detail.DetailViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    private var currentFoodId: Int = 0
    private var isFavoriteState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodId = intent.getIntExtra(EXTRA_ID, 0)
        val initialPhoto = intent.getIntExtra(EXTRA_PHOTO, 0)
        val initialNama = intent.getStringExtra(EXTRA_NAMA).orEmpty()
        val initialDetail = intent.getStringExtra(EXTRA_DETAIL).orEmpty()
        currentFoodId = foodId

        // Populate initial UI from Intent immediately (before Room query resolves)
        if (initialNama.isNotEmpty()) {
            binding.makananNama.text = initialNama
            binding.makananDetail.text = initialDetail
            if (initialPhoto != 0) {
                binding.makananPhoto.setImageResource(initialPhoto)
            }
        }

        // Back button
        binding.btnBack.setOnClickListener { finish() }

        // Favorite button — now actually toggles + persists to Room + updates icon
        binding.btnFavorite.setOnClickListener {
            toggleFavorite()
        }

        if (foodId > 0) {
            observeDetail(foodId)
        }
    }

    private fun toggleFavorite() {
        if (currentFoodId <= 0) return
        isFavoriteState = !isFavoriteState
        viewModel.setFavorite(currentFoodId, isFavoriteState)
        updateFavoriteIcon()
        val message = if (isFavoriteState) R.string.msg_ditambahkan_favorit else R.string.msg_dihapus_favorit
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateFavoriteIcon() {
        val iconRes = if (isFavoriteState) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off
        binding.btnFavoriteIcon.setImageResource(iconRes)
    }

    private fun observeDetail(id: Int) {
        viewModel.getMakananById(id)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.makananState.collect { state ->
                    when (state) {
                        is UiState.Loading -> { /* Loading */ }
                        is UiState.Success -> bindMakanan(state.data)
                        is UiState.Error -> {
                            Toast.makeText(this@DetailActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun bindMakanan(makanan: Makanan) {
        isFavoriteState = makanan.isFavorite
        updateFavoriteIcon()

        binding.makananNama.text = makanan.nama
        binding.makananDetail.text = makanan.detail
        if (makanan.photo != 0) {
            binding.makananPhoto.setImageResource(makanan.photo)
        }

        binding.tvRating.text = String.format(Locale.US, "%.1f", makanan.rating)
        binding.tvOrigin.text = makanan.asalDaerah
        binding.tvTagCategory.text = makanan.category
        binding.tvTagWaktu.text = getString(R.string.format_menit, makanan.waktuMasakMenit)
        binding.tvKalori.text = getString(R.string.format_kalori, makanan.estimasiKalori)
        binding.tvWaktu.text = getString(R.string.format_menit, makanan.waktuMasakMenit)
        binding.tvUlasan.text = makanan.jumlahUlasan

        val rupiah = NumberFormat.getNumberInstance(Locale("in", "ID")).format(makanan.hargaEstimasi)
        binding.tvHarga.text = "Rp $rupiah"
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA = "nama"
        const val EXTRA_PHOTO = "photo"
        const val EXTRA_DETAIL = "detail"
    }
}
