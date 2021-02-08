package com.infinitumcode.flipcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.infinitumcode.flipcard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = ListAdapter(this)
    }

    override fun onCardClick(pos: Int, data: String) {
        Toast.makeText(this, "Item clicked", Toast.LENGTH_SHORT).show()
    }
}