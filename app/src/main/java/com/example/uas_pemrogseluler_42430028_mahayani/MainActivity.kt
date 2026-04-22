package com.example.uas_pemrogseluler_42430028_mahayani

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvCatalog: RecyclerView
    private val list = ArrayList<Samsung>()
    private var currentList = ArrayList<Samsung>() // Menyimpan state data saat ini
    private lateinit var adapter: CatalogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi UI (Mendeteksi Portrait atau Landscape)
        rvCatalog = findViewById<RecyclerView>(R.id.rv_samsung_catalog) ?: findViewById(R.id.rv_samsung_catalog_land)
        val etSearch = findViewById<EditText>(R.id.et_search) ?: findViewById(R.id.et_search_land)

        // Kontrol Sortir
        val btnSortAz = findViewById<Button>(R.id.btn_sort_az)
        val btnSortZa = findViewById<Button>(R.id.btn_sort_za)
        val rgSort = findViewById<RadioGroup>(R.id.rg_sort)

        // 2. Setup Data & RecyclerView
        list.addAll(getListSamsung())
        currentList.addAll(list)

        adapter = CatalogAdapter(currentList)
        rvCatalog.adapter = adapter

        // Atur layout manager: Linear jika portrait, Grid(2) jika landscape
        if (findViewById<RecyclerView>(R.id.rv_samsung_catalog) != null) {
            rvCatalog.layoutManager = LinearLayoutManager(this)
        } else {
            rvCatalog.layoutManager = GridLayoutManager(this, 2)
        }

        // 3. LOGIKA LINEAR SEARCH (Modul 5 & 6)
        etSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val keyword = etSearch.text.toString().trim()
                if (keyword.isEmpty()) {
                    etSearch.error = "Masukkan seri HP!"
                    adapter.updateData(list) // Kembalikan ke daftar asli
                } else {
                    performLinearSearch(keyword)
                }
                true
            } else false
        }

        // 4. LOGIKA BUBBLE SORT (Modul 7)
        // Jika mode Portrait (Tombol)
        btnSortAz?.setOnClickListener { performBubbleSort(true) }
        btnSortZa?.setOnClickListener { performBubbleSort(false) }

        // Jika mode Landscape (RadioGroup)
        rgSort?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_sort_asc) performBubbleSort(true)
            if (checkedId == R.id.rb_sort_desc) performBubbleSort(false)
        }
    }

    // --- FUNGSI ALGORITMA: PENCARIAN LINEAR ---
    private fun performLinearSearch(query: String) {
        val searchResult = ArrayList<Samsung>()
        // Looping Linear Search: Mencari satu per satu di dalam Array
        for (hp in list) {
            if (hp.name.contains(query, ignoreCase = true)) {
                searchResult.add(hp)
            }
        }

        if (searchResult.isEmpty()) {
            Toast.makeText(this, "HP tidak ditemukan", Toast.LENGTH_SHORT).show()
        }

        currentList = searchResult
        adapter.updateData(currentList)
    }

    // --- FUNGSI ALGORITMA: BUBBLE SORT ---
    private fun performBubbleSort(isAscending: Boolean) {
        val n = currentList.size
        // Looping ganda khas Bubble Sort
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                // Menentukan kondisi tukar (A-Z atau Z-A)
                val condition = if (isAscending) {
                    currentList[j].name > currentList[j + 1].name
                } else {
                    currentList[j].name < currentList[j + 1].name
                }

                // Proses pertukaran data (Swapping)
                if (condition) {
                    val temp = currentList[j]
                    currentList[j] = currentList[j + 1]
                    currentList[j + 1] = temp
                }
            }
        }
        adapter.updateData(currentList)
        Toast.makeText(this, "Katalog diurutkan!", Toast.LENGTH_SHORT).show()
    }

    // --- SUMBER DATA ARRAY ---
    private fun getListSamsung(): ArrayList<Samsung> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val dataSpecs = resources.getStringArray(R.array.data_specs)

        val listHp = ArrayList<Samsung>()
        for (i in dataName.indices) {
            // Memasukkan data mentah ke dalam objek
            val hp = Samsung(
                dataName[i],
                dataPrice[i],
                dataSpecs[i],
                0 // Gambar kita biarkan 0 dulu, akan ditarik dari layout XML
            )
            listHp.add(hp)
        }
        return listHp
    }
}