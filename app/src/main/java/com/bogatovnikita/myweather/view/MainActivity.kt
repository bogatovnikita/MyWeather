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
        pushNotification()
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
                        beginTransaction().add(R.id.main_activity_container, MapsFragment())
                            .addToBackStack("").commit()
                    }
                }
            }
        }
        return true
    }

    companion object {
        private const val NOTIFICATION_ID_1 = 1
        private const val NOTIFICATION_ID_2 = 2
        private const val CHANNEL_ID_1 = " channel_id_1"
        private const val CHANNEL_ID_2 = " channel_id_2"
    }

    private fun pushNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilderFirst = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle(getString(R.string.test_title) + CHANNEL_ID_1)
            setContentText(getString(R.string.test_text) + CHANNEL_ID_1)
            priority = NotificationCompat.PRIORITY_MAX
        }

        val notificationBuilderSecond = NotificationCompat.Builder(this, CHANNEL_ID_2).apply {
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle(getString(R.string.test_title) + CHANNEL_ID_2)
            setContentText(getString(R.string.test_text) + CHANNEL_ID_2)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelNameFirst = (getString(R.string.channel_name) + CHANNEL_ID_1)
            val channelDescriptionFirst =
                (getString(R.string.channel_description_priority_max) + CHANNEL_ID_1)
            val channelPriorityFirst = NotificationManager.IMPORTANCE_HIGH

            val channelFirst =
                NotificationChannel(CHANNEL_ID_1, channelNameFirst, channelPriorityFirst).apply {
                    description = channelDescriptionFirst
                }
            notificationManager.createNotificationChannel(channelFirst)
        }
        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilderFirst.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelNameSecond = (getString(R.string.channel_name) + CHANNEL_ID_2)
            val channelDescriptionSecond =
                (getString(R.string.channel_description_priority_default) + CHANNEL_ID_2)
            val channelPrioritySecond = NotificationManager.IMPORTANCE_DEFAULT

            val channelSecond =
                NotificationChannel(CHANNEL_ID_2, channelNameSecond, channelPrioritySecond).apply {
                    description = channelDescriptionSecond
                }
            notificationManager.createNotificationChannel(channelSecond)
        }
        notificationManager.notify(NOTIFICATION_ID_2, notificationBuilderSecond.build())
    }
}