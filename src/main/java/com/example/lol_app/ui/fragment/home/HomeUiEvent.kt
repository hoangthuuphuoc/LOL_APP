package com.example.lol_app.ui.fragment.home

sealed class HomeUiEvent {

    data class ClickItem(
        val currentItem: Int,
        val totalItem: Int
    ) : HomeUiEvent()

    object ClickButton : HomeUiEvent()

    data class SendMessage(
        val message: String
    ) : HomeUiEvent()
}