package com.spartabasic.www

import android.os.Build
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

        // enum
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Toast.makeText(
                this,
                "Test : " + intent.getSerializableExtra("test", AnswerType::class.java),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Test : " + intent.getSerializableExtra("test"),
                Toast.LENGTH_SHORT
            ).show()
        }

        adapter = RecordRecyclerViewAdapter(recordClickListener = object : RecordClickListener {
            override fun onClickItem(record: Record) {
                // enum
                Toast.makeText(this@SecondActivity, "${record.isCorrect.text}", Toast.LENGTH_SHORT)
                    .show()

                // sealed
                /*when (val isCorrect = record.isCorrect) {
                    is AnotherAnswerType.Correct -> Toast.makeText(
                        this@SecondActivity,
                        isCorrect.text, Toast.LENGTH_SHORT
                    ).show()

                    is AnotherAnswerType.Wrong -> Toast.makeText(
                        this@SecondActivity,
                        "${isCorrect.text}, You are stupid, and stupid level: ${isCorrect.stupidLevel}",
                        Toast.LENGTH_SHORT
                    ).show()
                }*/
            }
        })
        adapter.setHasStableIds(true)
        binding.recyclerView.adapter = this.adapter

        adapter.submitList(myRecords)

    }

}



