package com.example.uas_pemrogseluler_42430028_mahayani

import android.os.Bundle
import android.util.Log
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
    private var currentList = ArrayList<Samsung>()
    private lateinit var adapter: CatalogAdapter
    private val nimTag = "42430028"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(nimTag, "Aplikasi dibuka, memuat MainActivity")

        rvCatalog = findViewById<RecyclerView>(R.id.rv_samsung_catalog) ?: findViewById(R.id.rv_samsung_catalog_land)
        val etSearch = findViewById<EditText>(R.id.et_search) ?: findViewById(R.id.et_search_land)
        val btnSortAz = findViewById<Button>(R.id.btn_sort_az)
        val btnSortZa = findViewById<Button>(R.id.btn_sort_za)
        val rgSort = findViewById<RadioGroup>(R.id.rg_sort)

        try {
            list.addAll(getListSamsung())
            currentList.addAll(list)
            Log.d(nimTag, "Data array berhasil dimuat: ${list.size} item")
        } catch (e: Exception) {
            Log.e(nimTag, "Error memuat data: ${e.message}")
            Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show()
        }

        adapter = CatalogAdapter(currentList)
        rvCatalog.adapter = adapter

        if (findViewById<RecyclerView>(R.id.rv_samsung_catalog) != null) {
            rvCatalog.layoutManager = LinearLayoutManager(this)
        } else {
            rvCatalog.layoutManager = GridLayoutManager(this, 2)
        }

        etSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val keyword = etSearch.text.toString().trim()
                if (keyword.isEmpty()) {
                    etSearch.error = "Masukkan seri HP!"
                    adapter.updateData(list)
                    Log.d(nimTag, "Pencarian kosong, reset daftar")
                } else {
                    performLinearSearch(keyword)
                }
                true
            } else false
        }

        btnSortAz?.setOnClickListener { performBubbleSort(true) }
        btnSortZa?.setOnClickListener { performBubbleSort(false) }

        rgSort?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_sort_asc) performBubbleSort(true)
            if (checkedId == R.id.rb_sort_desc) performBubbleSort(false)
        }
    }

    private fun performLinearSearch(query: String) {
        Log.d(nimTag, "Memulai Linear Search keyword: $query")
        try {
            val searchResult = ArrayList<Samsung>()
            for (hp in list) {
                if (hp.name.contains(query, ignoreCase = true)) {
                    searchResult.add(hp)
                }
            }

            if (searchResult.isEmpty()) {
                Toast.makeText(this, "HP tidak ditemukan", Toast.LENGTH_SHORT).show()
                Log.d(nimTag, "Hasil pencarian kosong")
            } else {
                Log.d(nimTag, "Ditemukan ${searchResult.size} hasil")
            }

            currentList = searchResult
            adapter.updateData(currentList)
        } catch (e: Exception) {
            Log.e(nimTag, "Error pencarian: ${e.message}")
        }
    }

    private fun performBubbleSort(isAscending: Boolean) {
        Log.d(nimTag, "Memulai Bubble Sort Ascending: $isAscending")
        try {
            val n = currentList.size
            for (i in 0 until n - 1) {
                for (j in 0 until n - i - 1) {
                    val condition = if (isAscending) {
                        currentList[j].name > currentList[j + 1].name
                    } else {
                        currentList[j].name < currentList[j + 1].name
                    }

                    if (condition) {
                        val temp = currentList[j]
                        currentList[j] = currentList[j + 1]
                        currentList[j + 1] = temp
                    }
                }
            }
            adapter.updateData(currentList)
            Toast.makeText(this, "Katalog diurutkan!", Toast.LENGTH_SHORT).show()
            Log.d(nimTag, "Bubble Sort selesai")
        } catch (e: Exception) {
            Log.e(nimTag, "Error sorting: ${e.message}")
        }
    }

    private fun getListSamsung(): ArrayList<Samsung> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val dataSpecs = resources.getStringArray(R.array.data_specs)

        val listHp = ArrayList<Samsung>()
        for (i in dataName.indices) {
            val hp = Samsung(dataName[i], dataPrice[i], dataSpecs[i], 0)
            listHp.add(hp)
        }
        return listHp
    }
}