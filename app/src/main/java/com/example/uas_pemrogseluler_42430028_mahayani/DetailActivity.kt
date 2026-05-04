package com.example.uas_pemrogseluler_42430028_mahayani

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private val nimTag = "42430028"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvName = findViewById<TextView>(R.id.tv_detail_name)
        val tvPrice = findViewById<TextView>(R.id.tv_detail_price)
        val tvSpecs = findViewById<TextView>(R.id.tv_detail_specs)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        try {
            val receivedName = intent.getStringExtra("EXTRA_NAME")
            val receivedPrice = intent.getStringExtra("EXTRA_PRICE")
            val receivedSpecs = intent.getStringExtra("EXTRA_SPECS")

            if (receivedName != null) tvName.text = receivedName
            if (receivedPrice != null) tvPrice.text = receivedPrice
            if (receivedSpecs != null) tvSpecs.text = receivedSpecs

            Log.d(nimTag, "Membuka detail untuk: $receivedName")
        } catch (e: Exception) {
            Log.e(nimTag, "Error memuat detail: ${e.message}")
        }

        btnBack.setOnClickListener {
            Log.d(nimTag, "Menutup DetailActivity")
            finish()
        }
    }
}