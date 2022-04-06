package com.example.mygithubuser.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mygithubuser.data.Items
import com.example.mygithubuser.databinding.ItemRowUserBinding

class FavoriteUserAdapter: RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>(){

    private val listfavorite = ArrayList<Items>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var binding: ItemRowUserBinding

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<Items>) {
        listfavorite.clear()
        listfavorite.addAll(users)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatarUrl) = listfavorite[position]
        holder.binding.tvItemName.text = login
        Glide.with(holder.itemView.context).load(avatarUrl).into(holder.binding.imgItemPhoto)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listfavorite[holder.bindingAdapterPosition]) }
    }


    override fun getItemCount(): Int = listfavorite.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Items)
    }

}
