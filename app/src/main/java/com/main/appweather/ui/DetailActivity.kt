package com.main.appweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.main.appweather.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding = ActivityDetailBinding.inflate( layoutInflater )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}