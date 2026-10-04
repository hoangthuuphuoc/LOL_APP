package com.example.lol_app.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lol_app.data.reponsitory.ChampionRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeFragementViewModel : ViewModel() {

    private val repository = ChampionRepository()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ClickItem -> {
                handleClickItem(
                    currentItem = event.currentItem,
                    totalItem = event.totalItem
                )
            }

            HomeUiEvent.ClickButton -> {
                sendEffect(HomeUiEffect.NavigationScreen)
            }

            is HomeUiEvent.SendMessage -> {
                sendEffect(
                    HomeUiEffect.ShowToast(event.message)
                )
            }
        }
    }

    private fun handleClickItem(
        currentItem: Int,
        totalItem: Int
    ) {
        if (totalItem <= 0) return

        val nextItem = if (currentItem < totalItem - 1) {
            currentItem + 1
        } else {
            0
        }

        sendEffect(
            HomeUiEffect.SetItemRecommended(nextItem)
        )
    }

    private fun sendEffect(effect: HomeUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }

    fun getChampions() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val list = repository.getChampions()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        listChampion = list,
                        listRecommended = list.take(5)
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(isLoading = false)
                }

                sendEffect(
                    HomeUiEffect.ShowToast(
                        exception.message ?: "Không thể tải danh sách tướng"
                    )
                )
            }
        }
    }
}