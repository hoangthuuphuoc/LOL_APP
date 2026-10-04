package com.example.lol_app.ui.fragment.home

import com.example.lol_app.data.model.ChampionItem

data class HomeUiState(
    val listChampion: List<ChampionItem> = emptyList(),
    val listRecommended: List<ChampionItem> = emptyList(),
    val isLoading: Boolean = false
)