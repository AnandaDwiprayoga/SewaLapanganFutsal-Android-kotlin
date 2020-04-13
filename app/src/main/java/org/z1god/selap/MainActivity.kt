package org.z1god.selap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.z1god.selap.Auth.LoginActivity
import org.z1god.selap.Utilitis.MyPreferences

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mGoogleSiginClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyPreferences.init(this)

        if (!MyPreferences.getIsLogin()){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSiginClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser
        //if user != null
        user?.let {
            tv_result.text = "${user.displayName}\n${user.email}\n${user.photoUrl}"
        }

        firebaseAuth.signOut()
        mGoogleSiginClient.signOut()
    }
}
