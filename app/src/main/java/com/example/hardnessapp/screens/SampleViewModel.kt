package com.example.hardnessapp.screens

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hardnessapp.data.Repository
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.screens.tools.extentions.editInputData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleViewModel(private val database: Repository) : ViewModel() {
    private val _number = MutableLiveData("")
    val number: LiveData<String> = _number

    private val _sampleVolume = MutableLiveData("50")
    val sampleVolume: LiveData<String> = _sampleVolume

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

    private val _listOfSamples = MutableLiveData<List<Sample>>()
    val listOfSamples: LiveData<List<Sample>> = _listOfSamples

    private val _sample = MutableLiveData<Sample>()
    val sample: LiveData<Sample> = _sample
    fun createSample(): Sample {
        return Sample(
            number = _number.value ?: "0",
            trillon = _trillon.value ?: "0",
            volumeSample = _sampleVolume.value?.toInt() ?: 50,
            volumeHardness1 = when (_volH1.value) {
                "" -> 0f
                else -> {
                    _volH1.value?.replace(',', '.')?.toFloat() ?: 0f
                }
            },
            volumeHardness2 = when (_volH2.value) {
                "" -> 0f
                else -> {
                    _volH2.value?.replace(',', '.')?.toFloat() ?: 0f
                }
            },
            volumeCalcium1 = when (_volC1.value) {
                "" -> 0f
                else -> {
                    _volC1.value?.replace(',', '.')?.toFloat() ?: 0f
                }
            },
            volumeCalcium2 = when (_volC2.value) {
                "" -> 0f
                else -> {
                    _volC2.value?.replace(',', '.')?.toFloat() ?: 0f
                }
            }
        )
    }
    fun editSampleVolume(volume: String) {
        _sampleVolume.value = when (volume) {
            "" -> "1"
            else -> { volume }
        }
    }
    fun editNumber(string: String) {
        _number.value = string
    }

    fun editTrillon(string: String) {
        _trillon.value = string
    }

    fun editVolumeHardness1(volume: String) {
        _volH1.value = volume.editInputData()
    }

    fun editVolumeHardness2(volume: String) {
        _volH2.value = volume.editInputData()
    }

    fun editVolumeCalcium1(volume: String) {
        _volC1.value = volume.editInputData()
    }

    fun editVolumeCalcium2(volume: String) {
        _volC2.value = volume.editInputData()
    }

    fun readAllSamples() {
        viewModelScope.launch(Dispatchers.Main) {
            _listOfSamples.value = database.readAll()
        }
    }

    fun addSample(sample: Sample) {
        viewModelScope.launch(Dispatchers.IO) {
            database.add(sample) { readAllSamples() }
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
