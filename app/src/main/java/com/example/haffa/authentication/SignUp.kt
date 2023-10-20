package com.example.haffa.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.example.haffa.R
import com.example.haffa.databinding.ActivityLogInBinding
import com.example.haffa.databinding.ActivitySignUpBinding
import com.example.haffa.navigation.BottomNavigation
import com.example.haffa.utils.DatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var mAuth: FirebaseAuth

    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText
    private lateinit var fullName: EditText
    private lateinit var username: EditText
    private lateinit var telephone: EditText
    private lateinit var birthDate: EditText

    private val VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = Firebase.auth

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        emailEdit = binding.etEmail
        passEdit = binding.etPasword
        birthDate = binding.etBirthDate

        // Obtener una referencia al botón bLogIn usando View Binding
        val buttonContinue = binding.bContinue

        // Acciones a realizar con el botón "Iniciar sesión"
        buttonContinue.setOnClickListener {
            // Crea una instancia del fragmento StartRoute
            signUp()
        }
        birthDate.setOnClickListener {
            val newFragment = DatePicker(birthDate)
            newFragment.show(supportFragmentManager, "datePicker")
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

    private fun signUp() {
        val email = emailEdit.text.toString()
        val pass = passEdit.text.toString()
        var correct = false
        if (!isEmailValid(email)) {
            Toast.makeText(this@SignUp, "Email is not a valid format", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = mAuth.currentUser
                Toast.makeText(
                    this@SignUp,
                    String.format("The user %s is successfully registered", user!!.email),
                    Toast.LENGTH_LONG
                ).show()
                correct = true
            }
        }.addOnFailureListener(this) { e ->
            Toast.makeText(this@SignUp, e.message, Toast.LENGTH_LONG).show() }

        if  (correct){
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

    }



    private fun isEmailValid(emailStr: String?): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }
}