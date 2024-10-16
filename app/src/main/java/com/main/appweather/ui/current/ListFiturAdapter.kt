package com.main.appweather.ui.current

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.main.appweather.R
import com.main.appweather.source.data.FiturWeather

class ListFiturWeatherAdapter(private val listFiturWeather: List<FiturWeather>) : RecyclerView.Adapter<ListFiturWeatherAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_detail_cuaca, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFiturWeather.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (fitur, icon, suhu) = listFiturWeather[position]
        holder.nameFitur.text = fitur
        holder.suhuFitur.text = suhu
        Glide.with(holder.iconFitur.context)
            .load(icon)
            .into(holder.iconFitur)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameFitur: TextView = itemView.findViewById(R.id.txtFitur)
        val iconFitur: ImageView = itemView.findViewById(R.id.imgFitur)
        val suhuFitur: TextView = itemView.findViewById(R.id.txtSuhuFitur)
    }
}