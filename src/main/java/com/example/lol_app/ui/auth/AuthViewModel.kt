package com.example.lol_app.ui.auth

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lol_app.data.reponsitory.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _isShow = MutableStateFlow(false)
    val isShow = _isShow.asStateFlow()

    private val _loginSuccess = MutableSharedFlow<Boolean>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError = _passwordError.asStateFlow()

    fun login(email: String, password: String) {

        viewModelScope.launch {

            _emailError.value = null
            _passwordError.value = null

            when {

                email.isEmpty() -> {
                    _emailError.value =
                        "* Vui lòng nhập email"
                    return@launch
                }

                !Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches() -> {

                    _emailError.value =
                        "* Email không hợp lệ"

                    return@launch
                }

                email.length > 50 -> {
                    _emailError.value =
                        "* Email không được quá 50 ký tự"

                    return@launch
                }

                password.isEmpty() -> {
                    _passwordError.value =
                        "* Vui lòng nhập mật khẩu"

                    return@launch
                }

                password.length < 6 -> {
                    _passwordError.value =
                        "* Mật khẩu phải có ít nhất 6 ký tự"

                    return@launch
                }

                password.length > 30 -> {
                    _passwordError.value =
                        "* Mật khẩu không được quá 30 ký tự"

                    return@launch
                }
            }

            _loading.value = true

            try {

                val user =
                    repository.loginUser(
                        email,
                        password
                    )

                if (user != null) {

                    _toast.emit(
                        "Đăng nhập thành công"
                    )

                    _loginSuccess.emit(true)
                }

            } catch (e: Exception) {

                Log.e(
                    "AuthViewModel",
                    "Login error",
                    e
                )

                _toast.emit(
                    "Email hoặc mật khẩu không chính xác"
                )

            } finally {

                _loading.value = false
            }
        }
    }

    fun displayPassword() {
        _isShow.value = !_isShow.value
    }
}