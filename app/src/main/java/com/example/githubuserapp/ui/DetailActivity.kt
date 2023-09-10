package com.example.githubuserapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.data.response.DetailResponse
import com.example.githubuserapp.data.retrofit.ApiConfig
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.model.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        private const val TAG = "DETAIL_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME")
        Log.d("DetailActivity", "Username: $username")

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        if (username != null) {
            mainViewModel.setUsername(username)
            getDetailUser(username)
        } else {
            Log.e("DetailActivity", "Username is null")
        }

        val adapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()
    }

    private fun getDetailUser(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        displayUserDetail(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayUserDetail(user: DetailResponse) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(imgDetailUser)
            tvLogin.text = user.login
            tvName.text = user.name
            tvFollowers.text = "${user.followers}"
            tvFollowing.text = "${user.following}"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}