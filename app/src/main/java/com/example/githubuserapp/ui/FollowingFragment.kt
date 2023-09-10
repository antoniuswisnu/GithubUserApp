package com.example.githubuserapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.FollowingAdapter
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.data.retrofit.ApiConfig
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.model.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        mainViewModel.username.observe(viewLifecycleOwner) { username ->
            getFollowingData(username)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.rvUserFollowing)
        followingAdapter = FollowingAdapter()

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = followingAdapter

        return view
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity)
        adapter = FollowingAdapter()
    }

    private fun getFollowingData(username: String){
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    Log.e("USERNAME",username)
                    val followingList = response.body()
                    if(followingList != null){
                        setFollowingData(followingList)
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
                Log.e("TAG", "Gagal ambil data following - onFailure: ${t.message}")
                Toast.makeText(requireContext(), "Gagal memuat data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setFollowingData(followingList: List<ItemsItem>){
        followingAdapter.submitList(followingList)
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}