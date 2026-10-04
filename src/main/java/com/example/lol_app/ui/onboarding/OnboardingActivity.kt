package com.example.lol_app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lol_app.data.adapter.OnboardingAdapter
import com.example.lol_app.databinding.ActivityOnboardingBinding
import com.example.lol_app.ui.auth.AuthActivity
import com.example.lol_app.ui.loginoption.LoginActivity

class OnboardingActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = OnboardingAdapter(this)
        binding.viewpage.adapter = adapter
    }

    fun nextPage() {
        if (binding.viewpage.currentItem < adapter.itemCount - 1) {
            binding.viewpage.currentItem = binding.viewpage.currentItem + 1
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}