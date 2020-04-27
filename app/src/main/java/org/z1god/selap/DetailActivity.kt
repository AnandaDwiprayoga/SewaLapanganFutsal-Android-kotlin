package org.z1god.selap

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import org.z1god.selap.PickDateActivity.Companion.KEY_DETAIL_LAPANGAN
import org.z1god.selap.PickDateActivity.Companion.KEY_TIME
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {
    companion object {
        public val KEY_LAPANGAN = "key_lapangan"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val lapangan = intent.getParcelableExtra<ItemLapanganModel>(KEY_LAPANGAN)

        lapangan?.let {
            iv_detail.setImageResource(lapangan.image)
            tv_name_detail.text = lapangan.nama
            rating_detail.rating = lapangan.rating
            tv_address_detail.text = lapangan.alamat
            tv_price_detail.text = lapangan.harga
            tv_total_lapangan.text = lapangan.jumlahLap.toString()
        }

        btn_back.setOnClickListener { finish() }
        btn_cek_tanggal.setOnClickListener {
            val calender = Calendar.getInstance()
            val year = calender.get(Calendar.YEAR)
            val month = calender.get(Calendar.MONTH)
            val day = calender.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this, R.style.AppTheme_CustomDialogTheme,
                OnDateSetListener { datePicker, year, month, day -> }, year, month, day
            )

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            //digit terakihir mempresentasikan harinya
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)

            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "Pilih",DialogInterface.OnClickListener { dialog, which ->
                    if (which == DialogInterface.BUTTON_POSITIVE){
                       val datepicker = datePickerDialog.datePicker

                        val day = datepicker.dayOfMonth
                        val month = datepicker.month
                        val year = datepicker.year

                        val calender = Calendar.getInstance()
                        calender.set(year,month,day)

                        val simpleDateFormat = SimpleDateFormat("EEEE, dd MMM")
                        val formatDate = simpleDateFormat.format(calender.time)

                        val intent = Intent(this, PickDateActivity::class.java)
                        intent.putExtra(KEY_TIME, formatDate)
                        intent.putExtra(KEY_DETAIL_LAPANGAN, lapangan)
                        startActivity(intent)
                    }
                })

            datePickerDialog.show()

        }

        btn_back.setOnClickListener { finish() }

    }
}
