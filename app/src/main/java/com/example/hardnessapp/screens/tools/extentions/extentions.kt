package com.example.hardnessapp.screens.tools.extentions

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.screens.tools.extentions.Result
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.roundToInt


fun Sample.getHardnessResult(trillon: Float): String {
    val result1 = (2f * this.volumeHardness1.toFloat() * trillon * 1000f) / this.volumeSample
    val result2 = (2f * this.volumeHardness2.toFloat() * trillon * 1000f) / this.volumeSample
    val average = (result1 + result2) / 2f
    val delta = (result1 + result2) / 2f * 0.09f
    return getRoundResult(average = average, delta = delta)
}

fun Sample.getHardnessResultToFloat(): Float {
    val result1 =
        (2f * this.volumeHardness1.toFloat() * this.trillon.toFloat() * 1000f) / this.volumeSample
    val result2 =
        (2f * this.volumeHardness2.toFloat() * this.trillon.toFloat() * 1000f) / this.volumeSample
    return (result1 + result2) / 2f
}

fun Sample.getCalciumResultSingle(): Float {
    val result1 =
        (40.08f * this.trillon.toFloat() * this.volumeCalcium1.toFloat() * 1000) / this.volumeSample
    val result2 =
        (40.08f * this.trillon.toFloat() * this.volumeCalcium2.toFloat() * 1000) / this.volumeSample
    return (result1 + result2) / 2f
}

fun Sample.getCalciumResult(trillon: Float): String {
    val result1 = (40.08f * trillon * this.volumeCalcium1.toFloat() * 1000) / this.volumeSample
    val result2 = (40.08f * trillon * this.volumeCalcium2.toFloat() * 1000) / this.volumeSample
    val average = (result1 + result2) / 2f
    Log.d("calc", "$average")
    var delta = 0f
    when {
        average <= 2f -> delta = average * 0.25f
        average in 2f..10f -> delta = average * 0.15f
        average > 10f -> delta = average * 0.11f
    }
    return getRoundResult(average = average, delta = delta)
}

fun getMagnesiumResult(sample: Sample): String {
    val result1 =
        (((sample.volumeHardness1.toFloat() - sample.volumeCalcium1.toFloat()) * sample.trillon.toFloat() * 24.32 * 1000) / sample.volumeSample).toFloat()
    val result2 =
        (((sample.volumeHardness2.toFloat() - sample.volumeCalcium2.toFloat()) * sample.trillon.toFloat() * 24.32 * 1000) / sample.volumeSample).toFloat()
    val average = (result1 + result2) / 2f
    val delta = (result1 + result2) / 2f * 0.02f
    return getRoundResult(average = average, delta = delta)
}


fun getRoundResult(average: Float, delta: Float): String {
    when {
        delta >= 3 -> return "${average.roundToInt()} + ${delta.roundToInt()}"
        delta in 0.3..3.0 -> return "${average.roundDeltaToTenth()} ± ${delta.roundDeltaToTenth()}"
        delta in 0.03..0.3 -> return "${average.roundDeltaToHundredths()} ± ${delta.roundDeltaToHundredths()}"
        delta <= 0.03 -> return "${average.roundDeltaTo1000()} ± ${delta.roundDeltaTo1000()}"
    }
    return ""
}

fun String.editInputData(): String {
    return when (this) {
        "," -> "0.".trim()
        "." -> "0.".trim()
        "-" -> "".trim()
        else -> {
            this
        }
    }
}
