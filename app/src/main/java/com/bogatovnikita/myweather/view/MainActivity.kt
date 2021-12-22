package com.bogatovnikita.myweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}