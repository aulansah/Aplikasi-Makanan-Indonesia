package com.auldy.makananindonesia

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listMakananAdapter: ListMakananAdapter

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Makanan Indonesia"

        setupRecyclerView()
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

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            // Loading state
                        }
                        is UiState.Success -> {
                            listMakananAdapter.setData(state.data)
                        }
                        is UiState.Error -> {
                            Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_about -> {
                val moveAbout = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveAbout)
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
