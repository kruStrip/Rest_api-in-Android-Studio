// Файл: data/src/main/java/com/example/myapplication/data/databases/roomdatabase/repositories/characterrepository/GetCharacterRepositoryNetwork.kt

package com.example.myapplication.data.databases.roomdatabase.repositories.characterrepository

import com.example.api.RickAndMortyApi
import com.example.api.dto.CharacterDto
import com.example.myapplication.domain.models.Character
import com.example.myapplication.domain.repositories.characterrepository.IGetCharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCharacterRepositoryNetwork(
    private val api: RickAndMortyApi
) : IGetCharacterRepository {

    override suspend fun getCharacters(): List<Character> = withContext(Dispatchers.IO) {
        // 1) Делаем Retrofit-запрос
        val responseDto = api.getCharacters()

        // 2) Берём список CharacterDto и преобразуем в List<Character>
        responseDto.results.map { dto: CharacterDto ->
            Character(
                id = dto.id,
                name = dto.name,
                status = dto.status,
                species = dto.species,
                image = dto.image
            )
        }
    }
}
