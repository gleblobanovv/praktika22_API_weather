package com.example.weather_paraktika20

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class weather : AppCompatActivity() {
    private lateinit var city1: TextView
    private lateinit var temp: TextView
    private lateinit var pressure: TextView
    private lateinit var speed: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather)

        city1 = findViewById(R.id.city)
        temp = findViewById(R.id.temp)
        pressure = findViewById(R.id.pressure)
        speed = findViewById(R.id.speed_windy)
        button = findViewById(R.id.search)

        button.setOnClickListener {
            getResult(city1.text.toString())
        }
    }

     fun getResult(city: String) {
        if (city1.text.toString().isNotEmpty() && city.isNotEmpty()) {
            val key = "e29117faf4609276fc4308798861ec72"
            val url =
                "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    try {
                        val obj = JSONObject(response)
                        val cityName = obj.getString("name")
                        val main = obj.getJSONObject("main")
                        val temperature = main.getString("temp")
                        val pressureValue = main.getString("pressure")
                        val wind = obj.getJSONObject("wind")
                        val windSpeed = wind.getString("speed")

                        city1.text = cityName
                        temp.text = "$temperature°C"
                        pressure.text = "$pressureValue hPa"
                        speed.text = "$windSpeed м/с"

                        Log.d("MyLog", "City: $cityName, Temp: $temperature°C, Pressure: $pressureValue hPa, Wind Speed: $windSpeed м/с")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    Log.d("MyLog", "Volley error: $error")
                }
            )
            queue.add(stringRequest)
        }
    }

}
