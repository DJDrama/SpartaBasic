package com.spartabasic.www.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.spartabasic.www.R
import com.spartabasic.www.data.network.NetworkModule
import com.spartabasic.www.data.network.api.KakaoApiResponse
import com.spartabasic.www.data.network.model.VideoSearchDto
import com.spartabasic.www.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val TAG = "SearchFragment"
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        //fetchImages()
        fetchVideos()
    }

    // Coroutine을 사용하는 방법
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

    // Call 인터페이스를 사용하는 방법
    private fun fetchVideos() {
        NetworkModule.retrofit.getSearchedVideos(
            query = "test",
            page = 1,
            size = 10,
            sort = "recency"
        ).enqueue(object : Callback<KakaoApiResponse<List<VideoSearchDto>>> {
            override fun onResponse(
                call: Call<KakaoApiResponse<List<VideoSearchDto>>>,
                response: Response<KakaoApiResponse<List<VideoSearchDto>>>
            ) {
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
                }
            }

            override fun onFailure(
                p0: Call<KakaoApiResponse<List<VideoSearchDto>>>,
                p1: Throwable
            ) {
                Log.e(TAG, "Error: ${p1.message}")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}