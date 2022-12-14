package com.example.tbc_final.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_final.data.repository.local.FavoritesRepositoryImpl
import com.example.tbc_final.domain.model.Sneakers
import com.example.tbc_final.domain.repository.local.FavoritesRepository
import com.example.tbc_final.domain.use_case.room.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val _favoriteSneakers = MutableSharedFlow<List<Sneakers>>()
    val favoriteSneaker get() = _favoriteSneakers.asSharedFlow()


    fun getFavorites() {
        viewModelScope.launch {
            val response = favoritesUseCase.invoke()
            _favoriteSneakers.emit(response)

        }
    }
}