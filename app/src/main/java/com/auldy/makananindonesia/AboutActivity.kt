package com.auldy.makananindonesia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auldy.makananindonesia.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "About Me"

        binding.btnSendEmail.setOnClickListener {
            val emailDeveloper = "a1211560@bangkit.academy"
            val sendEmailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$emailDeveloper"))
            startActivity(sendEmailIntent)
        }

        binding.btnCheckDicoding.setOnClickListener {
            val profileDeveloperURL = "https://www.dicoding.com/users/auldyansya"
            val checkDeveloperProfileIntent = Intent(Intent.ACTION_VIEW, Uri.parse(profileDeveloperURL))
            if (checkDeveloperProfileIntent.resolveActivity(packageManager) != null) {
                startActivity(checkDeveloperProfileIntent)
            }
        }
    }

    // Catatan: override onKeyDown(KEYCODE_BACK) di versi lama sengaja dihapus.
    // Perilaku tombol back sistem sudah otomatis menutup Activity ini (finish()),
    // jadi kode itu sebenarnya redundant sejak awal, dan pola onKeyDown untuk
    // menangani tombol back sudah lama digantikan oleh OnBackPressedCallback.
}