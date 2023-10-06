package com.example.apilesson

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.android.volley.Response
import com.android.volley.Response.*
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.apilesson.databinding.FragmentHomeBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val apiUrl = "https://api.weatherapi.com/v1/current.json?key=11b9394e7e024a2588a44954230610&q=Tashkent&aqi=no"

        val requestQue = Volley.newRequestQueue(requireContext())


        val tempTextView = binding.temp


        val request = JsonObjectRequest(apiUrl, object : Listener<JSONObject>{
            override fun onResponse(response: JSONObject?) {
                Log.d("TAG", "onCreate: $response")
                val current = response!!.getJSONObject("current")
                val condition = current.getJSONObject("condition")

                val iconUrl = condition.getString("content")
                val tempC = current.getDouble("temp_c")
                val windKph = current.getDouble("wind_kph")
                val humidity = current.getInt("humidity")

                binding.windSpeed.text = "${windKph}km/h"
                binding.humidity.text = "${humidity}%"
                binding.iconIV.load(iconUrl){
                    error(R.drawable.ic_launcher_foreground)
                }

                tempTextView.text = "${tempC}°"
            }
        }, object : ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                Log.d("TAG", "onErrorResponse: $error")
            }
        })
        requestQue.add(request)



        return binding.root
    }

}