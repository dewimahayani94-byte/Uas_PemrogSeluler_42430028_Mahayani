package com.example.uas_pemrogseluler_42430028_mahayani
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvName = findViewById<TextView>(R.id.tv_detail_name)
        val tvPrice = findViewById<TextView>(R.id.tv_detail_price)
        val btnBack = findViewById<ImageButton>(R.id.btn_back)

        val receivedName = intent.getStringExtra("EXTRA_NAME")
        val receivedPrice = intent.getStringExtra("EXTRA_PRICE")

        if (receivedName != null) {
            tvName.text = receivedName
        }
        if (receivedPrice != null) {
            tvPrice.text = receivedPrice
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}