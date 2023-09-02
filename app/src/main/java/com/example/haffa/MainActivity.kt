package com.example.haffa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haffa.Profile.Gallery
import com.example.haffa.Profile.publishView
import com.example.haffa.journey.activity.ShowAllJourneysActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Test
        val intent = Intent(this, Gallery::class.java)
        startActivity(intent)
    }
}