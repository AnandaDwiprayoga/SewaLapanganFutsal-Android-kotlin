package org.z1god.selap.Utilitis

import java.text.NumberFormat
import java.util.*

class MyMoney {
    companion object {
        fun convertToRupiah(money : Int) : String {
            val localId = Locale("id","ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localId);

            return formatRupiah.format(money)
        }

        fun convertToNumber(value :String) : Int {
            val money = value.split("Rp")

//            //menghandle jika terdapat /jam maka yang diambil hanya index ke 0, dan /jam dihilangkan
//            val finalMoney = money[1].split("/jam")

            val moneyWithoutDot = money[1].replace(".","")
            return moneyWithoutDot.toInt()
        }
    }
}