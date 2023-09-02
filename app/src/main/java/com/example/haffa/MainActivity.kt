package com.example.haffa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haffa.main.HomePageActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)

        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)

    }
}