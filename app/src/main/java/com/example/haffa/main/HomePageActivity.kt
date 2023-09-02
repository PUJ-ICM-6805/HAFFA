package com.example.haffa.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.ActivityHomeMainBinding

class HomePageActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeMainBinding
    private lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.RecyclerViewP

        recyclerView.layoutManager = LinearLayoutManager(this)

        postAdapter = PostAdapter(this, sampleData())
        recyclerView.adapter = postAdapter
    }

    private fun sampleData(): MutableList<Post> {
        val posts = mutableListOf<Post>()

        posts.add(
            Post(
                username = "UserA",
                comment = "Comment A",
                imgUrl = "https://tinyurl.com/3sakwxmr"
            )
        )

        posts.add(
            Post(
                username = "UserB",
                comment = "Comment B",
                imgUrl = "https://tinyurl.com/3sakwxmr"
            )
        )

        posts.add(
            Post(
                username = "UserC",
                comment = "Comment C",
                imgUrl = "https://tinyurl.com/3sakwxmr"
            )
        )

        return posts
    }
}