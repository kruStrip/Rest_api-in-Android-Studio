package com.example.api.dto

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)