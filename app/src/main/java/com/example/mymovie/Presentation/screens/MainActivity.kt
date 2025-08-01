package com.example.mymovie.Presentation.screens

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mymovie.R
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Лог-тег для фильтрации сообщений
    private companion object {
        const val TAG = "MovieApi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Для Android 11+ (API 30+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commit()
        Log.d(TAG, "Activity created")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Activity started")
    }


    private fun requestMovieData(movie: String) {
        // 1. Используем HTTP (временно, для обхода SSL проблем)
        val ipAddress = "13.225.103.26" // Актуальный IPv4 TMDb
        val url = "http://$ipAddress/3/$movie/550?api_key=6f6e8dc98c369b69a1cb7070ddd80765&language=ru-RU"

        // 2. Настройка очереди с очисткой кеша
        val queue = Volley.newRequestQueue(this).apply {
            cache.clear()
        }

        // 3. Создаем запрос с обязательными заголовками
        val request = object : StringRequest(
            Method.GET, url,
            { response ->
                try {
                    val json = JSONObject(response)
                    val title = json.getString("title")
                    Log.d("TAG1", "Успех! Название: $json")
                } catch (e: Exception) {
                    Log.e("TAG", "Ошибка парсинга", e)
                }
            },
            { error ->
                Log.e(TAG, "Ошибка запроса: ${error.message}")
            }
        ) {
            override fun getHeaders() = mapOf(
                "Host" to "api.themoviedb.org",
                "User-Agent" to "MyMovieApp/1.0",
                "Accept" to "application/json"
            )
        }.apply {
            retryPolicy = DefaultRetryPolicy(
                15000, // 15 секунд таймаут
                0,     // Без повторов
                1f
            )
        }

        // 4. Отправляем запрос
        queue.add(request)
    }
}