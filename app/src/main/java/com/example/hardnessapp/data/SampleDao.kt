package com.example.hardnessapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SampleDao {
    @Query("SELECT * FROM Sample")
    fun getSamples(): List<Sample>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSample(sample: Sample)

    @Delete
    fun deleteSample(sample: Sample)
}