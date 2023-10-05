package com.example.apilesson

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import coil.load
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val apiUrl = "http://api.weatherapi.com/v1/current.json?key=159d40268ea744789e993117230510&q=Tashkent&aqi=no"
    private lateinit var tempTextView: TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val requestQue = Volley.newRequestQueue(this)


        tempTextView = findViewById(R.id.temp)

        val paint = tempTextView.paint
        val width = paint.measureText(tempTextView.text.toString())
        val textShader: Shader = LinearGradient(0f, tempTextView.textSize, width, 0f, intArrayOf(
            Color.parseColor("#FFFFFFFF"),
            Color.parseColor("#EAEAEA")
        ), null, Shader.TileMode.CLAMP)

        tempTextView.paint.shader = textShader



        val request = JsonObjectRequest(apiUrl,
            { response ->
                val current = response.getJSONObject("current")
                val condition = response.getJSONObject("condition")

                val iconUrl = condition.getString("content")
                val tempC = current.getDouble("temp_c")
                val windKph = current.getDouble("wind_kph")
                val humidity = current.getInt("humidity")

                findViewById<TextView>(R.id.wind_speed).text = "${windKph}km/h"
                findViewById<TextView>(R.id.humidity).text = "${humidity}%"
                findViewById<ImageView>(R.id.iconIV).load(iconUrl){
                    error(R.drawable.ic_launcher_foreground)
                }

                tempTextView.text = "${tempC}°"

            }
        ) { error -> Log.d("TAG", "onErrorResponse: $error") }
        requestQue.add(request)

    }
}