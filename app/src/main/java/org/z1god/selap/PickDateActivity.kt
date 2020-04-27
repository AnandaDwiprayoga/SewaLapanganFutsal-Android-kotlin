package org.z1god.selap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_pick_date.*
import org.z1god.selap.Adapter.PilihJamAdapter
import org.z1god.selap.Adapter.PilihLapanganAdapter
import org.z1god.selap.Notification.CheckoutFragment
import org.z1god.selap.Notification.CheckoutFragment.Companion.KEY_ADDRESS_CHECKOUT
import org.z1god.selap.Notification.CheckoutFragment.Companion.KEY_DATE_CHECKOUT
import org.z1god.selap.Notification.CheckoutFragment.Companion.KEY_FUTSAL_CHECKOUT
import org.z1god.selap.Notification.CheckoutFragment.Companion.KEY_IMAGE_CHECKOUT
import org.z1god.selap.Notification.CheckoutFragment.Companion.KEY_JAM_CHECKOUT
import org.z1god.selap.Notification.CheckoutFragment.Companion.KEY_LOCATION_CHECKOUT
import org.z1god.selap.Notification.CheckoutFragment.Companion.KEY_TOTAL_CHECKOUT
import org.z1god.selap.Utilitis.DummyData
import org.z1god.selap.Utilitis.MyMoney
import org.z1god.selap.Utilitis.MyMoney.Companion.convertToNumber
import org.z1god.selap.Utilitis.MyMoney.Companion.convertToRupiah
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PickDateActivity : AppCompatActivity() {

    companion object {
        val KEY_TIME = "key_time"
        val KEY_DETAIL_LAPANGAN = "key_detail_lapangan"
    }

    private var pickLapangan = false
    private var pickTime = false

    private var lapanganChoosed : String? = null
    private var jamChoosed : List<PilihJamModel> ?= null

    lateinit var pilihJamAdapter: PilihJamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_date)

        val detailLapangan : ItemLapanganModel = intent.getParcelableExtra(KEY_DETAIL_LAPANGAN)

        val lapangan : ArrayList<PilihLapanganModel> = DummyData.getLapangan(detailLapangan.jumlahLap)

        val date = intent.getStringExtra(KEY_TIME)
        tv_tanggal.text = date

        pilihJamAdapter = PilihJamAdapter(ArrayList(), this)
        val pilihLapanganAdapter = PilihLapanganAdapter(lapangan)

        pilihLapanganAdapter.onItemClick = {
            //untuk menghandle ketika lapangan diganti akan mengembalikan semua pilihannya
            pickTime = false

            pickLapangan = true
            lapanganChoosed = it.name
            checkButtonChekout()

            //menampilkan jam sesuai dengan lapangan
            val namaLapangan = it.name?.split(" ")
            val index = namaLapangan!![1].toInt() - 1

            pilihJamAdapter.changeJam(DummyData.getJam(index)!!)
        }

        rv_pilih_lapangan.layoutManager = GridLayoutManager(this,2)
        rv_pilih_lapangan.adapter = pilihLapanganAdapter

        rv_pilih_jam.layoutManager = GridLayoutManager(this,4)
        rv_pilih_jam.adapter = pilihJamAdapter

        pilihJamAdapter.onItemClick = {
            tv_total_harga.text = convertToRupiah(it * convertToNumber(detailLapangan.harga.split("/jam")[0]))
            pickTime = it > 0
            checkButtonChekout()
        }
        if(currentDate()) pilihJamAdapter.setIsCurrentDate(true)


        btn_back.setOnClickListener { finish() }

        btn_checkout.setOnClickListener {
            jamChoosed = pilihJamAdapter.getSelectedJam()

            //untuk menyambungkan jam menjadi satu baris string
            var jam : String ?= ""
            for((index, value) in jamChoosed!!.withIndex()){
                jam += value.name
                if (index+1 != jamChoosed!!.size) jam += ", "
            }

            val bundle = Bundle()
            bundle.putInt(KEY_IMAGE_CHECKOUT, detailLapangan.image)
            bundle.putString(KEY_FUTSAL_CHECKOUT, detailLapangan.nama)
            bundle.putString(KEY_ADDRESS_CHECKOUT, detailLapangan.alamat)
            bundle.putString(KEY_LOCATION_CHECKOUT, lapanganChoosed)
            bundle.putString(KEY_JAM_CHECKOUT, jam)
            bundle.putString(KEY_DATE_CHECKOUT, date)
            bundle.putString(KEY_TOTAL_CHECKOUT, tv_total_harga.text.toString())

            val checkoutFragment = CheckoutFragment()
            checkoutFragment.arguments = bundle
            checkoutFragment.show(supportFragmentManager,checkoutFragment.tag)
        }

    }

    private fun currentDate(): Boolean {
        val date = intent.getStringExtra(KEY_TIME)
        date?.let {
            //get current date
            val sdf = SimpleDateFormat("EEEE, dd MMM")
            val currentDate = sdf.format(Date())

            if(it == currentDate) return true
        }
        return false
    }

    private fun checkButtonChekout() {
        if (pickLapangan && pickTime){
            btn_checkout.visibility = View.VISIBLE
        }else{
            btn_checkout.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        pilihJamAdapter.changeJam(ArrayList())
    }
}
