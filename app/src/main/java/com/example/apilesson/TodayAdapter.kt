package com.example.apilesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.json.JSONArray
import org.json.JSONObject

class TodayAdapter(private val hours:JSONArray, val from : Int): RecyclerView.Adapter<TodayAdapter.MyHolder>() {
    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val hour : TextView = itemView.findViewById(R.id.today_hour)
        val temp : TextView = itemView.findViewById(R.id.today_temp)
        val icon : ImageView = itemView.findViewById(R.id.forecast_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.today_item, parent, false))
    }

    override fun getItemCount(): Int {
        return hours.length()-from
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val hour:JSONObject = hours.getJSONObject(position+from)
        val time = hour.getString("time")
        val temp = hour.getDouble("temp_c")
        val iconUrl = hour.getJSONObject("condition").getString("icon")
        holder.hour.text = time.substring(time.length-6)
        holder.temp.text = temp.toInt().toString() + " C°"
        holder.icon.load("https:" + iconUrl)
    }
}