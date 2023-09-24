package com.example.githubuserapp.model.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.database.FavoriteDAO
import com.example.githubuserapp.database.FavoriteRoomDatabase
import com.example.githubuserapp.database.FavoriteUser

class FavoriteModelFactory(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteDAO?

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private lateinit var userDb: FavoriteRoomDatabase
    }

    init {
        userDb = FavoriteRoomDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        _isLoading.value = true
        val listUser = userDao?.getFavoriteUser()
        _isLoading.value = false
        return listUser
    }
}