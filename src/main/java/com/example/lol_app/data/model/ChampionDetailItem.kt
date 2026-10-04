package com.example.lol_app.data.model

class ChampionDetailItem(
    val id: String,
    val name: String,
    val title: String,
    val blurb: String,
    val role: String,
    val difficulty: String,
    val splashUrl: String,
    val skills: List<SkillItem>
)

data class SkillItem(
    val key: String,
    val name: String,
    val description: String,
    val imageUrl: String
)