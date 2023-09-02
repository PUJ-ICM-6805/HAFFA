package com.example.haffa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.haffa.databinding.ActivityHomeLoginBinding
import com.example.haffa.main.HomePageActivity
import com.example.haffa.main.RegistryActivity
import android.os.Bundle
import com.example.haffa.journey.ShowAllJourneysActivity

class MainActivity : AppCompatActivity() {

    // View binding instance for the main activity's layout
    private lateinit var binding: ActivityHomeLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.logIn.setOnClickListener(){
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        binding.register.setOnClickListener(){
            val intent = Intent(this, RegistryActivity::class.java)
            startActivity(intent)
        }

        //val intent = Intent(this, HomePageActivity::class.java)
        //startActivity(intent)
    }
}