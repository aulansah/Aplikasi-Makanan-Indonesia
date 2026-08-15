package com.auldy.makananindonesia.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auldy.makananindonesia.data.repository.MakananRepository
import com.auldy.makananindonesia.di.Injection
import com.auldy.makananindonesia.ui.detail.DetailViewModel
import com.auldy.makananindonesia.ui.main.MainViewModel

class ViewModelFactory(
    private val repository: MakananRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val repository = Injection.provideRepository(context)
                INSTANCE = ViewModelFactory(repository)
                INSTANCE!!
            }
        }
    }
}
