package com.example.mygithubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mygithubuser.R
import com.example.mygithubuser.data.Items
import com.example.mygithubuser.databinding.ItemRowUserBinding

class FollowListAdapter(private val listUserFollow: ArrayList<Items>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>(){

    private lateinit var binding: ItemRowUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ListViewHolder {
        binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserAdapter.ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ListViewHolder, position: Int) {
        val (login, avatarUrl) = listUserFollow[position]

        Glide.with(holder.itemView.context).load(avatarUrl).transition(DrawableTransitionOptions.withCrossFade()).into(holder.itemView.findViewById(R.id.img_item_photo))
        holder.binding.tvItemName.text = login

    }

    override fun getItemCount(): Int = listUserFollow.size
}