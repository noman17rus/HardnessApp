package com.example.hardnessapp.screens.tools.extentions

import android.util.Log
import com.example.hardnessapp.data.Sample
import kotlin.math.abs
import kotlin.math.roundToInt


fun Sample.getHardnessResult(trillon: Float): String {
    val result1 = (2f * this.volumeHardness1 * trillon * 1000f) / this.volumeSample
    val result2 = (2f * this.volumeHardness2 * trillon * 1000f) / this.volumeSample
    val average = (result1 + result2) / 2f
    val delta = (result1 + result2) / 2f * 0.09f
    return getRoundResult(average = average, delta = delta)
}

fun Sample.getCalciumResult(trillon: Float): String {
    val result1 = (40.08f * trillon * this.volumeCalcium1 * 1000) / this.volumeSample
    val result2 = (40.08f * trillon * this.volumeCalcium2 * 1000) / this.volumeSample
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

fun getMagnesiumResult(sample: Sample, trillon: Float): String {
    val result1 =
        (((sample.volumeHardness1 - sample.volumeCalcium1) * trillon * 24.32 * 1000) / sample.volumeSample).toFloat()
    val result2 =
        (((sample.volumeHardness2 - sample.volumeCalcium2) * trillon * 24.32 * 1000) / sample.volumeSample).toFloat()
    val average = (result1 + result2) / 2f
    val delta = (result1 + result2) / 2f * 0.02f
    return getRoundResult(average = average, delta = delta)
}

fun Float.roundDeltaToTenth(): Float {
    return ((this * 10f).roundToInt() / 10f)
}

fun Float.roundDeltaToHundredths(): Float {
    return ((this * 100f).roundToInt() / 100f)
}

fun Float.roundDeltaTo1000(): Float {
    return ((this * 1000f).roundToInt() / 1000f)
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

//условие сходимости
fun Sample.condition(trillon: Float): Boolean {
    //расхождения
    val discrepancyHardness: Float = abs(this.volumeHardness1 - this.volumeHardness2)
    val discrepancyCalcium: Float = abs(this.volumeCalcium1 - this.volumeCalcium2)
    val discrepancyMagnesium: Float = abs(
        getMagnesiumResult(
            this,
            trillon = trillon
        ).toFloat() -
                getMagnesiumResult(
                    this,
                    trillon = trillon
                ).toFloat()
    )
    //норматив
    val standardHardness = discrepancyHardness * 0.06f
    var standardCalcium: Float = 0f
    when {
        this.getCalciumResult(trillon = trillon).toFloat() <= 2.0 -> standardCalcium =
            discrepancyCalcium * 0.22f

        this.getCalciumResult(trillon = trillon).toFloat() in 2.0..10.0 -> standardCalcium =
            discrepancyCalcium * 0.14f

        this.getCalciumResult(trillon = trillon).toFloat() >= 10.0 -> standardCalcium =
            discrepancyCalcium * 0.08f
    }
    val standardMagnesium = discrepancyMagnesium * 0.02f
    Log.d(
        "hello",
        "$discrepancyHardness <= standardHardness && discrepancyCalcium <= standardCalcium && discrepancyMagnesium <= standardMagnesium"
    )
    return discrepancyHardness <= standardHardness && discrepancyCalcium <= standardCalcium && discrepancyMagnesium <= standardMagnesium
}