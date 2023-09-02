package com.example.haffa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haffa.journey.ShowAllJourneysActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)

        setContentView(R.layout.activity_main)

        //Test
        val intent = Intent(this, ShowAllJourneysActivity::class.java)
        startActivity(intent)
    }
}