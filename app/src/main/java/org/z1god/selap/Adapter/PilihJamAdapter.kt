package org.z1god.selap.Adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.z1god.selap.PilihJamModel
import org.z1god.selap.R
import java.util.*
import kotlin.collections.ArrayList


class PilihJamAdapter(private var listJam: ArrayList<PilihJamModel>, val context : Context) :
    RecyclerView.Adapter<PilihJamAdapter.MyViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var totalClick = 0
    private var isCurrentDate = false

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ceckbox: CheckBox = view.findViewById(R.id.cb_pilih_jam)

//        init {
//            ceckbox.setOnClickListener {
//                onItemClick?.invoke(totalClick)
//            }
//        }
    }

    fun setIsCurrentDate(isCurrentDate : Boolean) {
        this.isCurrentDate = isCurrentDate
    }

    fun changeJam(list : ArrayList<PilihJamModel>) {
        listJam.forEach {
            it.isCheked = false
        }
        totalClick = 0
        listJam = list
        notifyDataSetChanged()
    }

//    public fun getSelectedJam() : ArrayList<PilihJamModel> {
//        val selectedJam = ArrayList<PilihJamModel>()
//        listJam.forEach {
//            if (it.isCheked){
//                selectedJam.add(it)
//            }
//        }
//        return selectedJam
//    }

    public fun getSelectedJam() = listJam.filter {
        it.isCheked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pilih_jam, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = listJam.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val piliJam = listJam[position]

        //cek jika hari yang dipilih adalah hari ini, maka jam yang terlewati harus di disable
        if (isCurrentDate){
            val calender = Calendar.getInstance()
            val currentHour = calender.get(Calendar.HOUR_OF_DAY)

            //karena jam dimulai dari n-n+1, maka yang diambil n saja
            val listStartJam = piliJam.name.split("-")

            if(listStartJam[0].toInt() <= currentHour){
                piliJam.isAvailable = false
            }
        }


//        //in some cases, it will prevent unwanted situations
//        holder.ceckbox.setOnCheckedChangeListener(null)

        if (piliJam.isAvailable)
            holder.ceckbox.text = piliJam.name
        else
            holder.ceckbox.text = "Full"

        //memberi warna default hitam, karna terjadi perubahan sebeumnya
        holder.ceckbox.setTextColor(Color.BLACK)

        holder.ceckbox.isEnabled = piliJam.isAvailable
        holder.ceckbox.isChecked = piliJam.isCheked

        holder.ceckbox.setOnClickListener {
            val stateChekbox = holder.ceckbox.isChecked
            piliJam.isCheked = stateChekbox

            if (stateChekbox) {
                //2 + 1 = 3, karena dimulai dari 0 jadi harus dikurang 1 dari nilai aktualnya
                if (totalClick > 2){
                    Toast.makeText(context, "Anda hanya dapat memesan maksimal 3 jam", Toast.LENGTH_SHORT).show()
                    holder.ceckbox.isChecked = false
                    piliJam.isCheked = false
                }else {
                    totalClick++
                    holder.ceckbox.setTextColor(Color.WHITE)
                }
            } else {
                totalClick--
                holder.ceckbox.setTextColor(Color.BLACK)
            }
            onItemClick?.invoke(totalClick)
        }
//        holder.ceckbox.setOnCheckedChangeListener { _, isChecked ->
//            piliJam.isCheked = isChecked
//            if (isChecked) {
//                //2 + 1 = 3, karena dimulai dari 0 jadi harus dikurang 1 dari nilai aktualnya
//                if (totalClick > 2){
//                    Toast.makeText(context, "Anda hanya dapat memesan maksimal 3 jam", Toast.LENGTH_SHORT).show()
//                    holder.ceckbox.isChecked = false
//                    piliJam.isCheked = false
//                }else {
//                    totalClick++
//                    holder.ceckbox.setTextColor(Color.WHITE)
//                }
//            } else {
//                totalClick--
//                holder.ceckbox.setTextColor(Color.BLACK)
//            }
//        }

    }


}