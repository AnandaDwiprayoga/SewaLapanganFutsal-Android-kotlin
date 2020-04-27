package org.z1god.selap

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import org.z1god.selap.Auth.LoginActivity
import org.z1god.selap.Menu.AccountFragment
import org.z1god.selap.Menu.FavFragment
import org.z1god.selap.Menu.HomeFragment
import org.z1god.selap.Utilitis.MyPreferences

class MainActivity : AppCompatActivity() {
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var mGoogleSiginClient: GoogleSignInClient
    private val TAG_HOME = "tag_home"
    private val TAG_FAV = "tag_fav"
    private val TAG_ACCOUNT = "tag_account"
    private lateinit var fontBold : Typeface
    private lateinit var fontRegular : Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyPreferences.init(this)

        if (!MyPreferences.getIsLogin()){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val fragmentManager = supportFragmentManager
        ResourcesCompat.getFont(this, R.font.montserrat)
        fontBold = ResourcesCompat.getFont(this, R.font.montserrat_bold)!!
        fontRegular = ResourcesCompat.getFont(this, R.font.montserrat)!!

        fragmentManager.beginTransaction().add(R.id.framelayout_menu, HomeFragment(),TAG_HOME).commit()
        label_menu_home.typeface = fontBold

        menu_home.setOnClickListener {
            val currentFragment =  fragmentManager.findFragmentById(R.id.framelayout_menu)
            if (currentFragment !is HomeFragment){
                resetActiveIconMenu(TAG_HOME)
                fragmentManager.beginTransaction().replace(R.id.framelayout_menu, HomeFragment(),TAG_HOME).commit()
            }
        }

        menu_fav.setOnClickListener {
            val currentFragment =  fragmentManager.findFragmentById(R.id.framelayout_menu)
            if (currentFragment !is FavFragment){
                resetActiveIconMenu(TAG_FAV)
                fragmentManager.beginTransaction().replace(R.id.framelayout_menu, FavFragment(),TAG_FAV).commit()
            }
        }

        menu_account.setOnClickListener {
            val currentFragment =  fragmentManager.findFragmentById(R.id.framelayout_menu)
            if (currentFragment !is AccountFragment){
                resetActiveIconMenu(TAG_ACCOUNT)
                fragmentManager.beginTransaction().replace(R.id.framelayout_menu, AccountFragment(),TAG_ACCOUNT).commit()
            }
        }

        getAccount()
//
//
//
//        firebaseAuth.signOut()
//        mGoogleSiginClient.signOut()
    }

    private fun getAccount() {

        val firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser
//        if user != null
        user?.let {
            MyPreferences.setNameAccount(it.displayName!!)
            MyPreferences.setImgAccount(it.photoUrl.toString()!!)
            MyPreferences.setEmailAccount(it.email!!)
        }
    }

    private fun resetActiveIconMenu(tag: String) {
        if (tag == TAG_HOME){
            //change icon
            menu_account.setImageResource(R.drawable.account_default)
            menu_fav.setImageResource(R.drawable.favorite_default)
            menu_home.setImageResource(R.drawable.home_selected)

            //change font
            label_menu_home.typeface = fontBold
            label_menu_account.typeface = fontRegular
            label_menu_fav.typeface = fontRegular
        }else if (tag == TAG_ACCOUNT){
            //change icon
            menu_account.setImageResource(R.drawable.account_selected)
            menu_fav.setImageResource(R.drawable.favorite_default)
            menu_home.setImageResource(R.drawable.home_default)

            //change font
            label_menu_home.typeface = fontRegular
            label_menu_account.typeface = fontBold
            label_menu_fav.typeface = fontRegular
        }else if (tag == TAG_FAV){
            //change icon
            menu_account.setImageResource(R.drawable.account_default)
            menu_fav.setImageResource(R.drawable.favorite_selected)
            menu_home.setImageResource(R.drawable.home_default)

            //change font
            label_menu_home.typeface = fontRegular
            label_menu_account.typeface = fontRegular
            label_menu_fav.typeface = fontBold
        }
    }
}
