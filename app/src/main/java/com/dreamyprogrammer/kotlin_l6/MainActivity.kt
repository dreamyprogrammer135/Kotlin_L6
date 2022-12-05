package com.dreamyprogrammer.kotlin_l6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dreamyprogrammer.kotlin_l6.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentWorkerThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            currentWorkerThread = Thread {
                val delay = 2_000L
                var counter = 0
                val currTread = Thread.currentThread()
                while (!Thread.currentThread().isInterrupted) {
                    try {
                        Thread.sleep(delay)
                    }catch (e: InterruptedException) {
                        Log.d("@@@", "Exeption!!!")
                        break
                    }

                    counter++
                    val mills = Calendar.getInstance().timeInMillis
                    Log.d("@@@", "Готовность: $counter Время: $mills")
                    runOnUiThread {
                        binding.resultTextView.text =
                            "${binding.resultTextView.text}\nГотовность: $counter Время: $mills"
                    }
                }
            }.apply { start() }
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