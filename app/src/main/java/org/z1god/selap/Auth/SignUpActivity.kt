package org.z1god.selap.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.z1god.selap.Utilitis.MyInternet
import org.z1god.selap.Notification.ConnectionInfoFragment
import org.z1god.selap.Notification.VerificationFragment
import org.z1god.selap.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()

        btn_daftar.setOnClickListener {
            if (MyInternet.checkNetworkInfo(this)){
                if(validateInput()){
                    btn_daftar.isEnabled = false
                    loading_signup.visibility = View.VISIBLE
                    signUp()
                }
            }else{
                val connectionInfoFragment = ConnectionInfoFragment()
                connectionInfoFragment.show(supportFragmentManager, connectionInfoFragment.tag)
            }

//            val verficationFragment =
//                VerificationFragment()
//            verficationFragment.show(supportFragmentManager, verficationFragment.tag)
        }
    }

    private fun signUp() {
        firebaseAuth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val user = firebaseAuth.currentUser

                    //add name to firebase
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(etNama.text.toString())
                        .build()

                    user?.updateProfile(profileUpdates)
                    //end add name to firebase

                    //send email verification
                    user?.sendEmailVerification()?.addOnCompleteListener {task ->
                        if (task.isSuccessful){
                            val verficationFragment = VerificationFragment()
                            verficationFragment.show(supportFragmentManager, verficationFragment.tag)
                        }else{
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        loading_signup.visibility = View.INVISIBLE
    }

    private fun validateInput(): Boolean {
        return if(TextUtils.isEmpty(etNama.text.toString().trim())){
            Toast.makeText(this, "Masukkan nama terlebih dahulu", Toast.LENGTH_SHORT).show();
            false
        }else if(TextUtils.isEmpty(etEmail.text.toString().trim())){
            Toast.makeText(this, "Masukkan email terlebih dahulu", Toast.LENGTH_SHORT).show();
            false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()){
            Toast.makeText(this, "Masukkan email yang valid", Toast.LENGTH_SHORT).show();
            false
        }else if(TextUtils.isEmpty(etPassword.text.toString().trim()) || TextUtils.isEmpty(etPasswordAgain.text.toString().trim()) || etPassword.text.length <   6){
            Toast.makeText(this, "Masukkan password anda minimal 6 karakter", Toast.LENGTH_SHORT).show();
            false
        }else if(!TextUtils.equals(etPassword.text.toString().trim(), etPasswordAgain.text.toString().trim())){
            Toast.makeText(this, "Masukkan password yang sama", Toast.LENGTH_SHORT).show();
            false
        }else{
            true
        }
    }


    fun back(view: View) {
        finish()
    }


}
