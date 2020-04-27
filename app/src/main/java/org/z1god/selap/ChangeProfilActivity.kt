package org.z1god.selap

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_change_profil.*
import org.z1god.selap.Utilitis.MyPreferences

class ChangeProfilActivity : AppCompatActivity() {

    val RC_IMAGE_PICKER = 200
    var uriImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profil)

        MyPreferences.init(this)
        Picasso.get().load(MyPreferences.getImgAccount()).into(img_change_profil)
        tv_name_change.setText(MyPreferences.getNameAccount())

        btn_change_img.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, RC_IMAGE_PICKER)
        }


        btn_change_profil.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            //default img uri
            var imgUri = currentUser?.photoUrl

            uriImage?.let {

                //upload img uri to firebase
                loading_change_profil.visibility = View.VISIBLE

                val storageRefence = FirebaseStorage.getInstance().reference

                val ref = storageRefence.child("images/${currentUser?.uid}")
                ref.putFile(it).addOnSuccessListener {
                    loading_change_profil.visibility = View.INVISIBLE

                    ref.downloadUrl.addOnSuccessListener { uri ->
                        imgUri = uri

                        //bukan best practice karena dijalankan sesuai successnya foto atau tidak
                        val updatedUser = UserProfileChangeRequest.Builder()
                            .setDisplayName(tv_name_change.text.toString())
                            .setPhotoUri(imgUri)
                            .build()

                        currentUser?.updateProfile(updatedUser)
                            ?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    //mengupdate shared preferences
                                    MyPreferences.setImgAccount(imgUri.toString())
                                    MyPreferences.setNameAccount(tv_name_change.text.toString())

                                    Toast.makeText(this, "Berhasil terupdate", Toast.LENGTH_SHORT)
                                        .show()
                                    setResult(Activity.RESULT_OK)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Gagal terupdate", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }
                }
                    .addOnFailureListener {
                        loading_change_profil.visibility = View.INVISIBLE
                    }
            }
        }

        btn_back.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) return

        if (requestCode == RC_IMAGE_PICKER) {
            data?.let {
                uriImage = it.data

                Picasso.get().load(uriImage).into(img_change_profil)
            }
        }

    }
}
