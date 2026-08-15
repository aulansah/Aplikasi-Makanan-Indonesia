package com.auldy.makananindonesia.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auldy.makananindonesia.data.model.Makanan
import com.auldy.makananindonesia.data.repository.MakananRepository
import com.auldy.makananindonesia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val repository: MakananRepository
) : ViewModel() {

    companion object {
        const val CATEGORY_ALL = "Semua"
    }

    // Master list straight from Room, always up to date via Flow.
    private val masterList: StateFlow<List<Makanan>> =
        repository.getAllMakanan()
            .catch { emit(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow(CATEGORY_ALL)
    val selectedCategory: StateFlow<String> = _selectedCategory

    /** Filtered result driven by search query + selected category chip, combined live. */
    val uiState: StateFlow<UiState<List<Makanan>>> =
        combine(masterList, _searchQuery, _selectedCategory) { list, query, category ->
            if (list.isEmpty()) {
                UiState.Loading
            } else {
                val filtered = list.filter { makanan ->
                    val matchesCategory = category == CATEGORY_ALL || makanan.category.equals(category, ignoreCase = true)
                    val matchesQuery = query.isBlank() ||
                        makanan.nama.contains(query, ignoreCase = true) ||
                        makanan.detail.contains(query, ignoreCase = true) ||
                        makanan.category.contains(query, ignoreCase = true)
                    matchesCategory && matchesQuery
                }
                UiState.Success(filtered)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)

    /**
     * Featured "Rekomendasi Hari Ini" card — always sourced from the FULL master list
     * (independent of active search/filter) so the highlight card doesn't disappear
     * while the person is filtering the list below it.
     */
    val featured: StateFlow<Makanan?> =
        masterList.map { list ->
            list.firstOrNull { it.isFeatured } ?: list.maxByOrNull { it.rating }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }
}
