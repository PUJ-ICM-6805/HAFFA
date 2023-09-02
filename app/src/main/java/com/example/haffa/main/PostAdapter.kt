package com.example.haffa.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haffa.databinding.PostCardLayoutBinding

class PostAdapter(private val context: Context, private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {

        val binding =
            PostCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val currentPost = posts[position]
        holder.bindData(currentPost)
    }

    inner class ViewHolder(private val binding: PostCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(currentPost: Post) {
            binding.username.text = currentPost.username
            binding.usernameComment.text = currentPost.username

            binding.userComment.text = currentPost.comment

            Glide.with(context).load(currentPost.imgUrl).into(binding.postPhoto)

        }
    }

}