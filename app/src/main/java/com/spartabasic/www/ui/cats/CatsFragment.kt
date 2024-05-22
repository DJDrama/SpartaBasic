package com.spartabasic.www.ui.cats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.spartabasic.www.R
import com.spartabasic.www.databinding.FragmentCatsBinding
import com.spartabasic.www.domain.model.Cat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatsFragment : Fragment(R.layout.fragment_cats) {

    private var _binding: FragmentCatsBinding? = null
    private val binding: FragmentCatsBinding
        get() = _binding!!

    private var catsAdapter: CatsRecyclerViewAdapter? = null

    private val TAG = "CatsFragment"
    private val viewModel: CatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatsBinding.bind(view)
        initViews()
        collectFlows()
    }

    private fun initViews() {
        catsAdapter = CatsRecyclerViewAdapter()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = catsAdapter
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        setCatsToRecyclerView(cats = it.cats)
                    }
                }
                launch {
                    viewModel.errorState.collect(::showError)
                }
            }
        }
    }

    private fun setCatsToRecyclerView(cats: List<Cat>) {
        catsAdapter?.submitList(cats)
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        catsAdapter = null
    }
}