package com.example.lol_app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lol_app.data.model.ChampionItem
import com.example.lol_app.databinding.ItemChampionBinding

class ChampionAdapter(private val onClickItem: (ChampionItem) -> Unit) :
    RecyclerView.Adapter<ChampionAdapter.ChampionViewHolder>() {

    private val champions = mutableListOf<ChampionItem>()

    fun updateData(newList: List<ChampionItem>) {
        champions.clear()
        champions.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = champions.size

    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        holder.updateUi(champions[position])
        holder.itemView.alpha = 0f
        holder.itemView.translationY = 30f

        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(400)
            .start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {
        val binding = ItemChampionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChampionViewHolder(binding)
    }

    inner class ChampionViewHolder(
        private val binding: ItemChampionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun updateUi(item: ChampionItem) {
            binding.tvChampionName.text = item.name
            binding.tvChampionDesc.text = item.title

            Glide.with(binding.ivChampion.context)
                .load(item.imageUrl)
                .into(binding.ivChampion)
            binding.root.setOnClickListener {
                binding.root.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction {
                        binding.root.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()

                        onClickItem(item)
                    }
                    .start()
            }

        }

    }


}