package com.arifikhsan.githubfavorite.ui.favorite

import androidx.lifecycle.*
import com.arifikhsan.githubfavorite.entity.User
import com.arifikhsan.githubfavorite.repository.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: UserRepository) : ViewModel() {

    val users: ArrayList<User> = repository.allUsers().toCollection(ArrayList())

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }
}

class FavoriteViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}