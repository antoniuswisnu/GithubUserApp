package com.example.githubuserapp.model.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.response.GithubResponse
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val _listUserGithub = MutableLiveData<List<ItemsItem>>()
    val listUserGithub: LiveData<List<ItemsItem>> = _listUserGithub

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    companion object{
        private const val TAG = "MainViewModel"
    }

    init{
        getUser()
    }

    private fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser()
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUserGithub.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackbarText.value = response.message()
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _snackbarText.value = t.message
            }
        })
    }
    fun searchUsers(query: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getSearchUsers(query)
            .enqueue(object : Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listUserGithub.value = response.body()?.items
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _snackbarText.value = response.message()
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                    _snackbarText.value = t.message
                }
            })
    }


}