package com.auldy.makananindonesia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.auldy.makananindonesia.data.model.Makanan
import com.auldy.makananindonesia.databinding.ActivityFavoriteBinding
import com.auldy.makananindonesia.ui.common.UiState
import com.auldy.makananindonesia.ui.common.ViewModelFactory
import com.auldy.makananindonesia.ui.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListMakananAdapter

    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        adapter = ListMakananAdapter()
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(false)
            this.adapter = this@FavoriteActivity.adapter
        }
        adapter.setOnItemClickCallback(object : ListMakananAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Makanan) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, data.id)
                    putExtra(DetailActivity.EXTRA_NAMA, data.nama)
                    putExtra(DetailActivity.EXTRA_PHOTO, data.photo)
                    putExtra(DetailActivity.EXTRA_DETAIL, data.detail)
                }
                startActivity(intent)
            }
        })

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> { /* keep last content while loading */ }
                        is UiState.Success -> {
                            adapter.setData(state.data)
                            val isEmpty = state.data.isEmpty()
                            binding.layoutEmptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
                            binding.rvFavorite.visibility = if (isEmpty) View.GONE else View.VISIBLE
                        }
                        is UiState.Error -> {
                            binding.layoutEmptyState.visibility = View.VISIBLE
                            binding.rvFavorite.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}
