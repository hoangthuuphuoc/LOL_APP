package com.example.lol_app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.lol_app.R
import com.example.lol_app.databinding.ActivityAuthBinding
import com.example.lol_app.ui.home.HomeActivity
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    private val viewModel: AuthViewModel by viewModels()

    private var isEmailError = false
    private var isPasswordError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupEditTextState()
        setupKeyboardInsets()
        observeData()
        initListener()
    }

    private fun handleLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtSenha.text.toString().trim()

        viewModel.login(email, password)
    }

    private fun setupEditTextState() {

        binding.edtEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!isEmailError) {
                binding.edtEmail.setBackgroundResource(
                    if (hasFocus) {
                        R.drawable.bg_edit_text_focus
                    } else {
                        R.drawable.bg_edit_text
                    }
                )
            }
        }

        binding.edtSenha.setOnFocusChangeListener { _, hasFocus ->
            if (!isPasswordError) {
                binding.edtSenha.setBackgroundResource(
                    if (hasFocus) {
                        R.drawable.bg_edit_text_focus
                    } else {
                        R.drawable.bg_edit_text
                    }
                )
            }
        }
    }

    private fun showEmailError(message: String?) {

        if (message != null) {
            isEmailError = true

            binding.edtEmail.setBackgroundResource(
                R.drawable.bg_edit_text_error
            )

            binding.tvEmailError.text = message
            binding.tvEmailError.visibility = View.VISIBLE

        } else {
            isEmailError = false

            binding.tvEmailError.visibility = View.GONE

            binding.edtEmail.setBackgroundResource(
                if (binding.edtEmail.hasFocus()) {
                    R.drawable.bg_edit_text_focus
                } else {
                    R.drawable.bg_edit_text
                }
            )
        }
    }

    private fun showPasswordError(message: String?) {

        if (message != null) {
            isPasswordError = true

            binding.edtSenha.setBackgroundResource(
                R.drawable.bg_edit_text_error
            )

            binding.tvPasswordError.text = message
            binding.tvPasswordError.visibility = View.VISIBLE

        } else {
            isPasswordError = false

            binding.tvPasswordError.visibility = View.GONE

            binding.edtSenha.setBackgroundResource(
                if (binding.edtSenha.hasFocus()) {
                    R.drawable.bg_edit_text_focus
                } else {
                    R.drawable.bg_edit_text
                }
            )
        }
    }

    private fun observeData() {

        lifecycleScope.launch {
            viewModel.loading.collect { isLoading ->

                binding.animationLoading.visibility =
                    if (isLoading) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

                binding.btnStart.isEnabled = !isLoading
            }
        }

        lifecycleScope.launch {
            viewModel.loginSuccess.collect { success ->

                if (success) {
                    val intent = Intent(
                        this@AuthActivity,
                        HomeActivity::class.java
                    )

                    startActivity(intent)
                    finish()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.toast.collect { message ->

                Toast.makeText(
                    this@AuthActivity,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launch {
            viewModel.isShow.collect { isShow ->

                binding.edtSenha.inputType =
                    if (isShow) {
                        InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }

                binding.edtSenha.setSelection(
                    binding.edtSenha.text.length
                )
            }
        }

        lifecycleScope.launch {
            viewModel.emailError.collect { error ->
                showEmailError(error)
            }
        }

        lifecycleScope.launch {
            viewModel.passwordError.collect { error ->
                showPasswordError(error)
            }
        }
    }

    private fun setupKeyboardInsets() {

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->

            val keyboardHeight =
                insets.getInsets(
                    WindowInsetsCompat.Type.ime()
                ).bottom

            val isKeyboardShow =
                insets.isVisible(
                    WindowInsetsCompat.Type.ime()
                )

            binding.root.translationY =
                if (isKeyboardShow) {
                    -(keyboardHeight * 0.45f)
                } else {
                    0f
                }

            insets
        }

        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun initListener() {

        binding.ivIcBack.setOnClickListener {
            finish()
        }

        binding.btnStart.setOnClickListener {
            handleLogin()
        }

        binding.ivEye.setOnClickListener {
            viewModel.displayPassword()
        }
    }
}