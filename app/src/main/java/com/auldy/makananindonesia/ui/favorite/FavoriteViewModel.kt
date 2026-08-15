package com.auldy.makananindonesia.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auldy.makananindonesia.data.model.Makanan
import com.auldy.makananindonesia.data.repository.MakananRepository
import com.auldy.makananindonesia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: MakananRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Makanan>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Makanan>>> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getFavoriteMakanan()
                .catch { e ->
                    _uiState.value = UiState.Error(e.localizedMessage ?: "Terjadi kesalahan memuat favorit")
                }
                .collect { list ->
                    _uiState.value = UiState.Success(list)
                }
        }
    }
}
