package com.example.lol_app.ui.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {
    private val _next = MutableStateFlow<Boolean>(false)
    val next=_next.asStateFlow()

     fun nextScreen(){
        viewModelScope.launch {
            delay(3000)
            _next.emit(true)
        }
    }
}