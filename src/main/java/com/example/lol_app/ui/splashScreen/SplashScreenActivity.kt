package com.example.lol_app.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.lol_app.R
import com.example.lol_app.databinding.ActivityOnboardingBinding
import com.example.lol_app.databinding.ActivitySplashscreenBinding
import com.example.lol_app.ui.onboarding.OnboardingActivity
import com.example.lol_app.ui.splashScreen.SplashScreenViewModel
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySplashscreenBinding.inflate(layoutInflater)
    }

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nextScreen()
        delay()
    }
    private fun delay(){
        viewModel.nextScreen()
    }
    private fun nextScreen() {
        lifecycleScope.launch {
            viewModel.next.collect {
                if (it) {
                    val intent = Intent(
                        this@SplashScreenActivity, OnboardingActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}