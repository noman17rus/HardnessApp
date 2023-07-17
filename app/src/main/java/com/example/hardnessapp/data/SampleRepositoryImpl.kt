package com.example.hardnessapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SampleRepositoryImpl(private val dao: SampleDao): SampleRepository {
    override suspend fun readAll(): List<Sample> {
        return coroutineScope {
            async(Dispatchers.IO) {
                dao.getSamples()
            }.await()
        }
    }

    override suspend fun add(sample: Sample, update: () -> Unit) {
        coroutineScope {
            launch(Dispatchers.IO) {
                dao.addSample(sample)
                update()
            }
        }
    }

    override suspend fun delete(sample: Sample, update: () -> Unit) {
        coroutineScope {
            launch(Dispatchers.IO) {
                dao.deleteSample(sample)
                update()
            }
        }
    }
}