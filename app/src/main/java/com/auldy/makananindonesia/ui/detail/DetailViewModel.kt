package com.auldy.makananindonesia.ui.detail

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

class DetailViewModel(
    private val repository: MakananRepository
) : ViewModel() {

    private val _makananState = MutableStateFlow<UiState<Makanan>>(UiState.Loading)
    val makananState: StateFlow<UiState<Makanan>> = _makananState.asStateFlow()

    fun getMakananById(id: Int) {
        viewModelScope.launch {
            _makananState.value = UiState.Loading
            repository.getMakananById(id)
                .catch { exception ->
                    _makananState.value = UiState.Error(exception.localizedMessage ?: "Terjadi kesalahan memuat detail")
                }
                .collect { makanan ->
                    if (makanan != null) {
                        _makananState.value = UiState.Success(makanan)
                    } else {
                        _makananState.value = UiState.Error("Makanan tidak ditemukan")
                    }
                }
        }
    }

    fun setFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.setFavorite(id, isFavorite)
        }
    }
}
