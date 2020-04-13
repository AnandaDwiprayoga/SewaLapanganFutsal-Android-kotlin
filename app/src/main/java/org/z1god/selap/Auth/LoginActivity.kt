package org.z1god.selap.Auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import org.z1god.selap.MainActivity
import org.z1god.selap.Utilitis.MyInternet
import org.z1god.selap.Notification.ConnectionInfoFragment
import org.z1god.selap.R
import org.z1god.selap.Utilitis.MyPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSiginClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val RC_GMAIL = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        MyPreferences.init(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSiginClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        btn_gmail.setOnClickListener {
            if (MyInternet.checkNetworkInfo(this)) {
                val signInIntent = mGoogleSiginClient.signInIntent
                startActivityForResult(signInIntent, RC_GMAIL)
                loading_login.visibility = View.VISIBLE;
            } else {
                val connectionInfoFragment = ConnectionInfoFragment()
                connectionInfoFragment.show(supportFragmentManager, connectionInfoFragment.tag)
            }
        }

        btn_login.setOnClickListener {
            if (MyInternet.checkNetworkInfo(this)) {
                if(validateInput()){
                    loading_login.visibility = View.VISIBLE
                    login()
                }
            } else {
                val connectionInfoFragment = ConnectionInfoFragment()
                connectionInfoFragment.show(supportFragmentManager, connectionInfoFragment.tag)
            }
        }


        tv_daftar.setOnClickListener {
            startActivity(
                Intent(
                    LoginActivity@ this,
                    SignUpActivity::class.java
                )
            );
        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val user = auth.currentUser
                    if(user?.isEmailVerified!!){
                        MyPreferences.setIsLogin()
                        startActivity(Intent(LoginActivity@this, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Verifikasi email anda terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show()
                }
                loading_login.visibility = View.INVISIBLE
            }
    }

    private fun validateInput(): Boolean {
        return if(TextUtils.isEmpty(etEmail.text.toString().trim())){
            Toast.makeText(this, "Masukkan email terlebih dahulu", Toast.LENGTH_SHORT).show()
            false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()){
            Toast.makeText(this, "Masukkan email yang valid", Toast.LENGTH_SHORT).show()
            false
        }else if(TextUtils.isEmpty(etPassword.text.toString().trim()) || etPassword.text.length <   6){
            Toast.makeText(this, "Masukkan password anda minimal 6 karakter", Toast.LENGTH_SHORT).show()
            false
        }else{
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_GMAIL) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Snackbar.make(window.decorView.rootView, e.message!!, Snackbar.LENGTH_SHORT)
                        .show();
                    // ...
                }
            }
        }
        loading_login.visibility = View.INVISIBLE
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    MyPreferences.setIsLogin()
                    startActivity(Intent(LoginActivity@ this, MainActivity::class.java))
                    finish()
                }else{
                    Snackbar.make(window.decorView.rootView, "Login gagal" ,Snackbar.LENGTH_SHORT).show();
                }
            }
    }
}
