package com.example.lol_app.ui.fragment.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lol_app.data.adapter.SearchChampionAdapter
import com.example.lol_app.databinding.FragmentSearchBinding
import android.text.TextWatcher
import androidx.lifecycle.lifecycleScope
import com.example.lol_app.ui.detail.ChampionDetailActivity
import com.example.lol_app.ui.home.ChampionAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private val searchAdapter  = ChampionAdapter { champion ->
        val intent = Intent(requireContext(), ChampionDetailActivity::class.java)
        intent.putExtra(ChampionDetailActivity.KEY_CHAMPION_ID, champion.id)
        startActivity(intent)
    }
    private val binding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initRecyclerView()
        initListener()
        viewModel.getChampions()
    }

    private fun initRecyclerView() {
        binding.rcvSearchChampion.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun initListener() {
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            tvCancel.setOnClickListener {
                binding.edtSearch.setText("")
                viewModel.clearSearch()
            }
            edtSearch.addTextChangedListener( object : TextWatcher  {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {

                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {
                    viewModel.searchChampion(p0.toString().trim())
                }

            }
            )
        }



}
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.searchResult.collect { list ->
                searchAdapter.updateData(list)

                if (list.isEmpty()) {
                    binding.layoutEmpty.visibility = View.VISIBLE
                    binding.rcvSearchChampion.visibility = View.GONE
                } else {
                    binding.layoutEmpty.visibility = View.GONE
                    binding.rcvSearchChampion.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.query.collect { text ->
                binding.tvCancel.visibility =
                    if (text.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}