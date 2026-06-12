package com.qyupaww.jetpackcomposedigidex.data.remote

import com.qyupaww.jetpackcomposedigidex.data.remote.responses.Digimon
import com.qyupaww.jetpackcomposedigidex.data.remote.responses.DigimonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DigimonApi {
    @GET("digimon")
    suspend fun getDigimonList(
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): DigimonList

    @GET("digimon/{name}")
    suspend fun getDigimonInfo(
        @Path("name") name: String
    ): Digimon
}