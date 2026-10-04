package com.example.lol_app.ui.fragment.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.lol_app.R
import com.example.lol_app.data.adapter.RecomendAdapter
import com.example.lol_app.databinding.HomeFragmentBinding
import com.example.lol_app.ui.detail.ChampionDetailActivity
import com.example.lol_app.ui.home.ChampionAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeFragementViewModel by viewModels()

    private val binding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    private val championAdapter = ChampionAdapter { champion ->
        val intent = Intent(
            requireContext(),
            ChampionDetailActivity::class.java
        )

        intent.putExtra(
            ChampionDetailActivity.KEY_CHAMPION_ID,
            champion.id
        )

        startActivity(intent)
    }

    private val recomendAdapter by lazy {
        RecomendAdapter {
            nextBanner()
        }
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

        initRecyclerView()
        observeData()
        viewModel.getChampions()

    }

    private fun initRecyclerView() {
        binding.rcvChampion.apply {
            adapter = championAdapter

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        binding.viewPagerBanner.apply {
            adapter = recomendAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun nextBanner() {
        val currentItem = binding.viewPagerBanner.currentItem
        val totalItem = recomendAdapter.itemCount

        viewModel.onEvent(
            HomeUiEvent.ClickItem(
                currentItem = currentItem,
                totalItem = totalItem
            )
        )
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handleUiState(state)
            }
        }
        lifecycleScope.launch {
            viewModel.uiEffect.collect { effect ->
                handleUiEffect(effect)
            }
        }
    }


    private fun handleUiState(state: HomeUiState) {
        championAdapter.updateData(
            newList = state.listChampion
        )

        recomendAdapter.updateData(
            newList = state.listRecommended
        )
    }

    private fun handleUiEffect(effect: HomeUiEffect) {
        when (effect) {
            HomeUiEffect.NavigationScreen -> {
            }

            is HomeUiEffect.ShowToast -> {
                Toast.makeText(
                    requireContext(),
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is HomeUiEffect.SetItemRecommended -> {
                binding.viewPagerBanner.setCurrentItem(
                    effect.index,
                    true
                )
            }
        }
    }
}