package com.example.haffa.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haffa.R
import com.example.haffa.databinding.ActivityLogInBinding
import com.example.haffa.databinding.ActivitySignUpBinding
import com.example.haffa.navigation.BottomNavigation

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtener una referencia al botón bLogIn usando View Binding
        val buttonContinue = binding.bContinue

        // Acciones a realizar con el botón "Iniciar sesión"
        buttonContinue.setOnClickListener {
            // Crea una instancia del fragmento StartRoute
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }
    }
}