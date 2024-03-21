package com.spartabasic.www

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // val = value
        // var = variable
        val textView = findViewById<TextView>(R.id.spartaTextView)
        val button = findViewById<Button>(R.id.clickButton)

        button.setOnClickListener {
            textView.text = "Welcome to basic class!"
        }

        var i=0
        while(i<100){
            //textView.text = "$i"
            textView.text = i.toString()
            i+=1 // ++i, i++
        }
    }
}