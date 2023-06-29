package com.example.hardnessapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Sample::class], version = 1)
abstract class SampleDb : RoomDatabase( ){
    abstract fun getDao(): SampleDao
}