package org.z1god.selap.Utilitis

import org.z1god.selap.PilihJamModel
import org.z1god.selap.PilihLapanganModel

class DummyData {
    companion object {
        lateinit var listTempat : ArrayList<ArrayList<PilihLapanganModel>>
        lateinit var listJam : ArrayList<ArrayList<PilihJamModel>>

        init {
            val lapangan1 = ArrayList<PilihLapanganModel>()
            lapangan1.add(PilihLapanganModel(true, "Lapangan 1"))
            lapangan1.add(PilihLapanganModel(true, "Lapangan 2"))
            lapangan1.add(PilihLapanganModel(true, "Lapangan 3"))
            lapangan1.add(PilihLapanganModel(false, "Lapangan 4"))

            val lapangan2 = ArrayList<PilihLapanganModel>()
            lapangan2.add(PilihLapanganModel(true, "Lapangan 1"))
            lapangan2.add(PilihLapanganModel(false, "Lapangan 2"))
            lapangan2.add(PilihLapanganModel(true, "Lapangan 3"))

            val lapangan3 = ArrayList<PilihLapanganModel>()
            lapangan3.add(PilihLapanganModel(false, "Lapangan 1"))
            lapangan3.add(PilihLapanganModel(false, "Lapangan 2"))
            lapangan3.add(PilihLapanganModel(true, "Lapangan 3"))
            lapangan3.add(PilihLapanganModel(false, "Lapangan 4"))
            lapangan3.add(PilihLapanganModel(true, "Lapangan 5"))

            val lapangan4 = ArrayList<PilihLapanganModel>()
            lapangan4.add(PilihLapanganModel(false, "Lapangan 1"))
            lapangan4.add(PilihLapanganModel(true, "Lapangan 2"))

            listTempat = ArrayList()
            listTempat.add(lapangan1)
            listTempat.add(lapangan2)
            listTempat.add(lapangan3)
            listTempat.add(lapangan4)


            //untuk dummy jam
            val jam1 = ArrayList<PilihJamModel>()
            jam1.add(PilihJamModel("9-10",false,true))
            jam1.add(PilihJamModel("10-11",false,false))
            jam1.add(PilihJamModel("11-12",false,true))
            jam1.add(PilihJamModel("12-13",false,false))
            jam1.add(PilihJamModel("13-14",false,true))
            jam1.add(PilihJamModel("14-15",false,true))
            jam1.add(PilihJamModel("15-16",false,false))
            jam1.add(PilihJamModel("16-17",false,true))
            jam1.add(PilihJamModel("17-18",false,false))
            jam1.add(PilihJamModel("18-19",false,true))
            jam1.add(PilihJamModel("19-20",false,true))
            jam1.add(PilihJamModel("20-21",false,true))

            val jam2 = ArrayList<PilihJamModel>()
            jam2.add(PilihJamModel("9-10",false,true))
            jam2.add(PilihJamModel("10-11",false,false))
            jam2.add(PilihJamModel("11-12",false,true))
            jam2.add(PilihJamModel("12-13",false,false))
            jam2.add(PilihJamModel("13-14",false,false))
            jam2.add(PilihJamModel("14-15",false,true))
            jam2.add(PilihJamModel("15-16",false,false))
            jam2.add(PilihJamModel("16-17",false,true))
            jam2.add(PilihJamModel("17-18",false,true))
            jam2.add(PilihJamModel("18-19",false,true))
            jam2.add(PilihJamModel("19-20",false,false))
            jam2.add(PilihJamModel("20-21",false,false))

            val jam3 = ArrayList<PilihJamModel>()
            jam3.add(PilihJamModel("9-10",false,false))
            jam3.add(PilihJamModel("10-11",false,false))
            jam3.add(PilihJamModel("11-12",false,false))
            jam3.add(PilihJamModel("12-13",false,false))
            jam3.add(PilihJamModel("13-14",false,false))
            jam3.add(PilihJamModel("14-15",false,true))
            jam3.add(PilihJamModel("15-16",false,true))
            jam3.add(PilihJamModel("16-17",false,true))
            jam3.add(PilihJamModel("17-18",false,false))
            jam3.add(PilihJamModel("18-19",false,false))
            jam3.add(PilihJamModel("19-20",false,true))
            jam3.add(PilihJamModel("20-21",false,true))

            val jam4 = ArrayList<PilihJamModel>()
            jam4.add(PilihJamModel("9-10",false,true))
            jam4.add(PilihJamModel("10-11",false,true))
            jam4.add(PilihJamModel("11-12",false,true))
            jam4.add(PilihJamModel("12-13",false,true))
            jam4.add(PilihJamModel("13-14",false,false))
            jam4.add(PilihJamModel("14-15",false,false))
            jam4.add(PilihJamModel("15-16",false,false))
            jam4.add(PilihJamModel("16-17",false,true))
            jam4.add(PilihJamModel("17-18",false,true))
            jam4.add(PilihJamModel("18-19",false,true))
            jam4.add(PilihJamModel("19-20",false,false))
            jam4.add(PilihJamModel("20-21",false,false))

            val jam5 = ArrayList<PilihJamModel>()
            jam5.add(PilihJamModel("9-10",false,false))
            jam5.add(PilihJamModel("10-11",false,false))
            jam5.add(PilihJamModel("11-12",false,true))
            jam5.add(PilihJamModel("12-13",false,false))
            jam5.add(PilihJamModel("13-14",false,true))
            jam5.add(PilihJamModel("14-15",false,true))
            jam5.add(PilihJamModel("15-16",false,true))
            jam5.add(PilihJamModel("16-17",false,true))
            jam5.add(PilihJamModel("17-18",false,true))
            jam5.add(PilihJamModel("18-19",false,false))
            jam5.add(PilihJamModel("19-20",false,true))
            jam5.add(PilihJamModel("20-21",false,true))

            listJam = ArrayList()
            listJam.add(jam1)
            listJam.add(jam2)
            listJam.add(jam3)
            listJam.add(jam4)
            listJam.add(jam5)
        }
        fun getLapangan(jumlah :Int) : ArrayList<PilihLapanganModel> {
            listTempat?.forEach {
                if (it.size == jumlah) return it
            }
            return ArrayList()
        }

        fun getJam(position : Int) = listJam?.get(position)
    }
}