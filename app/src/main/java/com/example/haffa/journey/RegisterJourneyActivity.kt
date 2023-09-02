package com.example.haffa.journey

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haffa.R
import com.example.haffa.databinding.ActivityRegisterJourneyBinding
import com.example.haffa.databinding.ActivityShowAllJorneysBinding

class RegisterJourneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterJourneyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterJourneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewJourneysButton.setOnClickListener {
            val intent = Intent(this,
                ShowAllJourneysActivity::class.java)
            startActivity(intent)
        }
    }
}