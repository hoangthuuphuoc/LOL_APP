package com.example.lol_app.ui.detail

sealed class ChampionDetailUiEffect {

    object BackScreen : ChampionDetailUiEffect()

    data class ShowToast(
        val message: String
    ) : ChampionDetailUiEffect()
}