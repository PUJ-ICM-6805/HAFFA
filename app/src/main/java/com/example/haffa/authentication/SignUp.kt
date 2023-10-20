package com.example.haffa.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.haffa.databinding.ActivitySignUpBinding
import com.example.haffa.utils.DatePicker
import com.example.haffa.utils.Verification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var verification: Verification

    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText
    private lateinit var fullName: EditText
    private lateinit var username: EditText
    private lateinit var telephone: EditText
    private lateinit var birthDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = Firebase.auth

        verification = Verification()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        emailEdit = binding.etEmail
        passEdit = binding.etPasword
        birthDate = binding.etBirthDate
        telephone = binding.etPhoneNumber

        // Obtener una referencia al botón bLogIn usando View Binding
        val buttonContinue = binding.bContinue

        val userEmail = intent.getStringExtra("user_email")

        // Now you can use userEmail as needed
        if (userEmail != null) {
            // You can set the email to the emailEdit field if needed
            emailEdit.setText(userEmail)
            emailEdit.isFocusable = false
            emailEdit.isFocusableInTouchMode = false
            passEdit.isVisible = false
        }

        buttonContinue.setOnClickListener {
            if (userEmail != null){
                saveAdditionalInfoToDatabase()
            }
            else{
                signUp()
            }
        }
        birthDate.setOnClickListener {
            val newFragment = DatePicker(birthDate)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun signUp() {
        val email = emailEdit.text.toString()
        val pass = passEdit.text.toString()
        var correct = false
        if (!verification.isEmailValid(email)) {
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

    private fun saveAdditionalInfoToDatabase(){
        if (!verification.isTelephoneValid(telephone.toString())){
            telephone.error = "Formato teléfono inválido"
            return
        }

        // Agregar verificación de username repetido
    }

}