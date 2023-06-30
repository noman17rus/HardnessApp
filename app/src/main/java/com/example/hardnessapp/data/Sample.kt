package com.example.hardnessapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sample(
  @PrimaryKey(autoGenerate = false)
  val number: String,
  @ColumnInfo
  val trillon: String,
  @ColumnInfo
  val volumeSample: Int = 50,
  @ColumnInfo
  val volumeHardness1: Float,
  @ColumnInfo
  val volumeHardness2: Float,
  @ColumnInfo
  val volumeCalcium1: Float,
  @ColumnInfo
  val volumeCalcium2: Float,
  @ColumnInfo
  val resultHardness: String,
  @ColumnInfo
  val resultCalcium: String,
  @ColumnInfo
  val resultMagnesium: String,
)