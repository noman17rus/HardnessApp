package com.example.hardnessapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hardnessapp.data.SampleRepository
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.screens.tools.extentions.editInputData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor (private val repositoryImpl: SampleRepository)  : ViewModel() {

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
                    _volH1.value?.toFloat() ?: 0f
                }
            },
            volumeHardness2 = when (_volH2.value) {
                "" -> 0f
                else -> {
                    _volH2.value?.toFloat() ?: 0f
                }
            },
            volumeCalcium1 = when (_volC1.value) {
                "" -> 0f
                else -> {
                    _volC1.value?.toFloat() ?: 0f
                }
            },
            volumeCalcium2 = when (_volC2.value) {
                "" -> 0f
                else -> {
                    _volC2.value?.toFloat() ?: 0f
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
            _listOfSamples.value = repositoryImpl.readAll()
        }
    }

    fun addSample(sample: Sample) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.add(sample) { readAllSamples() }
        }
    }

    fun deleteSample(sample: Sample) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.delete(sample) { readAllSamples() }
        }
    }

}