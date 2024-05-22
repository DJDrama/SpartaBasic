package com.spartabasic.www.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.spartabasic.www.R
import com.spartabasic.www.databinding.FragmentCatDetailBinding
import com.spartabasic.www.ui.model.CatItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatDetailFragment : Fragment(R.layout.fragment_cat_detail) {

    private var _binding: FragmentCatDetailBinding? = null
    private val binding: FragmentCatDetailBinding
        get() = _binding!!


    private val TAG = "CatDetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatDetailBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        arguments?.let {
            val catItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable("catItem", CatItem::class.java)
            } else {
                it.getParcelable("catItem")
            }
            Glide.with(requireContext())
                .load(catItem?.imageUrl ?: R.drawable.baseline_person_24)
                .into(binding.imageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}