package org.z1god.selap.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.z1god.selap.ItemLapanganModel
import org.z1god.selap.R

class ItemTerbaikAdapter(val listItems : ArrayList<ItemLapanganModel>) :
    RecyclerView.Adapter<ItemTerbaikAdapter.MyViewHolder>() {

    var onItemClick : ((ItemLapanganModel) -> Unit) ?= null

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)
        val tvHarga = view.findViewById<TextView>(R.id.tv_price)
        val image = view.findViewById<ImageView>(R.id.iv_terbaik)
        val rating = view.findViewById<RatingBar>(R.id.rating)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(listItems[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_terbaik, parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listItems.get(position)

        holder.tvName.text = item.nama
        holder.tvAddress.text = item.alamat
        holder.tvHarga.text = item.harga
        holder.rating.rating = item.rating
        holder.image.setImageResource(item.image)
    }
}