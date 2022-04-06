package com.example.mygithubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.mygithubuser.R
import com.example.mygithubuser.adapters.SectionsPagerAdapter
import com.example.mygithubuser.data.DetailResponse
import com.example.mygithubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    companion object {
        const val EXTRA_PERSON = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_layout_text_1,
            R.string.tab_layout_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val login = intent.getStringExtra(EXTRA_PERSON)
        title = login

        if(login != null){
            detailViewModel.getDetailUser(login)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.listUsers.observe(this, {
            setUserDetail(it)
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        sectionsPagerAdapter.userData = login.toString()

        TabLayoutMediator(binding.tabLayout, binding.viewPager)
        { tab, position -> tab.text = resources.getString(TAB_TITLES[position]) }.attach()

        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUsers(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0 ){
                        binding.toggleFavorite.isChecked = true
                        isCheck = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        isCheck = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isCheck = !isCheck
            if (isCheck){
                detailViewModel.addUserToFavorite(login.toString(), id, avatarUrl.toString())
            }else{
                detailViewModel.removeFromFavoriteUsers(id)
            }
            binding.toggleFavorite.isChecked = isCheck
        }

    }


     fun setUserDetail(user: DetailResponse?) {
        if (user != null) {
            val unknown = "Unknown"
            Glide.with(this).load(user.avatarUrl).into(binding.imgAvatar)

            binding.apply {
                tvName.text = user.name
                tvCompany.text = user.company ?: unknown
                tvLocation.text = user.location ?: unknown
                tvFollowingLayout2.text = user.following.toString()
                tvFollowersLayout2.text = user.followers.toString()
                tvRepositoryLayout2.text = user.publicRepos.toString()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}