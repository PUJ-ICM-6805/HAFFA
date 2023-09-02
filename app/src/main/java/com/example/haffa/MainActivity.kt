package com.example.haffa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haffa.journey.ShowAllJourneysActivity
import com.example.haffa.main.HomePageActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)

        setContentView(R.layout.activity_main)

        //Test
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
    }
}