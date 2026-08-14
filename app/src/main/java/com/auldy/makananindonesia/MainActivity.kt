package com.auldy.makananindonesia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvMakanan: RecyclerView
    private var list: ArrayList<Makanan> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMakanan = findViewById(R.id.activity_makanan)
        rvMakanan.setHasFixedSize(true)

        list.addAll(MakananData.listData)
        showRecyclerList()

        supportActionBar!!.title = "Makanan Indonesia"
    }

    private fun showRecyclerList() {
        rvMakanan.layoutManager = LinearLayoutManager(this)
        val listMakananAdapter = ListMakananAdapter(list)
        rvMakanan.adapter = listMakananAdapter

        listMakananAdapter.setOnItemClickCallback(object : ListMakananAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Makanan) {
                showSelectedHero(data)
            }
        })
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
                val moveabout = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveabout)
            }
        }
    }

    private fun showSelectedHero(makan: Makanan) {
        startActivity(
                Intent(applicationContext, DetailActivity::class.java)
                        .putExtra("nama", makan.nama)
                        .putExtra("photo", makan.photo)
                        .putExtra("detail", makan.detail)
        )
    }
}
