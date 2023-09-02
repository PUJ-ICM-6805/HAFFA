package com.example.haffa.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haffa.databinding.ActivityHomeSignupBinding

class RegistryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHomeSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}