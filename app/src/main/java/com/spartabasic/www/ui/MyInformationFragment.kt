package com.spartabasic.www.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.spartabasic.www.R
import com.spartabasic.www.databinding.FragmentMyInformationBinding

class MyInformationFragment : Fragment(R.layout.fragment_my_information), View.OnClickListener {
    private var _binding: FragmentMyInformationBinding? = null
    private val binding: FragmentMyInformationBinding
        get() = _binding!!

    private var myName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyInformationBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        binding.edtName.doAfterTextChanged {
            myName = it.toString()
            binding.tvName.text = "$myName!!!!"
        }

        binding.btnSave.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (it) {
                binding.btnSave -> {
                    Toast.makeText(requireContext(), "Saved: $myName~!", Toast.LENGTH_LONG).show()
                    parentFragmentManager.beginTransaction()
                        .replace<SaveCompleteFragment>(
                            containerViewId = R.id.fragmentContainerView,
                            tag = "SaveCompleteFragment",
                            args = bundleOf(
                                "name" to myName
                            )
                        )
                        .addToBackStack("SaveCompleteFragment")
                        .commit()
                }

                else -> {}
            }
        }
    }

}