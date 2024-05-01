package com.spartabasic.www.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spartabasic.www.data.AnswerType
import com.spartabasic.www.data.Record
import com.spartabasic.www.databinding.ItemCorrectRecordBinding
import com.spartabasic.www.databinding.ItemWrongRecordBinding

interface RecordClickListener {
    fun onClickItem(record: Record)
}

class RecordRecyclerViewAdapter(
    private val recordClickListener: RecordClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var records: List<Record> = emptyList()

    class CorrectViewHolder( // Static Nested class
        private val binding: ItemCorrectRecordBinding,
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
            binding.tvTrial.text = recordItem.trial.toString()
            binding.tvTarget.text = recordItem.target.toString()
            binding.tvRecord.text = recordItem.record.toString()
            // enum
            binding.tvCorrect.text = recordItem.isCorrect.text

            // sealed
            /* binding.tvCorrect.text = when (recordItem.isCorrect) {
                 is AnotherAnswerType.Correct -> recordItem.isCorrect.text
                 is AnotherAnswerType.Wrong -> recordItem.isCorrect.text
             }*/
        }
    }

    class WrongViewHolder(
        private val binding: ItemWrongRecordBinding,
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
            binding.tvTrial.text = recordItem.trial.toString()
            binding.tvTarget.text = recordItem.target.toString()
            binding.tvRecord.text = recordItem.record.toString()
            // enum
            binding.tvCorrect.text = recordItem.isCorrect.text

            // sealed
            /*binding.tvCorrect.text = when (recordItem.isCorrect) {
                is AnotherAnswerType.Correct -> recordItem.isCorrect.text
                is AnotherAnswerType.Wrong -> recordItem.isCorrect.text
            }*/
        }
    }

    override fun getItemViewType(position: Int): Int {
        // enum
        return records[position].isCorrect.answerValue

        // sealed
        /* return when (val isCorrect = records[position].isCorrect) {
             is AnotherAnswerType.Correct -> isCorrect.answerValue
             is AnotherAnswerType.Wrong -> isCorrect.answerValue
         }*/
    }

    override fun getItemId(position: Int): Long {
        return records[position].trial.toLong()
    }

    fun submitList(items: List<Record>) { // List<T>
        this.records = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // enum
        val answerType = AnswerType.entries.find { it.answerValue == viewType }
        val layoutInflater = LayoutInflater.from(parent.context)
        // enum
        return when (answerType) {
            AnswerType.CORRECT -> CorrectViewHolder(
                binding = ItemCorrectRecordBinding.inflate(layoutInflater, parent, false),
                recordClickListener = recordClickListener
            )

            AnswerType.WRONG -> WrongViewHolder(
                binding = ItemWrongRecordBinding.inflate(layoutInflater, parent, false),
                recordClickListener = recordClickListener
            )

            else -> throw IllegalStateException("answerType cannot be null!")
        }

        // sealed
        /* return when (viewType) {
             0 -> WrongViewHolder(
                 binding = ItemWrongRecordBinding.inflate(layoutInflater, parent, false),
                 recordClickListener = recordClickListener
             )

             1 -> CorrectViewHolder(
                 binding = ItemCorrectRecordBinding.inflate(layoutInflater, parent, false),
                 recordClickListener = recordClickListener
             )

             else -> throw IllegalStateException()
         }*/
    }

    override fun getItemCount(): Int {
        return this.records.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recordItem = records[position]
        when (holder) {
            is CorrectViewHolder -> {
                holder.bind(recordItem = recordItem)
            }

            is WrongViewHolder -> {
                holder.bind(recordItem = recordItem)
            }
        }
    }
}