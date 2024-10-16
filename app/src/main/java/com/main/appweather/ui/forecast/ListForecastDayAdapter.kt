package com.main.appweather.ui.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.main.appweather.R
import com.main.appweather.source.data.RecyclerForecastDay

class ListForecastDayAdapter(private val listForecastDay: List<RecyclerForecastDay>) : RecyclerView.Adapter<ListForecastDayAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_forecast_day, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listForecastDay.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (date, suhu, cuaca, icon, uv) = listForecastDay[position]
        holder.daysForecast.text = date
        holder.suhuForecast.text = suhu
        holder.cuacaForecast.text = cuaca
        holder.uvForecast.text = uv
        Glide.with(holder.iconForecast.context)
            .load(icon)
            .into(holder.iconForecast)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val daysForecast: TextView = itemView.findViewById(R.id.txtDateDay)
        val suhuForecast: TextView = itemView.findViewById(R.id.txtSuhuDay)
        val iconForecast: ImageView = itemView.findViewById(R.id.imgCuacaDay)
        val cuacaForecast: TextView = itemView.findViewById(R.id.txtCuacaDay)
        val uvForecast: TextView = itemView.findViewById(R.id.txtUvDay)
    }
}