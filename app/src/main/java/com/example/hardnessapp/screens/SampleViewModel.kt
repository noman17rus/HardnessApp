package com.example.hardnessapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hardnessapp.data.Repository
import com.example.hardnessapp.data.SampleRepository
import com.example.hardnessapp.data.Sample
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SampleViewModel(private val database: Repository) : ViewModel() {
    private val _number = MutableLiveData("")
    val number: LiveData<String> = _number

    private val _trillon = MutableLiveData("0.0100")
    val trillon: LiveData<String> = _trillon

    private val _volH1 = MutableLiveData("")
    val volH1: LiveData<String> = _volH1

    private val _volH2 = MutableLiveData("")
    val volH2: LiveData<String> = _volH2

    private val _volC1 = MutableLiveData("")
    val volC1: LiveData<String> = _volC1

    private val _volC2 = MutableLiveData("")
    val volC2: LiveData<String> = _volC2

    private val _condition = MutableLiveData(false)
    val condition:LiveData<Boolean> = _condition

    private val _listOfSamples = MutableLiveData<List<Sample>>()
    val listOfSamples: LiveData<List<Sample>> = _listOfSamples

    fun editNumber(string: String) {
        _number.value = string
    }

    fun editTrillon(string: String) {
        _trillon.value = string
    }

    fun editVolumeHardness1(volume: String) {
        _volH1.value = volume
    }

    fun editVolumeHardness2(volume: String) {
        _volH2.value = volume
    }

    fun editVolumeCalcium1(volume: String) {
        _volC1.value = volume
    }

    fun editVolumeCalcium2(volume: String) {
        _volC2.value = volume
    }

    fun editCondition(condition: Boolean) {
        _condition.value = condition
    }

    fun readAllSamples() {
        viewModelScope.launch(Dispatchers.Main) {
            _listOfSamples.value = database.readAll()
        }
    }

    fun addSample(sample: Sample) {
        viewModelScope.launch(Dispatchers.IO) {
            database.add(sample) { readAllSamples()  }
        }
    }
    fun deleteSample(sample: Sample) {
        viewModelScope.launch(Dispatchers.IO) {
            database.delete(sample) { readAllSamples() }
        }
    }

}

class SampleViewModelFactory(private val _database: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SampleViewModel(database = _database) as T
    }
}