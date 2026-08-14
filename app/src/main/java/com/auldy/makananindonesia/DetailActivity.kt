package com.auldy.makananindonesia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var berkasphoto: ImageView
    private lateinit var berkasdetail: TextView
    private lateinit var berkasnama: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val photo = intent.getIntExtra("photo",0)
        val nama = intent.getStringExtra("nama")
        val detail = intent.getStringExtra("detail")
        supportActionBar!!.title = nama
        berkasphoto = findViewById(R.id.makanan_photo)
        berkasphoto.setImageResource(photo)

        berkasnama = findViewById(R.id.makanan_nama)
        berkasnama.text = nama.toString()

        berkasdetail = findViewById(R.id.makanan_detail)
        berkasdetail.text = detail.toString()

        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_back ->{
                finish()
            }
        }
    }

}

