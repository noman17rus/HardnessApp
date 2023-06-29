package com.example.hardnessapp.data

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class Repository(private val dao: SampleDao): SampleRepository {
    override suspend fun readAll(): List<Sample> {
        return coroutineScope {
            async {
                dao.getSamples()
            }.await()
        }
    }

    override suspend fun add(sample: Sample, update: () -> Unit) {
        coroutineScope {
            launch {
                dao.addSample(sample)
                update()
            }
        }
    }

    override suspend fun delete(sample: Sample, update: () -> Unit) {
        coroutineScope {
            launch {
                dao.deleteSample(sample)
                update()
            }
        }
    }
}