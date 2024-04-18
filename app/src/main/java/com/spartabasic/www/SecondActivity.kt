package com.spartabasic.www

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spartabasic.www.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private val TAG = "SecondActivity"
    private lateinit var binding: ActivitySecondBinding
    private lateinit var adapter: RecordRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = RecordRecyclerViewAdapter(recordClickListener = object : RecordClickListener {
            override fun onClickItem(record: Record) {
                Toast.makeText(this@SecondActivity, "${record.record}", Toast.LENGTH_SHORT).show()
            }
        })
        binding.recyclerView.adapter = this.adapter

        adapter.submitList(myRecords)

    }

}



