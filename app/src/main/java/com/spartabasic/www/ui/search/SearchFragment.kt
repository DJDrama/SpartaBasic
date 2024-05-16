package com.spartabasic.www.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.spartabasic.www.R
import com.spartabasic.www.data.network.NetworkModule
import com.spartabasic.www.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val TAG = "SearchFragment"
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        fetchImages()
    }

    private fun fetchImages() {
        lifecycleScope.launch {
            val response = NetworkModule.retrofit.getSearchedImages(
                query = "test",
                page = 1,
                size = 10,
                sort = "recency"
            )
            if (response.isSuccessful) {
                Log.i(TAG, "Successful! ${response.code()}")
                val body = response.body()
                if (body != null) {
                    val meta = body.meta
                    val documents = body.documents
                    binding.tvMeta.text = "$meta"
                    binding.tvDocuments.text = "$documents"

                } else {
                    Log.e(TAG, "Something went wrong!")
                }
            } else {
                Log.e(TAG, "Error: ${response.errorBody()}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}