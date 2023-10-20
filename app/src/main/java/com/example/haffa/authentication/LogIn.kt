package com.example.haffa.authentication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import java.util.regex.Matcher
import java.util.regex.Pattern


class LogIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding // Declara la variable de enlace

    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText
    private lateinit var verification: Verification

    // Google
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 1
    lateinit var gso:GoogleSignInOptions

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

        //
        val buttonLogInGoogle = binding.bGoogle

        // Obtener una referencia al text view tvSignUp usando View Binding
        val textViewSignUp = binding.tvSignUp

        emailEdit = binding.etUsername
        passEdit = binding.etPasword

        createRequest()

        // Acciones a realizar con el botón "Iniciar sesión"
        buttonLogIn.setOnClickListener {
            login()
        }

        buttonLogInGoogle.setOnClickListener(){
            loginGoogle()
        }

        // Acciones a realizar con el botón "Registro"
        textViewSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }



    private fun login() {
        val email = emailEdit.text.toString()
        if (!verification.isEmailValid(email)) {
            Toast.makeText(this@LogIn, "Usuario no válido.", Toast.LENGTH_SHORT).show()
            return
        }
        signInUser(emailEdit, passEdit)
    }

    private fun signInUser(email: EditText, password: EditText) {
        val emailtxt = emailEdit.text.toString()
        val passtxt = passEdit.text.toString()
        if (verification.validateForm(email,password)) {
            mAuth.signInWithEmailAndPassword(emailtxt, passtxt)
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





    private fun createRequest() {
        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private fun loginGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            }
            catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        mGoogleSignInClient.signOut()
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent= Intent(this,SignUp::class.java)
                    intent.putExtra("user_email", account.email)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn, "Login Failed: ", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent = Intent(baseContext, BottomNavigation::class.java)
            intent.putExtra("user", currentUser.email)
            startActivity(intent)
        } else {
            emailEdit.setText("")
            passEdit.setText("")
        }
    }
}