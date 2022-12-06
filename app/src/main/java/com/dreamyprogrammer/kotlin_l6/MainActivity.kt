package com.dreamyprogrammer.kotlin_l6

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dreamyprogrammer.kotlin_l6.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentWorkerThread: Thread? = null
    //private val handler: Handler = Handler(Looper.getMainLooper())
    private val handler: Handler by lazy { Handler(mainLooper) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            currentWorkerThread = Thread {
                val delay = 2_000L
                var counter = 0
                while (!Thread.currentThread().isInterrupted) {
                    try {
                        Thread.sleep(delay)
                    } catch (e: InterruptedException) {
                        Log.d("@@@", "Exeption!!!")
                        break
                    }
                    counter++
                    val mills = Calendar.getInstance().timeInMillis
                    Log.d("@@@", "Готовность: $counter Время: $mills")
                    handler.post {
                        binding.resultTextView.text =
                            "${binding.resultTextView.text}\nГотовность: $counter Время: $mills"
                    }
                }
            }
            currentWorkerThread?.start()
        }


        binding.stopButton.setOnClickListener {
            currentWorkerThread?.interrupt()
            currentWorkerThread == null
        }


    }

    override fun onDestroy() {
        currentWorkerThread?.interrupt()
        super.onDestroy()
    }
}
