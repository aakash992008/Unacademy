package com.aakash.unacademy.worker

import android.os.Handler
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ServiceWorker(val workerThreadName: String) {

    private val threadPool: ExecutorService = Executors.newSingleThreadExecutor()

    fun <T> addTask(listener: Task<T>) {
        val handler = Handler()
        threadPool.submit {
            //RUNS ON BACKGROUND THREAD
            val result = listener.onTaskExecuting()
            handler.post {
                //POST RESULT ON UI THREAD
                listener.onTaskCompleted(result)
            }
        }

    }

    interface Task<T> {
        fun onTaskExecuting(): T
        fun onTaskCompleted(result: T)

    }
}