package com.incorps.inapps

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.preferences.AccountSessionPreferences
import com.incorps.inapps.utils.Tools

class KritikSaranActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var editKritikSaran: TextInputEditText
    private lateinit var btnSubmit: MaterialButton

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder

    private lateinit var accountSessionPreferences: AccountSessionPreferences
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kritik_saran)

        btnBack = findViewById(R.id.img_back_button)
        editKritikSaran = findViewById(R.id.edittext_kritik_saran)
        btnSubmit = findViewById(R.id.btn_submit)

        btnBack.setOnClickListener {
            finish()
        }

        accountSessionPreferences = AccountSessionPreferences(this)

        db = Firebase.firestore

        btnSubmit.setOnClickListener {
            if (editKritikSaran.text.toString().trim() == "") {
                editKritikSaran.error = "Isi kolom kritik dan saran"
            } else {
                //set alert dialog builder
                builder = AlertDialog.Builder(this)

                //set title for alert dialog
                builder.setTitle("Submit Kritik dan Saran")

                //set message for alert dialog
                builder.setMessage("Apakah anda yakin untuk submit kritik dan saran?")
                builder.setIcon(R.drawable.ic_baseline_warning_24)

                //set positive and negative button
                builder.apply {
                    setPositiveButton("Yes") { dialogInterface, i ->

                        val dataKritikSaran = hashMapOf(
                            "id_user" to accountSessionPreferences.idUser,
                            "kritiksaran" to editKritikSaran.text.toString().trim(),
                            "date" to System.currentTimeMillis()
                        )

                        db.collection("kritik_saran")
                            .add(dataKritikSaran)
                            .addOnSuccessListener {
                                Tools.showCustomToastSuccess(context, layoutInflater, resources, "Kritik dan Saran berhasil ditambahkan!")
                                finish()
                            }
                    }
                    setNegativeButton("No") { dialogInterface, i ->

                    }
                }

                // Create the AlertDialog
                alertDialog = builder.create()
                alertDialog.show()

            }
        }
    }
}