package com.spartabasic.www.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.spartabasic.www.R
import com.spartabasic.www.data.AnswerType
import com.spartabasic.www.data.MyRecords
import com.spartabasic.www.data.Record
import com.spartabasic.www.databinding.FragmentGuessBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GuessFragment : Fragment(R.layout.fragment_guess), View.OnClickListener {
    private var _binding: FragmentGuessBinding? = null
    private val TAG = "GuessFragment"
    private val binding: FragmentGuessBinding
        get() = _binding!!

    private var job: Job? = null
    private var counter = 1
    private var randomValue = (1..100).random()
    private var isStopped = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGuessBinding.bind(view)
        Log.i(TAG, "onViewCreated ${parentFragmentManager.backStackEntryCount}")

        if (savedInstanceState != null) {
            randomValue = savedInstanceState.getInt("randomValue")
            isStopped = savedInstanceState.getBoolean("isStopped")
        }

        initButtons()
        setRandomValueBetweenOneToHundred()
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        counter = savedInstanceState?.getInt("counter") ?: 1
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
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            MyRecords.addRecord(
                record = Record(
                    target = randomText.toInt(),
                    record = spartaText.toInt(),
                    // enum
                    isCorrect = AnswerType.CORRECT

                    // sealed
                    //  isCorrect = AnotherAnswerType.Correct()
                )
            )
        } else {
            Toast.makeText(requireContext(), "Wrong!", Toast.LENGTH_SHORT).show()
            MyRecords.addRecord(
                record = Record(
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
                    if (MyRecords.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "You don't have any record!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return
                    }
                    val intent = Intent(requireContext(), SecondActivity::class.java)
                    // enum
                    intent.putExtra("test", AnswerType.CORRECT);
                    startActivity(intent)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart called")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView called")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy Called")
    }
}