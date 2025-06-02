// app/src/main/java/com/example/myapplication/presentation/CharactersViewModel.kt
package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.RetrofitInstance
import com.example.myapplication.data.databases.roomdatabase.repositories.characterrepository.GetCharacterRepositoryNetwork
import com.example.myapplication.domain.models.Character
import com.example.myapplication.domain.repositories.characterrepository.IGetCharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

    private val repository: IGetCharacterRepository =
        GetCharacterRepositoryNetwork(RetrofitInstance.api)

    private val _uiState = MutableStateFlow<List<Character>>(emptyList())
    val uiState: StateFlow<List<Character>> = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val list: List<Character> = repository.getCharacters()
                _uiState.value = list
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
