package com.spartabasic.www

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.spartabasic.www.databinding.ItemRecordBinding

interface RecordClickListener {
    fun onClickItem(record: Record)
}

class RecordRecyclerViewAdapter(
    private val recordClickListener: RecordClickListener
) : RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder>() {

    private var records: List<Record> = emptyList()

    class ViewHolder( // Static Nested class
        private val binding: ItemRecordBinding,
        private val recordClickListener: RecordClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private var record: Record? = null

        init {
            binding.root.setOnClickListener {
                record?.let {
                    recordClickListener.onClickItem(record = it)
                }
            }
        }

        fun bind(recordItem: Record) {
            this.record = recordItem
            binding.textViewRecord.text = recordItem.record
        }
    }

    fun submitList(items: List<Record>) { // List<T>
        this.records = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            binding = binding,
            recordClickListener = recordClickListener
        )
    }

    override fun getItemCount(): Int {
        return this.records.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recordItem = records[position])
    }
}