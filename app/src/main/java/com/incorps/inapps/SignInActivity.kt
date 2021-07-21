package com.incorps.inapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editEmail: TextInputEditText
    private lateinit var editPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnLoginGoogle: MaterialButton
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        editEmail = findViewById(R.id.edittext_email)
        editPassword = findViewById(R.id.edittext_password)
        btnLogin = findViewById(R.id.btn_login)
        btnLoginGoogle = findViewById(R.id.btn_login_google)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        tvSignUp = findViewById(R.id.tv_sign_up)

        btnLogin.setOnClickListener(this)
        btnLoginGoogle.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth
    }

    override fun onClick(view: View?) {
        when(view) {
            btnLogin ->  {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
            btnLoginGoogle -> {
                signIn()
            }
            tvForgotPassword ->  {
                val intentForgotPasswordActivity = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intentForgotPasswordActivity)
            }
            tvSignUp ->  {
                val intentSignUpActivity = Intent(this, SignUpActivity::class.java)
                startActivity(intentSignUpActivity)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
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
                Log.d("Berhasil", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Gagal", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("authBerhasil", "signInWithCredential:success")
                    val user = auth.currentUser
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("authGagal", "signInWithCredential:failure", task.exception)
                }
            }
    }
}