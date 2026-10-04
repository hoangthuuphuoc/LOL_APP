package com.example.lol_app.data.model

class ChampionResponse(
    val data: Map<String, ChampionDto>
)

data class ChampionDto(
    val id: String,
    val name: String,
    val title: String,
    val image: ChampionImage
)

data class ChampionImage(
    val full: String
)