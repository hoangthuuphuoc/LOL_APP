package com.example.lol_app.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lol_app.data.model.ChampionItem
import com.example.lol_app.databinding.ItemChampionBinding

class SearchChampionAdapter(private val onClickItem: (ChampionItem) -> Unit) : RecyclerView.Adapter<SearchChampionAdapter.SearchViewHolder>() {

    private val champions = mutableListOf<ChampionItem>()

    fun updateData(newList: List<ChampionItem>) {
        champions.clear()
        champions.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemChampionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int = champions.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.updateUi(champions[position])
    }

    inner class SearchViewHolder(
        private val binding: ItemChampionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun updateUi(item: ChampionItem) {
            binding.tvChampionName.text = item.name
            binding.tvChampionDesc.text = item.title

            Glide.with(binding.ivChampion.context)
                .load(item.imageUrl)
                .into(binding.ivChampion)
            binding.root.setOnClickListener {
                onClickItem(item)
            }
        }
    }
}