 package org.z1god.selap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.z1god.selap.OnBoarding.OnBoardingActivity

 class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            startActivity(Intent(MainActivity@this, OnBoardingActivity::class.java))
            finish()
        },2500);
    }
}
