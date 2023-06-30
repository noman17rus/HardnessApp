package com.example.hardnessapp.data

interface Repository {
    suspend fun readAll(): List<Sample>
    suspend fun add(sample: Sample, update: () -> Unit)
    suspend fun delete(sample: Sample, update: () -> Unit)
}