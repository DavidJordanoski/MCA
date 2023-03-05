package com.example.mcareceiptdetails

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mcareceiptdetails.model.ReceiptDetails


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val receiptDetails = ReceiptDetails("",false,0f,0,"")
        val textView = findViewById<TextView>(R.id.tv1)
        receiptDetails.fetchData(textView)
    }
}