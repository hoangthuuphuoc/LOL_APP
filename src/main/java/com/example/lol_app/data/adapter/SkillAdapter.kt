package com.example.lol_app.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lol_app.R
import com.example.lol_app.data.model.SkillItem
import com.example.lol_app.databinding.ItemSkillBinding

class SkillAdapter(
    private val onClickItem: (SkillItem) -> Unit
) : RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {

    private val skills = mutableListOf<SkillItem>()
    private var selectedPosition = 0


    fun updateData(newList: List<SkillItem>) {
        skills.clear()
        skills.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = ItemSkillBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SkillViewHolder(binding)
    }

    override fun getItemCount(): Int = skills.size

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.updateUi(skills[position])
    }

    inner class SkillViewHolder(
        private val binding: ItemSkillBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun updateUi(item: SkillItem) {
            Glide.with(binding.ivSkill.context)
                .load(item.imageUrl)
                .into(binding.ivSkill)

            binding.root.setOnClickListener {
                onClickItem(item)
            }
            if (position == selectedPosition) {
                binding.viewBorderBack.visibility = View.VISIBLE
                binding.viewBorderFront.visibility = View.VISIBLE
            } else {
                binding.viewBorderBack.visibility = View.INVISIBLE
                binding.viewBorderFront.visibility = View.INVISIBLE
            }
            binding.root.setOnClickListener {
                val oldPosition = selectedPosition
                selectedPosition = bindingAdapterPosition

                notifyItemChanged(oldPosition)
                notifyItemChanged(selectedPosition)

                onClickItem(item)
            }
        }
    }
}