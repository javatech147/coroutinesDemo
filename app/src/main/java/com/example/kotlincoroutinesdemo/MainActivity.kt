package com.example.kotlincoroutinesdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    var count: Int = 0

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDownload.setOnClickListener {
            // Perform long running heavy task to background thread.
            CoroutineScope(Dispatchers.IO).launch {
                downloadTaskFromInternet()
            }
        }

        btnCount.setOnClickListener {
            tvCount.text = count++.toString()
        }
    }


    private suspend fun downloadTaskFromInternet() {
        // withContext() is used to switch scope from IO to Main, because we have to update UI
        withContext(Dispatchers.Main) {
            for (i in 1..20000) {
                Log.d(TAG, "Current Thread Name : ${Thread.currentThread().name} : $i")
                tvDownloadStatus.text = "Current Thread Name : ${Thread.currentThread().name} : $i"
                delay(100)
            }
        }
    }
}