package com.example.hardnessapp.screens.tools.extentions

import android.util.Log
import com.example.hardnessapp.data.Sample
import kotlin.math.abs
import kotlin.math.roundToInt

class Result(val sample: Sample) {
    //результаты
    val hardnessResult: Float = getAverageResult(
        getHardnessResultSingle(sample.volumeHardness1),
        getHardnessResultSingle(sample.volumeHardness2)
    )
    val calciumResult: Float = getAverageResult(
        getCalciumResultSingle(sample.volumeCalcium1),
        getCalciumResultSingle(sample.volumeCalcium2)
    )
    val magnesiumResult: Float =
        when(calciumResult) {
            0f -> 0f
            else -> { getAverageResult(getMagnesiumResultSingle(sample.volumeHardness1, sample.volumeCalcium1), getMagnesiumResultSingle(sample.volumeHardness2, sample.volumeCalcium2)) }
        }


    //погрешности
    val hardnessDelta = hardnessResult * 0.09f
    val calciumDelta = calciumDelta(calciumResult)
    val magnesiumDelta = magnesiumResult * 0.02f

    //расхождения
    val discrepancyHardness =
        abs(getHardnessResultSingle(sample.volumeHardness1) - getHardnessResultSingle(sample.volumeHardness2))
    val discrepancyCalcium =
        abs(getCalciumResultSingle(sample.volumeCalcium1) - getCalciumResultSingle(sample.volumeCalcium2))
    val discrepancyMagnesium = abs(
        getMagnesiumResultSingle(
            sample.volumeHardness1,
            sample.volumeCalcium1
        ) - getMagnesiumResultSingle(
            sample.volumeHardness2,
            sample.volumeCalcium2
        )
    )
    //нормативы
    val standardHardness = hardnessResult * 0.06f
    val standardCalcium = when {
        calciumResult <= 2.0 -> calciumResult * 0.22f
        calciumResult in 2.0..10.0 -> calciumResult * 0.14f
        calciumResult >= 10.0 -> calciumResult * 0.08f
        else -> {
            0f
        }
    }
    val standardMagnesium = magnesiumResult * 0.02f
    fun getHardnessResultSingle(volume: Float): Float {
        return (2f * volume * sample.trillon.toFloat() * 1000f) / sample.volumeSample
    }

    fun getCalciumResultSingle(volume: Float): Float {
        return (40.08f * sample.trillon.toFloat() * volume * 1000) / sample.volumeSample
    }

    fun getMagnesiumResultSingle(volH: Float, volC: Float): Float {
        return (((volH - volC) * sample.trillon.toFloat() * 24.32f * 1000f) / sample.volumeSample.toFloat())
    }

    fun calciumDelta(result: Float): Float {
        when {
            result <= 2f -> return result * 0.25f
            result in 2f..10f -> return result * 0.15f
            result > 10f -> return result * 0.11f
        }
        return 0f
    }

    //расхождение
    fun getDiscrepancy(): String {
        val discrepancyHardness: Float =
            abs(getHardnessResultSingle(sample.volumeHardness1) - getHardnessResultSingle(sample.volumeHardness2)).roundDeltaToHundredths()
        val discrepancyCalcium: Float =
            abs(getCalciumResultSingle(sample.volumeCalcium1) - getCalciumResultSingle(sample.volumeCalcium2)).roundDeltaToHundredths()
        val discrepancyMagnesium: Float = abs(
            getMagnesiumResultSingle(
                sample.volumeHardness1,
                sample.volumeCalcium1
            ) - getMagnesiumResultSingle(sample.volumeHardness2, sample.volumeCalcium2)
        ).roundDeltaToHundredths()
        return "Расхождение: \n" +
                "Жесткость: $discrepancyHardness \n" +
                "Кальций: $discrepancyCalcium \n" +
                "Магний: $discrepancyMagnesium \n" +
                ""
    }

    //норматив
    fun getStandard(): String {
        //норматив
        val standardHardness = (hardnessResult * 0.06f).roundDeltaToHundredths()
        val standardCalcium = when {
            calciumResult <= 2.0 -> (calciumResult * 0.22f).roundDeltaToHundredths()
            calciumResult in 2.0..10.0 -> (calciumResult * 0.14f).roundDeltaToHundredths()
            calciumResult >= 10.0 -> (calciumResult * 0.08f).roundDeltaToHundredths()
            else -> {
                0f
            }
        }
        val standardMagnesium = (magnesiumResult * 0.02f).roundDeltaToHundredths()
        return "Норматив: \n" +
                "Жесткость: $standardHardness \n" +
                "Кальций: $standardCalcium \n" +
                "Магний: $standardMagnesium \n" +
                ""
    }

}

fun getAverageResult(result1: Float, result2: Float): Float {
    return (result1 + result2) / 2f
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

fun Float.parseResultWithDeltaToString(delta: Float): String {
    when {
        delta >= 3 -> return "${this.roundToInt()} ± ${delta.roundToInt()}"
        delta in 0.3..3.0 -> return "${this.roundDeltaToTenth()} ± ${delta.roundDeltaToTenth()}"
        delta in 0.03..0.3 -> return "${this.roundDeltaToHundredths()} ± ${delta.roundDeltaToHundredths()}"
        delta <= 0.03 -> return "${this.roundDeltaTo1000()} ± ${delta.roundDeltaTo1000()}"
    }
    return ""
}

//условие сходимости
fun Result.isCondition(): Boolean {
    return when (calciumResult) {
        0f -> this.discrepancyHardness <= standardHardness
        else -> {
            this.discrepancyHardness <= standardHardness && discrepancyCalcium <= standardCalcium && discrepancyMagnesium <= standardMagnesium
        }
    }

}

