package com.example.apilesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class TodayAdapter(private val hours:JSONArray): RecyclerView.Adapter<TodayAdapter.MyHolder>() {
    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val hour : TextView = itemView.findViewById(R.id.today_hour)
        val temp : TextView = itemView.findViewById(R.id.today_temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.today_item, parent, false))
    }

    override fun getItemCount(): Int {
        return hours.length()
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val hour:JSONObject = hours.getJSONObject(position)
        val time = hour.getString("time")
        val temp = hour.getDouble("temp_c")
        holder.hour.text = time
        holder.temp.text = temp.toString()
    }
}