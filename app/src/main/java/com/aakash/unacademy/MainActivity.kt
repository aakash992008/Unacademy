package com.aakash.unacademy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.aakash.unacademy.databinding.ActivityMainBinding
import com.aakash.unacademy.worker.ServiceWorker
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val listOfImages = ArrayList<String>()
    private var count = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadListOfImages()


        //WORKER 1 for 1st Image Loading
        val worker1 = ServiceWorker("Worker Thread 1")
        //WORKER 2 for 2nd Image Loading
        val worker2 = ServiceWorker("Worker Thread 2")


        //When Task1 is started
        mBinding.task1.setOnClickListener {
            worker1.addTask(object : ServiceWorker.Task<String> {
                override fun onTaskExecuting(): String {
                    //TAKES 5 Seconds to Load Image and return Result
                    // RUNS ON BACKGROUND THREAD
                    Thread.sleep(5000)
                    return listOfImages[createCount()]
                }

                override fun onTaskCompleted(result: String) {
                    //RUNS ON UI THREAD
                    //UPDATES THE IMAGE VIEW
                    Glide.with(this@MainActivity).load(result).into(mBinding.imageOne)
                }
            })
        }

        //When Task2 is started
        mBinding.task2.setOnClickListener {
            worker2.addTask(object : ServiceWorker.Task<String> {
                override fun onTaskExecuting(): String {
                    //TAKES 5 Seconds to Load Image and return Result
                    // RUNS ON BACKGROUND THREAD
                    Thread.sleep(5000)
                    return listOfImages[createCount()]
                }

                override fun onTaskCompleted(result: String) {
                    //RUNS ON UI THREAD
                    //UPDATES THE IMAGE VIEW
                    Glide.with(this@MainActivity).load(result).into(mBinding.imageTwo)
                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private fun createCount(): Int {
        if (count == listOfImages.size - 1) {
            count = 0
        } else {
            count++;
        }
        return count;
    }

    private fun loadListOfImages() {
        listOfImages.add(
            "data:image/jpeg;base64,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"
        )
        listOfImages.add("https://free4kwallpapers.com/uploads/originals/2019/05/08/marvel-wallpaper.jpg")
        listOfImages.add("https://wallpaperboat.com/wp-content/uploads/2019/06/marvel-01.jpg")
        listOfImages.add("https://img5.goodfon.com/wallpaper/nbig/3/b0/zheleznyi-chelovek-iron-man-komiks-marvel-kostium-fon.jpg")
        listOfImages.add("https://i.pinimg.com/originals/7d/60/a6/7d60a68e8ab6e8e5cc8a3576cece3d76.png")
    }
}
