package com.example.kotlincoroutinesdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var job: Job

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = CoroutineScope(Dispatchers.Main).launch {
            downloadData()
        }

        btnShowStatus.setOnClickListener {
            when {
                job.isActive -> {
                    tvJobStatus.text = "Active"
                }
                job.isCancelled -> {
                    tvJobStatus.text = "Cancelled"
                }
                job.isCompleted -> {
                    tvJobStatus.text = "Completed"
                }
            }
        }

        btnCancel.setOnClickListener {
            job.cancel()
        }
    }

    private suspend fun downloadData() {
        withContext(Dispatchers.IO) {
            repeat(20) {
                delay(1000)
                Log.d(TAG, "Repeating $it")
            }
        }
    }
}