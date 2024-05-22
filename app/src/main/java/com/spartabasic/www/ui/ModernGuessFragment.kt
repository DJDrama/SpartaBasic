package com.spartabasic.www.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.spartabasic.www.R
import com.spartabasic.www.data.AnswerType
import com.spartabasic.www.data.MyRecords
import com.spartabasic.www.databinding.FragmentGuessBinding
import com.spartabasic.www.ui.search.SearchFragment

class ModernGuessFragment : Fragment(R.layout.fragment_guess), View.OnClickListener {
    private var _binding: FragmentGuessBinding? = null
    private val binding: FragmentGuessBinding
        get() = _binding!!

    private val TAG = "ModernGuessFragment"
    private val viewModel: ModernGuessViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGuessBinding.bind(view)
        Log.i(TAG, "onViewCreated ${parentFragmentManager.backStackEntryCount}")

        initButtons()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.counter.observe(viewLifecycleOwner) { counter ->
            setCounterValue(counter = counter)
        }

        viewModel.randomValue.observe(viewLifecycleOwner) {
            setRandomValue(randomValue = it)
        }

        viewModel.correctStatus.observe(viewLifecycleOwner) {
            showToastByCorrectStatus(isCorrect = it)
        }
    }

    private fun setCounterValue(counter: Int) {
        binding.spartaTextView.text = counter.toString()
    }

    private fun setRandomValue(randomValue: Int) {
        binding.textViewRandom.text = randomValue.toString()
    }

    private fun showToastByCorrectStatus(isCorrect: Boolean) {
        if (isCorrect) {
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initButtons() {
        binding.ivProfile.setOnClickListener(this)
        binding.clickButton.setOnClickListener(this)
        binding.restartButton.setOnClickListener(this)
        binding.checkRecordButton.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        viewModel.continueCounting()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        viewModel.cancelCounting()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
    }

    private fun checkAnswerAndShowToast() {
        viewModel.checkAnswer()
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (it) {
                binding.ivProfile -> navigateToMyInformationScreen()
                binding.restartButton -> viewModel.initializeAndStart()
                binding.checkRecordButton -> checkRecordsAndNavigateToRecordsScreen()
                binding.clickButton -> checkAnswerAndShowToast()
                binding.btnSearch -> navigateToSearchScreen()
            }
        }
    }

    private fun navigateToSearchScreen(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, SearchFragment())
            .addToBackStack("SearchFragment")
            .commit()
    }

    private fun navigateToMyInformationScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, MyInformationFragment())
            .addToBackStack("MyInformationFragment")
            .commit()
    }

    private fun checkRecordsAndNavigateToRecordsScreen() {
        if (MyRecords.isEmpty()) {
            Toast.makeText(requireContext(), "You don't have any record!", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val intent = Intent(requireContext(), SecondActivity::class.java)
        // enum
        intent.putExtra("test", AnswerType.CORRECT);
        startActivity(intent)
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