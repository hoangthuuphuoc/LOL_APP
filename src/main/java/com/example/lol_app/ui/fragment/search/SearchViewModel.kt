package com.example.lol_app.ui.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lol_app.data.model.ChampionItem
import com.example.lol_app.data.reponsitory.ChampionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = ChampionRepository()

    private var allChampions = listOf<ChampionItem>()

    private val _searchResult = MutableStateFlow<List<ChampionItem>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun getChampions() {
        viewModelScope.launch {
            allChampions = repository.getChampions()
        }
    }

    fun searchChampion(text: String) {
        _query.value = text

        if (text.isEmpty()) {
            _searchResult.value = emptyList()
            return
        }

        _searchResult.value = allChampions.filter { champion ->
            champion.name.contains(text, ignoreCase = true) ||
                    champion.title.contains(text, ignoreCase = true)
        }
    }

    fun clearSearch() {
        _query.value = ""
        _searchResult.value = emptyList()
    }
}