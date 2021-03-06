package com.bogatovnikita.myweather.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.bogatovnikita.myweather.R
import com.bogatovnikita.myweather.contentProvider.ContentProviderContactsFragment
import com.bogatovnikita.myweather.databinding.ActivityMainBinding
import com.bogatovnikita.myweather.googleMaps.MapsFragment
import com.bogatovnikita.myweather.view.history.HistoryFragment
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_history -> {
                val fragmentHistory = supportFragmentManager.findFragmentByTag("history_fragment")
                if (fragmentHistory == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.main_activity_container,
                                HistoryFragment.newInstance(),
                                "history_fragment"
                            )
                            .addToBackStack("").commit()
                    }
                }
            }

            R.id.menu_contacts -> {
                val fragmentContacts = supportFragmentManager.findFragmentByTag("contacts_fragment")
                if (fragmentContacts == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.main_activity_container,
                                ContentProviderContactsFragment.newInstance(), "contacts_fragment"
                            )
                            .addToBackStack("").commit()
                    }
                }
            }

            R.id.menu_google_maps -> {
                val fragmentMaps = supportFragmentManager.findFragmentByTag("maps_fragments")
                if (fragmentMaps == null) {
                    supportFragmentManager.apply {
                        beginTransaction().replace(R.id.main_activity_container, MapsFragment(),"maps_fragments")
                            .addToBackStack("").commit()
                    }
                }
            }
        }
        return true
    }
}