package com.incorps.inapps

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.Tools

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var progressDialog: ProgressDialog

    private lateinit var editEmail: TextInputEditText
    private lateinit var editPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnLoginGoogle: MaterialButton
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvSignUp: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore

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

        accountSessionPreferences = AccountSessionPreferences(this)

        // Set Progress Dialog
        progressDialog = ProgressDialog(this)

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        // Set up Firestore
        db = Firebase.firestore
    }

    override fun onClick(view: View?) {
        when(view) {
            btnLogin ->  {
                when {
                    editEmail.text.toString() == "" -> {
                        editEmail.error = resources.getString(R.string.error_email)
                    }
                    editPassword.text.toString() == "" -> {
                        editPassword.error = resources.getString(R.string.error_password)
                    }
                    else -> {
                        signIn(editEmail.text.toString(), editPassword.text.toString())
                    }
                }
            }
            btnLoginGoogle -> {
                signInGoogle()
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

    private fun signIn(email: String, password: String) {
        progressDialog.setMessage(resources.getString(R.string.loggingin))
        progressDialog.show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    progressDialog.dismiss()

                    val user = auth.currentUser

                    user?.let {
                        db.collection("user_accounts").document(it.uid).get().addOnSuccessListener { document ->

                            accountSessionPreferences.isLogin = true
                            accountSessionPreferences.idUser = it.uid
                            accountSessionPreferences.emailUser = email
                            accountSessionPreferences.nameUser = it.displayName.toString()
                            accountSessionPreferences.phoneUser = document.data?.get("phone").toString()
                            accountSessionPreferences.addressUser = document.data?.get("address").toString()
                            accountSessionPreferences.passwordUser = password
                        }

                        // Show Toast
                        val message = "Login berhasil! Selamat datang di In-Apps"
                        Tools.showCustomToastSuccess(this, layoutInflater, resources, message)

                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                    }

                } else {
                    progressDialog.dismiss()

                    // Show Toast
                    val message = "Login gagal! Coba Lagi"
                    Tools.showCustomToastFailed(this, layoutInflater, resources, message)
                }
            }
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        progressDialog.setMessage(resources.getString(R.string.loggingin))
        progressDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            progressDialog.dismiss()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Berhasil", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, resources.getString(R.string.login_gagal), Toast.LENGTH_LONG)
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

                    val user = auth.currentUser

                    user?.let {

                        // Cek apakah pertama kali (belum ada di database)
                        db.collection("user_accounts").document(it.uid).get().addOnSuccessListener { document ->

                            // Sudah di database
                            if (document != null) {

                                accountSessionPreferences.isLogin = true
                                accountSessionPreferences.idUser = it.uid
                                accountSessionPreferences.emailUser = it.email.toString()
                                accountSessionPreferences.nameUser = it.displayName.toString()
                                accountSessionPreferences.phoneUser = document.data?.get("phone").toString()
                                accountSessionPreferences.addressUser = document.data?.get("address").toString()

                                // Show Toast
                                val message = "Login berhasil! Selamat datang di In-Apps"
                                Tools.showCustomToastSuccess(this, layoutInflater, resources, message)

                                startActivity(Intent(this, DashboardActivity::class.java))
                                finish()

                            } else {

                                val intentSignInAdvanced = Intent(this, SignInAdvancedActivity::class.java)
                                intentSignInAdvanced.putExtra("IdUser", it.uid)
                                intentSignInAdvanced.putExtra("EmailUser", it.email.toString())
                                intentSignInAdvanced.putExtra("NameUser", it.displayName.toString())

                                startActivity(intentSignInAdvanced)
                                finish()

                            }
                        }

                    }
                } else {

                    // Show Toast
                    val message = "Login gagal! Coba Lagi"
                    Tools.showCustomToastFailed(this, layoutInflater, resources, message)
                }
            }
    }
}