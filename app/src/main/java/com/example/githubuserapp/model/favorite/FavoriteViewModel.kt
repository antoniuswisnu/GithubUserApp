package com.example.githubuserapp.model.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubuserapp.database.FavoriteDAO
import com.example.githubuserapp.database.FavoriteRoomDatabase
import com.example.githubuserapp.database.FavoriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteDAO?
    private var userDb: FavoriteRoomDatabase? = FavoriteRoomDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                id,
                username,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}

