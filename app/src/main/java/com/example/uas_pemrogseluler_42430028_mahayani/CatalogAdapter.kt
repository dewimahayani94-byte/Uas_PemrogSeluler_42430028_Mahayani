package com.example.uas_pemrogseluler_42430028_mahayani

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CatalogAdapter(private var listSamsung: ArrayList<Samsung>) : RecyclerView.Adapter<CatalogAdapter.ListViewHolder>() {

    private val nimTag = "42430028"

    fun updateData(newList: ArrayList<Samsung>) {
        listSamsung = newList
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_product)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_samsung, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        try {
            val (name, price, specs, image) = listSamsung[position]

            holder.tvName.text = name
            holder.tvPrice.text = price

            if (image != 0) {
                holder.imgPhoto.setImageResource(image)
            }

            holder.itemView.setOnClickListener {
                Log.d(nimTag, "Item diklik: $name")
                val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)

                intentDetail.putExtra("EXTRA_NAME", name)
                intentDetail.putExtra("EXTRA_PRICE", price)
                intentDetail.putExtra("EXTRA_SPECS", specs)
                intentDetail.putExtra("EXTRA_IMAGE", image) // <-- INI YANG TADI TERLEWAT

                holder.itemView.context.startActivity(intentDetail)
            }
        } catch (e: Exception) {
            Log.e(nimTag, "Error bind view: ${e.message}")
        }
    }

    override fun getItemCount(): Int = listSamsung.size
}