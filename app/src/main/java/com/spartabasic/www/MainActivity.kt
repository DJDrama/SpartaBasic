package com.spartabasic.www

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var job: Job? = null

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"
    private var counter = 1
    private var randomValue = (1..100).random()
    private var isStopped = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        initButtons()
        setRandomValueBetweenOneToHundred()
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

    private fun initButtons() {
        binding.clickButton.setOnClickListener {
            checkAnswerAndShowToast()
            job?.cancel()
            isStopped = true
        }
        binding.restartButton.setOnClickListener(this)
        binding.checkRecordButton.setOnClickListener(this)
    }

    private fun setRandomValueBetweenOneToHundred() {
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
                    delay(250) // 1초 = 1000
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
            myRecords.add(
                element = Record(
                    trial = myRecords.size + 1,
                    target = randomText.toInt(),
                    record = spartaText.toInt(),
                    // enum
                    isCorrect = AnswerType.CORRECT

                    // sealed
                    //  isCorrect = AnotherAnswerType.Correct()
                )
            )
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            myRecords.add(
                element = Record(
                    trial = myRecords.size + 1,
                    target = randomText.toInt(),
                    record = spartaText.toInt(),
                    // enum
                    isCorrect = AnswerType.WRONG

                    // sealed
                    //isCorrect = AnotherAnswerType.Wrong(stupidLevel = (1..5).random())
                )
            )
        }
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (it) {
                binding.restartButton -> {
                    isStopped = false
                    counter = 1
                    randomValue = (1..100).random()
                    setRandomValueBetweenOneToHundred()
                    setJobAndLaunch()
                }

                binding.checkRecordButton -> {
                    if (myRecords.isEmpty()) {
                        Toast.makeText(this, "You don't have any record!", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    val intent = Intent(this, SecondActivity::class.java)
                    // enum
                    intent.putExtra("test", AnswerType.CORRECT);
                    startActivity(intent)
                }
            }
        }
    }
}