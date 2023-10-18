package com.example.haffa.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser
import com.example.haffa.databinding.ActivityLogInBinding
import com.example.haffa.navigation.BottomNavigation
import java.util.regex.Matcher
import java.util.regex.Pattern

class LogIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding // Declara la variable de enlace

    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText
    private val VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = Firebase.auth

        // Inflar la vista utilizando View Binding
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtener una referencia al botón bLogIn usando View Binding
        val buttonLogIn = binding.bLogIn

        // Obtener una referencia al text view tvSignUp usando View Binding
        val textViewSignUp = binding.tvSignUp

        emailEdit = binding.etUsername
        passEdit = binding.etPasword

        // Acciones a realizar con el botón "Iniciar sesión"
        buttonLogIn.setOnClickListener {
            // Crea una instancia del fragmento StartRoute
            login()
        }

        // Acciones a realizar con el botón "Registro"
        textViewSignUp.setOnClickListener {
            // Crea una instancia del fragmento StartRoute
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent = Intent(baseContext, LogIn::class.java)
            intent.putExtra("user", currentUser.email)
            startActivity(intent)
        } else {
            //
        }
    }

    private fun login() {
        val email = emailEdit.text.toString()
        val pass = passEdit.text.toString()
        if (!isEmailValid(email)) {
            Toast.makeText(this@LogIn, "Usuario no válido.", Toast.LENGTH_SHORT).show()
            return
        }
        signInUser(email, pass)
    }

    private fun signInUser(email: String, password: String) {
        if (validateForm()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        updateUI(user)
                        val intent = Intent(this, BottomNavigation::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LogIn, "Autenticación fallida.", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = emailEdit.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailEdit.error = "Required"
            valid = false
        } else {
            emailEdit.error = null
        }
        val password = passEdit.text.toString()
        if (TextUtils.isEmpty(password)) {
            passEdit.error = "Required"
            valid = false
        } else {
            passEdit.error = null
        }
        return valid
    }


    private fun isEmailValid(emailStr: String?): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }



}