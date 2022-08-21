package com.soopeach.coco

import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import coil.load
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.soopeach.coco.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.getStringExtra("uri")?.replace("thumbnails","images")
        binding = ActivityDetailBinding.inflate(layoutInflater)
        binding.imageView.load(data?.toUri())
        setContentView(binding.root)

        binding.rotateBtn.setOnClickListener {
            binding.imageView.rotation += 90f
        }

    }


}