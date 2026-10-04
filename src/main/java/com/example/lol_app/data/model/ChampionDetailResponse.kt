package com.example.lol_app.data.model

class ChampionDetailResponse(
    val data: Map<String, ChampionDetailDto>
)

class ChampionDetailDto(
    val id: String,
    val name: String,
    val title: String,
    val lore: String,
    val blurb: String,
    val tags: List<String>,
    val info: ChampionInfoDto,
    val spells: List<SpellDto>,
    val passive: PassiveDto
)

 class ChampionInfoDto(
    val attack: Int,
    val defense: Int,
    val magic: Int,
    val difficulty: Int
)

 class SpellDto(
    val id: String,
    val name: String,
    val description: String,
    val image: SpellImageDto
)

 class PassiveDto(
    val name: String,
    val description: String,
    val image: SpellImageDto
)

 class SpellImageDto(
    val full: String
)