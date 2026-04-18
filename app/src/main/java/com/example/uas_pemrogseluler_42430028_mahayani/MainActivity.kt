package com.example.uas_pemrogseluler_42430028_mahayani

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch = findViewById<EditText>(R.id.et_search)

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {

                val keyword = etSearch.text.toString().trim()

                if (keyword.isEmpty()) {
                    etSearch.error = "Nama seri Samsung tidak boleh kosong!"
                    Toast.makeText(this, "Silakan masukkan tipe HP yang dicari", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Mencari seri: $keyword...", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }

        val layoutHeader = findViewById<LinearLayout>(R.id.layout_header)

        layoutHeader.setOnClickListener {
            val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)

            moveIntent.putExtra("EXTRA_NAME", "Samsung Galaxy A55 5G")
            moveIntent.putExtra("EXTRA_PRICE", "Rp 5.999.000")

            startActivity(moveIntent)
        }
    }
}