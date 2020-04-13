package org.z1god.selap.OnBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_on_boarding.*
import org.z1god.selap.Auth.LoginActivity
import org.z1god.selap.MainActivity
import org.z1god.selap.R
import org.z1god.selap.Utilitis.MyPreferences

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        MyPreferences.init(this)

        if(MyPreferences.getIsFinishOnBoarding()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val listScreen :ArrayList<ScreenModel> = arrayListOf()

        listScreen.add(
            ScreenModel(
                R.drawable.board1,
                "Jadwal Up to Date",
                "jadwal yang ada di aplikasi real time dengan jadwal di lapangan"
            )
        )
        listScreen.add(
            ScreenModel(
                R.drawable.board2,
                "Pilih Sesukamu",
                "Tidak usah bingung, kamu dapat menemukan banyak pilihan lapangan"
            )
        )
        listScreen.add(
            ScreenModel(
                R.drawable.board3,
                "Bayar Sesukamu",
                "Kamu tidak perlu bayar full untuk pesan, bayar sesukamu dan lunasi setelah main"
            )
        )

        vp_onboarding.adapter =
            ScreenAdapter(this, listScreen)
        tl_indicator.setupWithViewPager(vp_onboarding)

        tl_indicator.addOnTabSelectedListener(object: TabLayout.BaseOnTabSelectedListener<TabLayout.Tab>{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == listScreen.size-1){
                    btn_onboarding.text = "Mulai"
                }else{
                    btn_onboarding.text = "Lanjut"
                }
            }

        })

        btn_onboarding.setOnClickListener {
            var currentPosition = vp_onboarding.currentItem

            if (btn_onboarding.text.toString().equals("lanjut",true)){
                currentPosition++
                vp_onboarding.currentItem = currentPosition
            }else if(btn_onboarding.text.toString().equals("mulai",true)){
                MyPreferences.setIsFinishOnBoarding()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
