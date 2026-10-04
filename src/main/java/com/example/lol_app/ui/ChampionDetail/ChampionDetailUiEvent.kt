package com.example.lol_app.ui.detail

import com.example.lol_app.data.model.SkillItem

sealed class ChampionDetailUiEvent {

    data class GetChampionDetail(
        val championId: String
    ) : ChampionDetailUiEvent()

    data class ClickSkill(
        val skill: SkillItem
    ) : ChampionDetailUiEvent()

    object ClickBack : ChampionDetailUiEvent()
}