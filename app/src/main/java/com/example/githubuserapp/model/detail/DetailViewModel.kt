package com.example.githubuserapp.model.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.response.DetailResponse
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel(){

    private val _username = MutableLiveData<String>()

    val username: LiveData<String> get() = _username

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _snackbarText = MutableLiveData<String>()

    private val _followerList = MutableLiveData<List<ItemsItem>>()
    val followerList: LiveData<List<ItemsItem>> = _followerList

    private val _followingList = MutableLiveData<List<ItemsItem>>()
    val followingList: LiveData<List<ItemsItem>> = _followingList

    fun setUsername(username: String) {
        _username.value = username
    }
    companion object {
        private const val TAG = "DETAIL_ACTIVITY"
    }


    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { _detailUser.value = it }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackbarText.value = response.message()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _snackbarText.value = t.message
            }
        })
    }

    fun getFollowersData(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _followerList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Gagal ambil data followers - onFailure: ${t.message}")
                _snackbarText.value = t.message
            }
        })
    }

    fun getFollowingData(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _followingList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("TAG", "Gagal ambil data following - onFailure: ${t.message}")
                _snackbarText.value = t.message
            }
        })
    }
}