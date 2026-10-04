package com.example.lol_app.ui.fragment.home

sealed class HomeUiEffect {

    object NavigationScreen : HomeUiEffect()

    data class ShowToast(
        val message: String
    ) : HomeUiEffect()

    data class SetItemRecommended(
        val index: Int
    ) : HomeUiEffect()
}