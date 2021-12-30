package com.bogatovnikita.myweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.databinding.ActivityMainBinding
import com.bogatovnikita.myweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_activity_container, MainFragment.newInstance()).commit()
        }
    }
}