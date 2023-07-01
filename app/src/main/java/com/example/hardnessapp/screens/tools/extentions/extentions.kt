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

fun Sample.getHardnessResultToFloat(): Float {
    val result1 = (2f * this.volumeHardness1 * this.trillon.toFloat() * 1000f) / this.volumeSample
    val result2 = (2f * this.volumeHardness2 * this.trillon.toFloat() * 1000f) / this.volumeSample
    return (result1 + result2) / 2f
}

fun Sample.getCalciumResultSingle(): Float {
    val result1 = (40.08f * this.trillon.toFloat() * this.volumeCalcium1 * 1000) / this.volumeSample
    val result2 = (40.08f * this.trillon.toFloat() * this.volumeCalcium2 * 1000) / this.volumeSample
    return (result1 + result2) / 2f
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

fun getMagnesiumResult(sample: Sample): String {
    val result1 =
        (((sample.volumeHardness1 - sample.volumeCalcium1) * sample.trillon.toFloat() * 24.32 * 1000) / sample.volumeSample).toFloat()
    val result2 =
        (((sample.volumeHardness2 - sample.volumeCalcium2) * sample.trillon.toFloat() * 24.32 * 1000) / sample.volumeSample).toFloat()
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


//расхождение
fun getDiscrepancy(sample: Sample): String {
    val discrepancyHardness: Float = abs(sample.volumeHardness1 - sample.volumeHardness2)
    val discrepancyCalcium: Float = abs(sample.volumeCalcium1 - sample.volumeCalcium2)
    val discrepancyMagnesium: Float = abs(
        ((sample.volumeHardness1 - sample.volumeCalcium1) * sample.trillon.toFloat() * 24.32f * 100f) / sample.volumeSample - ((sample.volumeHardness2 - sample.volumeCalcium2) * sample.trillon.toFloat() * 24.32f * 100f) / sample.volumeSample
    )
    return "Расхождение: \n Жесткость:$discrepancyHardness \n Калций:$discrepancyCalcium \n Магний:$discrepancyMagnesium"
}

fun getStandard(sample: Sample): String {
    val discrepancyHardness: Float = abs(sample.volumeHardness1 - sample.volumeHardness2)
    val discrepancyCalcium: Float = abs(sample.volumeCalcium1 - sample.volumeCalcium2)
    val discrepancyMagnesium: Float = abs(
        ((sample.volumeHardness1 - sample.volumeCalcium1) * sample.trillon.toFloat() * 24.32f * 100f) / sample.volumeSample - ((sample.volumeHardness2 - sample.volumeCalcium2) * sample.trillon.toFloat() * 24.32f * 100f) / sample.volumeSample
    )
    //норматив
    val standardHardness = discrepancyHardness * 0.06f
    var standardCalcium: Float = 0f
        //код ниже больше не работает
    when {
        sample.getCalciumResultSingle() <= 2.0 -> standardCalcium =
            discrepancyCalcium * 0.22f

        sample.getCalciumResultSingle() in 2.0..10.0 -> standardCalcium =
            discrepancyCalcium * 0.14f

        sample.getCalciumResultSingle() >= 10.0 -> standardCalcium =
            discrepancyCalcium * 0.08f
    }
    val standardMagnesium = discrepancyMagnesium * 0.02f
    return "Норматив: \n" +
            " Жесткость:$standardHardness \n" +
            " Калций:$standardCalcium \n" +
            " Магний:$standardMagnesium"
}