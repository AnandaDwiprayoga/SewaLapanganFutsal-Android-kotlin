package org.z1god.selap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_transfer.*

class TransferActivity : AppCompatActivity() {

    companion object {
        const val KEY_DP = "KEY_DP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        intent.getStringExtra(KEY_DP)?.let { tv_bayar_dp.text = it }

        object : CountDownTimer(1800000,1000){
            override fun onFinish() {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                val minute = (millisUntilFinished/ (1000*60))%60
                val second = (millisUntilFinished/1000)%60

                tv_timer.text = String.format("%02d Menit : %02d Detik", minute, second)
            }
        }.start()

        btn_go_home.setOnClickListener {
            finishAffinity()
            startActivity(Intent(TransferActivity@this, MainActivity::class.java))
        }
    }
}
