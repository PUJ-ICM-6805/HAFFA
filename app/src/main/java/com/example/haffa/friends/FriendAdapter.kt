package com.example.haffa.friends

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haffa.R
import com.example.haffa.databinding.CardFriendBinding

import com.bumptech.glide.load.resource.bitmap.CircleCrop

class FriendAdapter(private val context: Context, private val friends: List<Friend>) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            CardFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendAdapter.ViewHolder, position: Int) {
        val currentFriend = friends[position]
        holder.bindData(currentFriend)

        holder.itemView.setOnClickListener{

            val bundle = Bundle()
            bundle.putSerializable("friend", currentFriend)

            val fragmentTransaction = (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            val newFragment = FriendsRoutes()
            newFragment.arguments = bundle
            fragmentTransaction.replace(R.id.frame_container, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

    override fun getItemCount(): Int {
        return friends.size
    }

    inner class ViewHolder(private val binding: CardFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(currentFriend: Friend){
            binding.name.text = currentFriend.name

            Glide.with(context)
                .load(currentFriend.imgUrl)
                .transform(CircleCrop())
                .into(binding.cardImg)

        }
    }

}