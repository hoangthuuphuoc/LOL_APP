package com.example.lol_app.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lol_app.data.model.ChampionItem
import com.example.lol_app.databinding.ItemRecomendBinding

class RecomendAdapter(
    private val onNextClick: () -> Unit
) : RecyclerView.Adapter<RecomendAdapter.RecomendViewHolder>() {

    private val champions = mutableListOf<ChampionItem>()

    fun updateData(newList: List<ChampionItem>) {
        champions.clear()
        champions.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendViewHolder {
        val binding = ItemRecomendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecomendViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return champions.size
    }

    override fun onBindViewHolder(holder: RecomendViewHolder, position: Int) {
        holder.updateUi(champions[position])
    }

    inner class RecomendViewHolder(
        private val binding: ItemRecomendBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun updateUi(item: ChampionItem) {
            binding.tvRecommendName.text = item.name
            binding.tvRecommendTitle.text = item.title

            Glide.with(binding.ivRecommend.context)
                .load(item.splashUrl)
                .into(binding.ivRecommend)

            binding.ivNext.setOnClickListener {
                onNextClick()
            }
        }
    }
}