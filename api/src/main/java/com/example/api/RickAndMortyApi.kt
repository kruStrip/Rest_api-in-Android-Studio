package com.example.api

import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): com.example.api.dto.CharacterResponseDto
}
