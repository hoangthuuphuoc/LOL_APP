package com.example.lol_app.ui.loginoption

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lol_app.databinding.ActivityAuthBinding
import com.example.lol_app.databinding.ActivityLoginOptionBinding
import com.example.lol_app.ui.auth.AuthActivity

class LoginActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginOptionBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()

    }
    private fun initListener(){
        binding.tvLogin.setOnClickListener {
            val intent= Intent(this@LoginActivity, AuthActivity::class.java)
            startActivity(intent)
        }
    }
}