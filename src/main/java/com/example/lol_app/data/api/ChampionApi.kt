package com.example.lol_app.data.api

import com.example.lol_app.data.model.ChampionDetailResponse
import com.example.lol_app.data.model.ChampionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChampionApi {
    @GET("cdn/14.3.1/data/vi_VN/champion.json")
    suspend fun getChampions(): Response<ChampionResponse>

    @GET("cdn/14.3.1/data/vi_VN/champion/{championId}.json")
    suspend fun getChampionDetail(
        @Path("championId") championId: String
    ): Response<ChampionDetailResponse>
}