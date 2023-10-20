package com.example.haffa.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.haffa.R
import com.example.haffa.databinding.ActivityLogInBinding
import com.example.haffa.navigation.BottomNavigation
import com.example.haffa.utils.Verification
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogIn : AppCompatActivity() {

    // View binding for the LoginActivity.
    private lateinit var binding: ActivityLogInBinding

    // Firebase Authentication instance for user authentication.
    private lateinit var mAuth: FirebaseAuth
    // An instance of the Verification class for performing user input validation.
    private lateinit var verification: Verification

    // EditText field for user email input.
    private lateinit var emailEdit: EditText
    // EditText field for user password input.
    private lateinit var passEdit: EditText

    // Google Sign-In
    // GoogleSignInClient for handling Google sign-in authentication.
    lateinit var mGoogleSignInClient: GoogleSignInClient
    // Request code for Google Sign-In authentication.
    val RC_SIGN_IN: Int = 1
    // GoogleSignInOptions for configuring Google sign-in requests.
    lateinit var gso: GoogleSignInOptions

    /**
     * Initializes the activity and sets up the user interface elements.
     *
     * @param savedInstanceState The saved state of the activity (if available).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Authentication
        mAuth = Firebase.auth

        // Initialize the Verification utility
        verification = Verification()

        // Inflate the view using View Binding
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Get a reference to the "Log In" button using View Binding
        val buttonLogIn = binding.bLogIn

        // Get a reference to the "Google" button using View Binding
        val buttonLogInGoogle = binding.bGoogle

        // Get a reference to the "Sign Up" text view using View Binding
        val textViewSignUp = binding.tvSignUp

        // Assign references to email and password EditText fields
        emailEdit = binding.etUsername
        passEdit = binding.etPasword

        // Create a Google Sign-In request
        createRequest()

        // Set an onClick listener for the "Log In" button
        buttonLogIn.setOnClickListener {
            login()
        }

        // Set an onClick listener for the "Google" button
        buttonLogInGoogle.setOnClickListener() {
            loginGoogle()
        }

        // Set an onClick listener for the "Sign Up" text view
        textViewSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    /**
     * Called when the activity is starting or resuming.
     *
     * This method is used for checking the current user's authentication status
     * and updating the user interface accordingly.
     */
    override fun onStart() {
        super.onStart()

        // Get the currently signed-in user
        val currentUser = mAuth.currentUser

        // Update the user interface based on the current user's authentication status
        updateUI(currentUser)
    }

    /**
     * Attempts to log in the user with the provided email and password.
     *
     * This function is triggered when the "Log In" button is clicked. It validates the user's email,
     * and if it's valid, it calls the [signInUser] function to attempt authentication.
     *
     * @param email The email entered by the user for login.
     */
    private fun login() {
        val email = emailEdit.text.toString()

        // Validate the user's email address
        if (!verification.isEmailValid(email)) {
            Toast.makeText(this@LogIn, "Usuario Incorrecto.", Toast.LENGTH_SHORT).show()
            return
        }

        // Proceed to sign in with the user's credentials
        signInUser(emailEdit, passEdit)
    }

    /**
     * Attempts to sign in the user with the provided email and password.
     *
     * This function is responsible for signing in the user using their email and password.
     * It first validates the email and password, and if they are valid, it attempts to sign in
     * using Firebase Authentication. After the sign-in attempt, it either navigates to the main
     * screen or displays an error message.
     *
     * @param email The email entered by the user for login.
     * @param password The password entered by the user for login.
     */
    private fun signInUser(email: EditText, password: EditText) {
        val emailtxt = emailEdit.text.toString()
        val passtxt = passEdit.text.toString()

        // Validate the form with email and password
        if (verification.validateForm(email, password)) {
            mAuth.signInWithEmailAndPassword(emailtxt, passtxt)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser

                        // Navigate to the main screen on successful authentication
                        updateUI(user)
                        val intent = Intent(this, BottomNavigation::class.java)
                        startActivity(intent)
                    } else {
                        // Display an error message on failed authentication
                        Toast.makeText(this@LogIn, "Autenticación fallida.", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    /**
     * - GOOGLE AUTHENTICATION
     */

    /**
     * Creates a Google Sign-In request.
     *
     * This function is responsible for creating a Google Sign-In request by configuring
     * the options, including the request for email and ID token. It uses the default web
     * client ID from resources and initializes a GoogleSignInClient.
     */
    private fun createRequest() {
        // Configure Google Sign-In options
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Initialize the GoogleSignInClient
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    /**
     * Initiates Google Sign-In.
     *
     * This function triggers the Google Sign-In process by obtaining an intent to sign in
     * with Google. The result of this operation is handled by `onActivityResult`.
     */
    private fun loginGoogle() {
        // Get an intent for Google Sign-In
        val signInIntent = mGoogleSignInClient.signInIntent

        // Start the Google Sign-In process and handle the result in onActivityResult
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * Handles the result of an activity started with `startActivityForResult`.
     *
     * This function is invoked when the result of a previously launched activity is available.
     * It specifically handles the result of the Google Sign-In process.
     *
     * @param requestCode The request code to identify the operation.
     * @param resultCode The result code indicating the success or failure of the operation.
     * @param data The intent containing the result data from the activity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the result is from Google Sign-In
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Google Sign-In was successful, authenticate with Firebase using the obtained account information
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Handle the case where Google Sign-In failed by displaying an appropriate message to the user
                Toast.makeText(this, "Autenticación Fallida", Toast.LENGTH_SHORT).show()
            }
        }

        // Sign out from the Google Sign-In client after processing
        mGoogleSignInClient.signOut()
    }

    /**
     * Authenticates the user with Firebase using Google Sign-In credentials.
     *
     * This function is called when a user successfully signs in with Google.
     *
     * @param account The GoogleSignInAccount object containing user information.
     */
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        // Create Firebase credentials from the Google Sign-In account
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        // Sign in to Firebase with the Google credentials
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // If the sign-in is successful, navigate to the SignUp activity
                    val intent = Intent(this, SignUp::class.java)
                    intent.putExtra("user_email", account.email)
                    startActivity(intent)
                } else {
                    // If the sign-in fails, display a failure message to the user
                    Toast.makeText(this@LogIn, "Autneticación Fallida: ", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Update the user interface based on the current user's status.
     *
     * If a user is signed in, navigate to the BottomNavigation activity.
     * If no user is signed in, clear the email and password fields.
     *
     * @param currentUser The current user retrieved from Firebase Auth.
     */
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            // If a user is signed in, navigate to the BottomNavigation activity
            val intent = Intent(baseContext, BottomNavigation::class.java)
            intent.putExtra("user", currentUser.email)
            startActivity(intent)
        } else {
            // If no user is signed in, clear the email and password fields
            emailEdit.setText("")
            passEdit.setText("")
        }
    }
}