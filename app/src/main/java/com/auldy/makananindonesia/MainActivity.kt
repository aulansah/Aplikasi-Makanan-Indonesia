package com.auldy.makananindonesia

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.auldy.makananindonesia.data.model.Makanan
import com.auldy.makananindonesia.databinding.ActivityMainBinding
import com.auldy.makananindonesia.ui.common.UiState
import com.auldy.makananindonesia.ui.common.ViewModelFactory
import com.auldy.makananindonesia.ui.main.MainViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listMakananAdapter: ListMakananAdapter

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    private var currentFeatured: Makanan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearch()
        setupFilterChips()
        setupFeaturedCard()
        setupBottomNav()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        listMakananAdapter = ListMakananAdapter()
        binding.activityMakanan.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listMakananAdapter
        }

        listMakananAdapter.setOnItemClickCallback(object : ListMakananAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Makanan) {
                showSelectedMakanan(data)
            }
        })
    }

    // ── Search — wired to MainViewModel, filters the list live as the user types ──
    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.onSearchQueryChanged(s?.toString().orEmpty())
            }
        })
    }

    // ── Filter chips — each chip is now clickable and toggles active/inactive style ──
    private fun setupFilterChips() {
        val chips = listOf(
            binding.chipSemua to MainViewModel.CATEGORY_ALL,
            binding.chipBerkuah to getString(R.string.chip_berkuah),
            binding.chipBerdaging to getString(R.string.chip_berdaging),
            binding.chipPedas to getString(R.string.chip_pedas)
        )
        chips.forEach { (chip, category) ->
            chip.setOnClickListener {
                viewModel.onCategorySelected(category)
                updateChipStyles(chips, category)
            }
        }
    }

    private fun updateChipStyles(chips: List<Pair<TextView, String>>, selected: String) {
        chips.forEach { (chip, category) ->
            val isActive = category == selected
            chip.setBackgroundResource(if (isActive) R.drawable.bg_chip_active else R.drawable.bg_chip_inactive)
            chip.setTextColor(
                if (isActive) getColor(R.color.nds_on_primary) else getColor(R.color.nds_on_surface_medium)
            )
        }
    }

    // ── Featured "Rekomendasi Hari Ini" card — now bound to real data + clickable ──
    private fun setupFeaturedCard() {
        binding.cardFeatured.setOnClickListener {
            currentFeatured?.let { showSelectedMakanan(it) }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.featured.collect { makanan ->
                    currentFeatured = makanan
                    if (makanan != null) {
                        binding.tvFeaturedTitle.text = makanan.nama
                        binding.tvFeaturedDesc.text = makanan.detail
                        binding.tvFeaturedMeta.text = String.format(
                            Locale.US, "★ %.1f  %s", makanan.rating, makanan.jumlahUlasan
                        )
                        com.bumptech.glide.Glide.with(this@MainActivity)
                            .load(makanan.photo)
                            .into(binding.ivRecommendation)
                    }
                }
            }
        }
    }

    // ── Bottom nav — all four destinations now do something real ──
    private fun setupBottomNav() {
        binding.navHome.setOnClickListener {
            binding.scrollContent.smoothScrollTo(0, 0)
        }
        binding.navFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        binding.navMenu.setOnClickListener {
            Toast.makeText(this, R.string.msg_riwayat_segera, Toast.LENGTH_SHORT).show()
        }
        binding.navProfile.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> { /* Loading state — bisa tambah shimmer di sini */ }
                        is UiState.Success -> {
                            listMakananAdapter.setData(state.data)
                            val isEmpty = state.data.isEmpty()
                            binding.layoutEmptyResult.visibility = if (isEmpty) View.VISIBLE else View.GONE
                            binding.activityMakanan.visibility = if (isEmpty) View.GONE else View.VISIBLE
                        }
                        is UiState.Error -> {
                            Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showSelectedMakanan(makan: Makanan) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_ID, makan.id)
            putExtra(DetailActivity.EXTRA_NAMA, makan.nama)
            putExtra(DetailActivity.EXTRA_PHOTO, makan.photo)
            putExtra(DetailActivity.EXTRA_DETAIL, makan.detail)
        }
        startActivity(intent)
    }
}
