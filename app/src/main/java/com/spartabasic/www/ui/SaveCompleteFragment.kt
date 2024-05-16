package com.spartabasic.www.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.spartabasic.www.R
import com.spartabasic.www.databinding.FragmentSaveCompleteBinding

class SaveCompleteFragment : Fragment(R.layout.fragment_save_complete) {
    private val TAG = "SaveCompleteFragment"
    private var _binding: FragmentSaveCompleteBinding? = null
    private val binding: FragmentSaveCompleteBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSaveCompleteBinding.bind(view)
        arguments?.let {
            binding.tvName.text = it.getString("name")
        }
        Log.e(TAG, "savedInstanceState : $savedInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}