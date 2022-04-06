package com.example.mygithubuser.follows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.adapters.FollowListAdapter
import com.example.mygithubuser.data.FollowerResponseItem
import com.example.mygithubuser.data.Items
import com.example.mygithubuser.databinding.FragmentFollowersBinding

class Followers : Fragment() {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val followersViewModel by viewModels<FollowerViewModel>()

    companion object {
        const val ARG_USER = "user_data"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val name = arguments?.getString(ARG_USER)

        followersViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        followersViewModel.follower.observe(viewLifecycleOwner, {
            setFollowerUser(it)
        })

        if (name != null) {
            followersViewModel.getFollowerUser(name)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showRecyclerList(list: ArrayList<Items>) {

        binding.RvFollowingList.layoutManager = LinearLayoutManager(activity)
        val listUserAdapter = FollowListAdapter(list)
        binding.RvFollowingList.adapter = listUserAdapter

    }

    private fun setFollowerUser(users: List<FollowerResponseItem>){
        val listUsers = ArrayList<Items>()
        for (user in users) {
            val userItem = Items(
                user.login,
                user.avatarUrl,
                user.id
            )
            listUsers.add(userItem)
        }

        showRecyclerList(listUsers)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}