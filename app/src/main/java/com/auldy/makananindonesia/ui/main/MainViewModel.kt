package com.auldy.makananindonesia.ui.main

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

class MainViewModel(
    private val repository: MakananRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Makanan>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Makanan>>> = _uiState.asStateFlow()

    init {
        fetchAllMakanan()
    }

    fun fetchAllMakanan() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAllMakanan()
                .catch { exception ->
                    _uiState.value = UiState.Error(exception.localizedMessage ?: "Terjadi kesalahan memuat data")
                }
                .collect { makananList ->
                    _uiState.value = UiState.Success(makananList)
                }
        }
    }
}
