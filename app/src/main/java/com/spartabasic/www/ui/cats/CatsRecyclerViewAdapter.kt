package com.spartabasic.www.ui.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.spartabasic.www.BuildConfig
import com.spartabasic.www.databinding.ItemCatBinding
import com.spartabasic.www.domain.model.Cat

class CatsRecyclerViewAdapter :
    ListAdapter<Cat, CatsRecyclerViewAdapter.CatViewHolder>(CatDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(cat = getItem(position))
    }

    class CatViewHolder(
        private val binding: ItemCatBinding
    ) : ViewHolder(binding.root) {
        fun bind(cat: Cat) {
            Glide
                .with(binding.root)
                .load(BuildConfig.CATAAS_BASE_URL.plus("/cat/").plus(cat.id))
                .sizeMultiplier(0.5f)
                .into(binding.imageView)

            binding.chip.text = cat.tags[0]
        }
    }

    companion object CatDiffCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id && oldItem.tags == newItem.tags
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }

    }
}