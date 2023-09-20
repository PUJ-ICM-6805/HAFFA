package com.example.haffa.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haffa.databinding.ActivityLogInBinding
import com.example.haffa.navigation.BottomNavigation

class LogIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding // Declara la variable de enlace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar la vista utilizando View Binding
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtener una referencia al botón bLogIn usando View Binding
        val buttonLogIn = binding.bLogIn

        // Obtener una referencia al text view tvSignUp usando View Binding
        val textViewSignUp = binding.tvSignUp

        // Acciones a realizar con el botón "Iniciar sesión"
        buttonLogIn.setOnClickListener {
            // Crea una instancia del fragmento StartRoute
            val intent = Intent(this, BottomNavigation::class.java)
            startActivity(intent)
        }

        // Acciones a realizar con el botón "Iniciar sesión"
        textViewSignUp.setOnClickListener {
            // Crea una instancia del fragmento StartRoute
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }


    }
}