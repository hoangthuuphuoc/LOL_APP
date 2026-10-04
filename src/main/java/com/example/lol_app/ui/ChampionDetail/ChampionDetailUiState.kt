package com.example.lol_app.ui.detail

import com.example.lol_app.data.model.ChampionDetailItem
import com.example.lol_app.data.model.SkillItem

data class ChampionDetailUiState(
    val championDetail: ChampionDetailItem? = null,
    val selectedSkill: SkillItem? = null,
    val isLoading: Boolean = false
)