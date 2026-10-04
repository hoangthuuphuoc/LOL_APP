package com.example.lol_app.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lol_app.R
import com.example.lol_app.data.adapter.HomeAdapter
import com.example.lol_app.databinding.ActivityHomeBinding
import com.example.lol_app.ui.fragment.home.HomeFragementViewModel
import kotlin.getValue

class HomeActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeFragementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left, 0, systemBars.right, 0
            )
            insets
        }
        adapter = HomeAdapter(this)
        binding.viewPager.adapter = adapter
        binding.nvgNvg1.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    binding.viewPager.setCurrentItem(0, true)
                    true
                }

                R.id.search -> {
                    binding.viewPager.setCurrentItem(1, true)
                    true
                }

                else -> false

            }
        }

    }
}
