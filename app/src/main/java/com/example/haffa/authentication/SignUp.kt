package com.example.haffa.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.haffa.databinding.ActivitySignUpBinding
import com.example.haffa.navigation.BottomNavigation
import com.example.haffa.utils.DatePicker
import com.example.haffa.utils.Verification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    // View binding for the SignUp activity layout.
    private lateinit var binding: ActivitySignUpBinding

    // Firebase Authentication instance for user registration and authentication.
    private lateinit var mAuth: FirebaseAuth

    // Helper class for data validation, including email, phone, and password.
    private lateinit var verification: Verification

    // EditText for user's email input.
    private lateinit var emailEdit: EditText
    // EditText for user's password input.
    private lateinit var passEdit: EditText
    // EditText for user's full name input.
    private lateinit var fullName: EditText
    // EditText for user's username input.
    private lateinit var username: EditText
    // EditText for user's telephone number input.
    private lateinit var telephone: EditText
    // EditText for user's birthdate input.
    private lateinit var birthDate: EditText

    /**
     * Initialize the activity, views, and event listeners.
     *
     * @param savedInstanceState The saved instance state (if any).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth instance
        mAuth = Firebase.auth

        // Initialize Verification instance
        verification = Verification()

        // Inflate the view using View Binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Assign references to EditText fields
        emailEdit = binding.etEmail
        passEdit = binding.etPasword
        birthDate = binding.etBirthDate
        telephone = binding.etPhoneNumber

        // Get a reference to the "Continue" button using View Binding
        val buttonContinue = binding.bContinue

        // Get the email from the intent (if it exists)
        val userEmail = intent.getStringExtra("user_email")

        // If there's an email in the intent, set it in the email field and disable password field
        if (userEmail != null) {
            emailEdit.setText(userEmail)
            emailEdit.isFocusable = false
            emailEdit.isFocusableInTouchMode = false
            passEdit.isVisible = false
        }

        // Set an onClick listener for the "Continue" button
        buttonContinue.setOnClickListener {
            if (userEmail != null){
                saveAdditionalInfoToDatabase()
            }
            else{
                signUp()
            }
        }

        // Set a click listener for the birthDate field to show a date picker
        birthDate.setOnClickListener {
            val newFragment = DatePicker(birthDate)
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    /**
     * Registers a user with the provided email and password in Firebase Auth.
     *
     * @param email The user's email.
     * @param pass The user's password.
     */
    private fun signUp() {
        val email = emailEdit.text.toString()
        val pass = passEdit.text.toString()
        var correct = false

        // Verify and save additional information in the database
        if (!saveAdditionalInfoToDatabase()) {
            return
        }

        // Create a user in Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = mAuth.currentUser
                Toast.makeText(
                    this@SignUp,
                    String.format("The user %s is successfully registered", user!!.email),
                    Toast.LENGTH_LONG
                ).show()
                correct = true
                val intent = Intent(this, BottomNavigation::class.java)
                startActivity(intent)
            }
        }.addOnFailureListener(this) { e ->
            Toast.makeText(this@SignUp, e.message, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Verifies and saves additional user information in the database.
     *
     * @return `true` if the data is valid and successfully saved; `false` otherwise.
     *
     * @param emailEdit The EditText field for the user's email.
     * @param telephone The EditText field for the user's telephone number.
     * @param passEdit The EditText field for the user's password.
     * @param birthDate The EditText field for the user's birth date.
     */
    private fun saveAdditionalInfoToDatabase(): Boolean {
        // Para ANDRÉS: Aquí es donde tienes que aggregar la información adicional,
        // después de verificar los datos, los agregas y haces un intent  :))

        // Verify and save additional information
        return verification.isDataValid(emailEdit, telephone, passEdit, birthDate)
    }
}
