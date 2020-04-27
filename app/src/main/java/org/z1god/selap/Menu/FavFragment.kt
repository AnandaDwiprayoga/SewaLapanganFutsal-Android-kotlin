package org.z1god.selap.Menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_fav.*
import org.z1god.selap.Adapter.ItemFavoriteAdapter
import org.z1god.selap.DetailActivity
import org.z1god.selap.ItemLapanganModel

import org.z1god.selap.R

/**
 * A simple [Fragment] subclass.
 */
class FavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemsLapangan = ArrayList<ItemLapanganModel>()
        itemsLapangan.add(ItemLapanganModel(4f,"Futsal Sumeleh", "kecamatan kauman","Rp 100ribu/jam",R.drawable.unnamed,3))

        var adapter = ItemFavoriteAdapter(itemsLapangan)
        rv_fav.adapter = adapter

        rv_fav.layoutManager = LinearLayoutManager(activity)

        adapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.KEY_LAPANGAN, it)
            startActivity(intent)
        }

        refresh_fav.setOnRefreshListener {
            itemsLapangan.add(ItemLapanganModel(3f,"Futsal ngrendeng", "Kota Malang, sawo jajar","Rp 100ribu/jam",R.drawable.jaring_lapangan_futsal,4))
            itemsLapangan.add(ItemLapanganModel(4f,"Futsal Sumeleh", "kecamatan kauman","Rp 100ribu/jam",R.drawable.unnamed,5))
            itemsLapangan.add(ItemLapanganModel(3f,"Futsal ngrendeng", "Kota Malang, sawo jajar","Rp 100ribu/jam",R.drawable.jaring_lapangan_futsal,2))

            adapter = ItemFavoriteAdapter(itemsLapangan)
            rv_fav.adapter =
                ItemFavoriteAdapter(itemsLapangan)

            refresh_fav.isRefreshing = false
        }
    }
}
