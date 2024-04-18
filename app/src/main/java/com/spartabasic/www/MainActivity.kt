package com.spartabasic.www

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.spartabasic.www.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var job: Job? = null

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"
    private var counter = 1
    private var randomValue = (1..100).random()
    private var isStopped = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            randomValue = savedInstanceState.getInt("randomValue")
            isStopped = savedInstanceState.getBoolean("isStopped")
        }

        setupButton()
        setRandomValueBetweenOneToHundred()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        setJobAndLaunch()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        job?.cancel()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        counter = savedInstanceState.getInt("counter")
        if (counter > 100) {
            counter = 100
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt("counter", counter)
        outState.putInt("randomValue", randomValue)
        outState.putBoolean("isStopped", isStopped)
    }

    private fun setupButton() {
        binding.clickButton.setOnClickListener {
            checkAnswerAndShowToast()
            job?.cancel()
            isStopped = true
        }
    }

    private fun setRandomValueBetweenOneToHundred() {
        val randomValue = (1..100).random()
        binding.textViewRandom.text = randomValue.toString()
    }


    private fun setJobAndLaunch() {
        job?.cancel() // job is uninitialized exception
        job = lifecycleScope.launch {
            while (counter <= 100) {
                if (isActive) {
                    binding.spartaTextView.text = counter.toString()
                    if (isStopped) {
                        break
                    }
                    delay(500) // 1초 = 1000
                    counter += 1
                }
            }
        }
    }

    private fun checkAnswerAndShowToast() {
        val spartaText = binding.spartaTextView.text.toString()
        val randomText = binding.textViewRandom.text.toString()
        if (spartaText == randomText) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}