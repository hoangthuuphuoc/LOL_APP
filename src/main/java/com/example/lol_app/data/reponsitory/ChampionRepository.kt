package com.example.lol_app.data.reponsitory

import com.example.lol_app.data.api.ChampionApi
import com.example.lol_app.data.model.ChampionDetailItem
import com.example.lol_app.data.model.ChampionItem
import com.example.lol_app.data.model.SkillItem
import com.example.lol_app.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChampionRepository {
    val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ChampionApi::class.java)

    suspend fun getChampions(): List<ChampionItem> {
        val response = apiService.getChampions()

        return if (response.body() != null) {
            response.body()?.data?.values?.map { champion ->
                ChampionItem(
                    id = champion.id,
                    name = champion.name,
                    title = champion.title,
                    imageUrl = "https://ddragon.leagueoflegends.com/cdn/14.3.1/img/champion/${champion.image.full}",
                    splashUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${champion.id}_1.jpg"
                )
            } ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getChampionDetail(championId: String): ChampionDetailItem? {
        val response = apiService.getChampionDetail(championId)

        if (!response.isSuccessful || response.body() == null) {
            return null
        }

        val champion = response.body()?.data?.get(championId) ?: return null

        val passiveSkill = SkillItem(
            key = "P",
            name = champion.passive.name,
            description = cleanHtml(champion.passive.description),
            imageUrl = "https://ddragon.leagueoflegends.com/cdn/14.3.1/img/passive/${champion.passive.image.full}"
        )

        val skillKeys = listOf("Q", "W", "E", "R")

        val spellSkills = champion.spells.mapIndexed { index, spell ->
            SkillItem(
                key = skillKeys.getOrNull(index) ?: "",
                name = spell.name,
                description = cleanHtml(spell.description),
                imageUrl = "https://ddragon.leagueoflegends.com/cdn/14.3.1/img/spell/${spell.image.full}"
            )
        }

        return ChampionDetailItem(
            id = champion.id,
            name = champion.name,
            title = champion.title,
            blurb = champion.blurb,
            role = getRoleName(champion.tags),
            difficulty = getDifficultyText(champion.info.difficulty),
            splashUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${champion.id}_2.jpg",
            skills = listOf(passiveSkill) + spellSkills
        )
    }

    private fun cleanHtml(text: String): String {
        return text
            .replace(Regex("<.*?>"), "")
            .replace("&nbsp;", " ")
            .replace("&amp;", "&")
    }

    private fun getDifficultyText(value: Int): String {
        return when {
            value <= 3 -> "FÁCIL"
            value <= 7 -> "MÉDIO"
            else -> "DIFÍCIL"
        }
    }

    private fun getRoleName(tags: List<String>): String {
        val role = when {
            tags.contains("Marksman") -> "Marksman"
            tags.contains("Fighter") -> "Fighter"
            tags.contains("Tank") -> "Tank"
            tags.contains("Mage") -> "Mage"
            tags.contains("Assassin") -> "Assassin"
            tags.contains("Support") -> "Support"
            else -> tags.firstOrNull() ?: ""
        }

        return when (role) {
            "Fighter" -> "Lutador"
            "Tank" -> "Tanque"
            "Mage" -> "Mago"
            "Assassin" -> "Assassino"
            "Marksman" -> "Atirador"
            "Support" -> "Suporte"
            else -> role
        }
    }
}

