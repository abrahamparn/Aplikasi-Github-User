package com.example.mygithubuser.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mygithubuser.follows.Followers
import com.example.mygithubuser.follows.Following

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var userData : String = ""

    override fun getItemCount(): Int { return 2 }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = Followers()
            1 -> fragment = Following()
        }
        fragment?.arguments = Bundle().apply {
            putString(Following.ARG_USER, userData)
        }
        return fragment as Fragment
    }
}