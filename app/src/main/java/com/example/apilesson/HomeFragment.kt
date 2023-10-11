package com.example.apilesson

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.apilesson.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalTime


class HomeFragment : Fragment() {
    private val apiUrl = "https://api.weatherapi.com/v1/forecast.json?key=11b9394e7e024a2588a44954230610&q=Tashkent&days=8&aqi=no&alerts=no"
    private lateinit var binding: FragmentHomeBinding
    var forecastAdapter = ForecastAdapter(JSONArray(), object : ForecastAdapter.ItemClickInterface{
        override fun onParentClick(day: JSONObject) {
            changeToday(day)
        }
    })
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentHour = LocalTime.now().hour
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val requestQue = Volley.newRequestQueue(requireContext())

        binding.forecastRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.todayRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)



        val myShader: Shader = LinearGradient(
            0f, 0f, 0f, 100f,
            Color.WHITE, Color.WHITE,
            Shader.TileMode.CLAMP
        )
        binding.temp.paint.shader = myShader

        val request = JsonObjectRequest(apiUrl,
            { response ->
                val current = response.getJSONObject("current")
                val tempC = current.getDouble("temp_c")
                val windKph = current.getDouble("wind_kph")
                val humidity = current.getInt("humidity")

                binding.windSpeed.text = "${windKph}km/h"
                binding.humidity.text = "${humidity}%"
                binding.temp.text = "${tempC.toInt()} C°"

                forecastAdapter = ForecastAdapter(response.getJSONObject("forecast").getJSONArray("forecastday"), object : ForecastAdapter.ItemClickInterface{
                    override fun onParentClick(day: JSONObject) {
                        changeToday(day)
                    }
                })
                binding.forecastRv.adapter = forecastAdapter
                binding.todayRv.adapter = TodayAdapter(response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour"), currentHour)
                binding.icon.load("https:" + current.getJSONObject("condition").getString("icon"))
                forecastAdapter.notifyDataSetChanged()
            }
        ) { error -> Log.d("TAG", "onErrorResponse: $error") }
        requestQue.add(request)

        return binding.root
    }
    fun changeToday(day: JSONObject){
        //TODO:
    }
}