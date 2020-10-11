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
            CoroutineScope(Dispatchers.Main).launch {
                val deferred1: Deferred<Int> = async(Dispatchers.IO) {
                    getResult1()
                }
                val deferred2 = async(Dispatchers.IO) {
                    getResult2()
                }

                val total = deferred1.await() + deferred2.await()
                Log.d(TAG, "Total is $total")

            }
        }

        btnCount.setOnClickListener {
            tvCount.text = count++.toString()
        }
    }

    suspend fun getResult1(): Int {
        Log.d(TAG, "Executing task1")
        delay(15000)
        return 15
    }

    suspend fun getResult2(): Int {
        Log.d(TAG, "Executing task2")
        delay(1000)
        return 10
    }
}