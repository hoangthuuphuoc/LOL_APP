package com.example.lol_app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lol_app.data.reponsitory.ChampionRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChampionDetailViewModel : ViewModel() {

    private val repository = ChampionRepository()

    private val _uiState = MutableStateFlow(
        ChampionDetailUiState()
    )
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ChampionDetailUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: ChampionDetailUiEvent) {
        when (event) {
            is ChampionDetailUiEvent.GetChampionDetail -> {
                getChampionDetail(event.championId)
            }

            is ChampionDetailUiEvent.ClickSkill -> {
                _uiState.update {
                    it.copy(selectedSkill = event.skill)
                }
            }

            ChampionDetailUiEvent.ClickBack -> {
                sendEffect(
                    ChampionDetailUiEffect.BackScreen
                )
            }
        }
    }

    private fun getChampionDetail(championId: String) {
        viewModelScope.launch {
            try {
                val champion =
                    repository.getChampionDetail(championId)

                _uiState.update {
                    it.copy(
                        championDetail = champion,
                        selectedSkill = champion?.skills?.firstOrNull(),
                        isLoading = false
                    )
                }
            } catch (exception: Exception) {
                sendEffect(
                    ChampionDetailUiEffect.ShowToast(
                        exception.message
                            ?: "Không thể tải thông tin tướng"
                    )
                )
            }
        }
    }


    private fun sendEffect(
        effect: ChampionDetailUiEffect
    ) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}