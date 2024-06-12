package com.example.coroutines

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var count =20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btn =findViewById<Button>(R.id.button)
        var txtview =findViewById<TextView>(R.id.textView)

        fun launchCoroutines() {
            (1..count).forEach {
                txtview.text = "Started Coroutine ${it}"
                coroutineScope.launch(Dispatchers.Main) {
                    txtview.text = performTaskAsync(it).await()
                }
            }
        }

        btn.setOnClickListener {
            launchCoroutines()
        }


    }

    private suspend fun performTaskAsync(tasknumber: Int): Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(5000)
            return@async "Finished Coroutine $tasknumber"
        }
}