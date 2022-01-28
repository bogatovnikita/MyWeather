package com.bogatovnikita.myweather.room

import androidx.room.*

@Dao
interface HistoryWeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryWeatherDao)

    @Delete
    fun delete(entity: HistoryWeatherDao)

    @Update
    fun update(entity: HistoryWeatherDao)

    @Query("SELECT * FROM table_for_history_weather")
    fun getAllHistoryWeather(): List<HistoryWeatherEntity>
}