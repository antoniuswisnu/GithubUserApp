package com.example.githubuserapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    fun setUsername(username: String) {
        _username.value = username
    }
}