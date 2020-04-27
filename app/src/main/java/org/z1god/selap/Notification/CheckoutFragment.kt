package org.z1god.selap.Notification

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_checkout.*

import org.z1god.selap.R
import org.z1god.selap.TransferActivity
import org.z1god.selap.TransferActivity.Companion.KEY_DP
import org.z1god.selap.Utilitis.MyMoney
import org.z1god.selap.Utilitis.MyMoney.Companion.convertToNumber
import org.z1god.selap.Utilitis.MyMoney.Companion.convertToRupiah
import java.text.NumberFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CheckoutFragment : BottomSheetDialogFragment() {

    companion object {
        val KEY_IMAGE_CHECKOUT = "KEY_IMAGE_CHECKOUT"
        val KEY_FUTSAL_CHECKOUT = "KEY_FUTSAL_CHECKOUT"
        val KEY_ADDRESS_CHECKOUT = "KEY_ADDRESS_CHECKOUT"
        val KEY_DATE_CHECKOUT = "KEY_DATE_CHECKOUT"
        val KEY_LOCATION_CHECKOUT = "KEY_LOCATION_CHECKOUT"
        val KEY_JAM_CHECKOUT = "KEY_JAM_CHECKOUT"
        val KEY_TOTAL_CHECKOUT = "KEY_TOTAL_CHECKOUT"
    }
    private var batasDp : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            iv_checkout.setImageResource(it.getInt(KEY_IMAGE_CHECKOUT))
            tv_futsal_checkout.text = it.getString(KEY_FUTSAL_CHECKOUT)
            tv_address_checkout.text = it.getString(KEY_ADDRESS_CHECKOUT)
            tv_location_checkout.text = it.getString(KEY_LOCATION_CHECKOUT)
            tv_date_checkout.text = it.getString(KEY_DATE_CHECKOUT)
            tv_jam_checkout.text = it.getString(KEY_JAM_CHECKOUT)
            tv_total_checkout.text = it.getString(KEY_TOTAL_CHECKOUT)

            batasDp = 0.2 * convertToNumber(it.getString(KEY_TOTAL_CHECKOUT)!!)
        }

        tv_dp.text = convertToRupiah(batasDp.toInt())

        btn_plus_dp.setOnClickListener {
            if (convertToNumber(tv_dp.text.toString()) + 10000 <= convertToNumber(tv_total_checkout.text.toString()) ) {
                tv_dp.text = convertToRupiah(convertToNumber(tv_dp.text.toString()) + 10000)
            }else{
                Toast.makeText(context, "Anda sudah mencapai batas maksimal dp" , Toast.LENGTH_SHORT).show()
            }
        }

        btn_minus_dp.setOnClickListener {
            if (convertToNumber(tv_dp.text.toString()) - 10000 >= batasDp ) {
                tv_dp.text = convertToRupiah(convertToNumber(tv_dp.text.toString()) - 10000)
            }else{
                Toast.makeText(context, "Anda sudah mencapai batas minimal dp" , Toast.LENGTH_SHORT).show()
            }
        }

        btn_bayar.setOnClickListener {
            val intent = Intent(context,TransferActivity::class.java)
            intent.putExtra(KEY_DP,tv_dp.text.toString())
            startActivity(intent)
            activity?.finish()
        }
    }

}
