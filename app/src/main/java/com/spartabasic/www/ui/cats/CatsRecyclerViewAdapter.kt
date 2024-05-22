package com.spartabasic.www.ui.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.spartabasic.www.databinding.ItemCatBinding
import com.spartabasic.www.ui.model.CatItem

class CatsRecyclerViewAdapter(
    private val onClickCat: (CatItem) -> Unit,
) : ListAdapter<CatItem, CatsRecyclerViewAdapter.CatViewHolder>(CatDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            onClickCat = onClickCat,
            binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(cat = getItem(position))
    }

    class CatViewHolder(
        private val onClickCat: (CatItem) -> Unit,
        private val binding: ItemCatBinding
    ) : ViewHolder(binding.root) {
        private var cat: CatItem? = null

        init {
            binding.root.setOnClickListener {
                cat?.let {
                    onClickCat(it)
                }
            }
        }

        fun bind(cat: CatItem) {
            this.cat = cat
            Glide
                .with(binding.root)
                .load(cat.imageUrl)
                .sizeMultiplier(0.5f)
                .into(binding.imageView)

            binding.chip.text = cat.tags[0]
        }
    }

    companion object CatDiffCallback : DiffUtil.ItemCallback<CatItem>() {
        override fun areItemsTheSame(oldItem: CatItem, newItem: CatItem): Boolean {
            return oldItem.id == newItem.id && oldItem.tags == newItem.tags
        }

        override fun areContentsTheSame(oldItem: CatItem, newItem: CatItem): Boolean {
            return oldItem == newItem
        }

    }
}