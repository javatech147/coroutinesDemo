package com.example.kotlincoroutinesdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var job: Job
    lateinit var deferred: Deferred<Int>
    var count = 0

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = CoroutineScope(Dispatchers.Main).launch {
            val result = getTotalUserCount()
            Log.d(TAG, "Total User Count : $result")
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

    private suspend fun getTotalUserCount(): Int {
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(1000)
                count = 50
            }

            deferred = async(Dispatchers.IO) {
                delay(3000)
                return@async 70
            }
        }
        return count + deferred.await()
    }

}