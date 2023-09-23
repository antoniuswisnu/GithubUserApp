package com.example.githubuserapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.FollowersAdapter
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.model.detail.DetailViewModel

class FollowersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        detailViewModel.username.observe(viewLifecycleOwner) { username ->
            getFollowersData(username)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.followerList.observe(viewLifecycleOwner) { followersList ->
            followersList?.let {
                followersAdapter.submitList(it)
            }
        }

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.rvUserFollowers)
        followersAdapter = FollowersAdapter()

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = followersAdapter

        return view
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity)
        adapter = FollowersAdapter()
    }

    private fun getFollowersData(username: String) {
        showLoading(false)
        detailViewModel.getFollowersData(username)
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