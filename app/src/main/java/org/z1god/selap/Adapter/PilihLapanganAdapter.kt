package org.z1god.selap.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import org.z1god.selap.PilihLapanganModel
import org.z1god.selap.R



class PilihLapanganAdapter(val listLapangan : ArrayList<PilihLapanganModel>) : RecyclerView.Adapter<PilihLapanganAdapter.MyViewHolder>() {

    var lastSelectedPosition = -1
    var onItemClick : ((PilihLapanganModel) -> Unit) ?= null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var radioButton: RadioButton = view.findViewById(R.id.rb_lapangan)

        init {
            radioButton.setOnClickListener {
                onItemClick?.invoke(listLapangan[adapterPosition])
                lastSelectedPosition = adapterPosition
                notifyDataSetChanged()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pilih_lapangan, parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = listLapangan.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pilihLapangan = listLapangan.get(position)

        if (pilihLapangan.isAvailable!!)
            holder.radioButton.text = pilihLapangan.name
        else
            holder.radioButton.text = "Full";

        holder.radioButton.isEnabled = pilihLapangan.isAvailable!!
        holder.radioButton.isChecked = lastSelectedPosition == position

        if (position == lastSelectedPosition){
            holder.radioButton.setTextColor(Color.WHITE)
        }else{
            holder.radioButton.setTextColor(Color.BLACK)
        }

    }


}