package com.example.myapplication.domain.repositories.characterrepository
import com.example.myapplication.domain.models.Character

interface IGetCharacterRepository {
    suspend fun getCharacters(): List<Character>
}