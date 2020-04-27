package org.z1god.selap.Menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_account.*
import org.z1god.selap.Auth.LoginActivity
import org.z1god.selap.ChangePasswordActivity
import org.z1god.selap.ChangeProfilActivity
import org.z1god.selap.Notification.VerificationFragment
import org.z1god.selap.Notification.VerificationFragment.Companion.KEY_MESSAGE

import org.z1god.selap.R
import org.z1god.selap.Utilitis.MyPreferences

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    val RC_CHANGE_PROFIL = 201;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        MyPreferences.init(context!!)
        Picasso.get().load(MyPreferences.getImgAccount()).into(img_profil_account)
        tv_name_account.text = MyPreferences.getNameAccount()
        tv_email_account.text = MyPreferences.getEmailAccount()

        tv_ubah_profil.setOnClickListener {
            startActivityForResult(Intent(context, ChangeProfilActivity::class.java), RC_CHANGE_PROFIL)
        }

        tv_ubah_password.setOnClickListener {
            val firebase = FirebaseAuth.getInstance()
            val currentUser = firebase.currentUser


            firebase.sendPasswordResetEmail(currentUser?.email!!).addOnCompleteListener {
                if (it.isSuccessful){
                    val bundle  = Bundle()
                    bundle.putString(KEY_MESSAGE, "Check email anda untuk mengubah password")

                    val sendEmail = VerificationFragment()
                    sendEmail.arguments = bundle
                    sendEmail.show(childFragmentManager, sendEmail.tag)
                }else{
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        btn_ganti_akun.setOnClickListener {
            activity?.finishAffinity()

            val firebaseAuth = FirebaseAuth.getInstance()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val mGoogleSiginClient = GoogleSignIn.getClient(context!!, gso)

            firebaseAuth.signOut()
            mGoogleSiginClient.signOut()

            startActivity(Intent(context, LoginActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_CHANGE_PROFIL){
            Picasso.get().load(MyPreferences.getImgAccount()).into(img_profil_account)
            tv_name_account.text = MyPreferences.getNameAccount()
        }
    }

}
