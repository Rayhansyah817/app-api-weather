package com.main.appweather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.main.appweather.R
import com.main.appweather.source.weather.ForeCast

class ListForeCastAdapter(private val listForeCast: List<ForeCast>) : RecyclerView.Adapter<ListForeCastAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_hari, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listForeCast.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (days, icon, suhu) = listForeCast[position]
        holder.daysForeCast.text = days
        holder.suhuForeCast.text = suhu
        Glide.with(holder.iconForeCast.context)
            .load(icon)
            .into(holder.iconForeCast)

        // Set click listener on the itemView
        holder.itemView.setOnClickListener {
//            Toast.makeText(holder.itemView.context, "Clicked: $name", Toast.LENGTH_SHORT).show()
            val fragment = HomeFragment()
            val fragmentManager =
                (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
            fragmentTransaction.addToBackStack(null)  // Opsional: menambahkan ke back stack jika diperlukan
            fragmentTransaction.commit()
        }


    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val daysForeCast: TextView = itemView.findViewById(R.id.txtHari)
        val iconForeCast: ImageView = itemView.findViewById(R.id.imgCuacaHari)
        val suhuForeCast: TextView = itemView.findViewById(R.id.txtSuhuHari)
    }
}