package com.main.appweather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.main.appweather.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding = ActivityDetailBinding.inflate( layoutInflater )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}