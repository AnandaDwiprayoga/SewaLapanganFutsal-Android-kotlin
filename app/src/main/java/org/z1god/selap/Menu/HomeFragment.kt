package org.z1god.selap.Menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import org.z1god.selap.*
import org.z1god.selap.Adapter.ItemTerbaikAdapter
import org.z1god.selap.Adapter.ItemTerdekatAdapter
import org.z1god.selap.DetailActivity.Companion.KEY_LAPANGAN
import org.z1god.selap.R
import org.z1god.selap.Utilitis.MyPreferences
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val PERMISSION_ID = 120
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        getLastLocation()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_search.setImeActionLabel("Cari", KeyEvent.KEYCODE_ENTER)
        et_search.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER)
                Toast.makeText(context, "Search ok", Toast.LENGTH_SHORT).show()
            false
        }

        val itemsLapangan = ArrayList<ItemLapanganModel>()
        itemsLapangan.add(ItemLapanganModel(4f,"Futsal Sumeleh", "kecamatan kauman","Rp100.000/jam",R.drawable.unnamed,3))
        itemsLapangan.add(ItemLapanganModel(3f,"Futsal ngrendeng", "Kota Malang, sawo jajar","Rp100.000/jam",R.drawable.jaring_lapangan_futsal,4))
        itemsLapangan.add(ItemLapanganModel(4f,"Futsal Sumeleh", "kecamatan kauman","Rp100.000/jam",R.drawable.unnamed,5))
        itemsLapangan.add(ItemLapanganModel(3f,"Futsal ngrendeng", "Kota Malang, sawo jajar","Rp100.000/jam",R.drawable.jaring_lapangan_futsal,2))

        MyPreferences.init(context!!)
        Picasso.get().load(MyPreferences.getImgAccount()).into(img_profil)
        //jadi hanya memakai nama depannya saja
        val namaLengkap = MyPreferences.getNameAccount()?.split(" ")
        val namaDepan = namaLengkap!![0]
        tv_name_profil.text = "Hai, ${namaDepan}"

        val adapterTerdekat =
            ItemTerdekatAdapter(itemsLapangan)
        val adapterTerbaik =
            ItemTerbaikAdapter(itemsLapangan)

        rv_terdekat.adapter = adapterTerdekat
        rv_terdekat.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        rv_terbaik.adapter = adapterTerbaik
        rv_terbaik.layoutManager = LinearLayoutManager(activity)

        adapterTerbaik.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(KEY_LAPANGAN, it)
            startActivity(intent)
        }

        adapterTerdekat.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(KEY_LAPANGAN, it)
            startActivity(intent)
        }
    }

    private fun checkPermissions() : Boolean {
        if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED )
            return true
        return false
    }

    private fun requestPermissions() {
        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLastLocation(){
        if (checkPermissions()){
            if (isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener {
                    if (it.result == null){
                        requestNewLocationData()
                    }else{
                        val latitude = it.result!!.latitude
                        val longitude = it.result!!.longitude
                        updateLocation(latitude,longitude)
                    }
                }
            }else{
                Toast.makeText(context, "Hidupkan Lokasi anda", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermissions()
        }
    }

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            val latitude = mLastLocation.latitude
            val longitude = mLastLocation.longitude
            updateLocation(latitude,longitude)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }

        }
    }

    private fun updateLocation(latitude : Double, longitude : Double){
        val geo = Geocoder(context, Locale.getDefault())
        val address : List<Address> = geo.getFromLocation(latitude,longitude,1)
        if (address.isEmpty()){
            tv_address_profil.text = "Sedang mencari lokasi"
        }else{
            if (address.size > 0){
                tv_address_profil.text = "${address[0].thoroughfare}, ${address[0].locality}, ${address[0].adminArea}"
            }
        }
    }


//    nice tips location from https://www.androdocs.com/kotlin/getting-current-location-latitude-longitude-in-android-using-kotlin.html
}
