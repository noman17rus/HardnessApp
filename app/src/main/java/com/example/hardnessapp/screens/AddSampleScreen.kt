package com.example.hardnessapp.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.navigation.AllScreens
import com.example.hardnessapp.screens.tools.extentions.getCalciumResult
import com.example.hardnessapp.screens.tools.extentions.getHardnessResult
import com.example.hardnessapp.screens.tools.extentions.getMagnesiumResult
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSampleScreen(viewModel: SampleViewModel, navigator: NavHostController) {

    var number by remember { mutableStateOf("") }
    number = viewModel.number.observeAsState().value.toString()
    var trillon by remember { mutableStateOf("0.0100") }
    trillon = viewModel.trillon.observeAsState().value.toString()
    val volumeH1 = remember { mutableStateOf("") }
    volumeH1.value = viewModel.volH1.observeAsState().value.toString()
    val volumeH2 = remember { mutableStateOf("") }
    volumeH2.value = viewModel.volH2.observeAsState().value.toString()
    val volumeC1 = remember { mutableStateOf("") }
    volumeC1.value = viewModel.volC1.observeAsState().value.toString()
    val volumeC2 = remember { mutableStateOf("") }
    volumeC2.value = viewModel.volC2.observeAsState().value.toString()
    val resultHardness = remember { mutableStateOf("") }
    val resultCalcium = remember { mutableStateOf("") }
    val resultMagnesium = remember { mutableStateOf("") }
    val condition = remember { mutableStateOf(false) }

    val sample by remember {
        mutableStateOf(Sample())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    )
    {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = "Номер пробы") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = number.value, onValueChange = { viewModel.editNumber(it) },
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = "Концентрация триллона-Б") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = trillon.value, onValueChange = { viewModel.editTrillon(it) }
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(
                    text = "объемы ушедшие на титрование жесткости",
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text(text = "V1") },
                    value = volumeH1.value,
                    onValueChange = { viewModel.editVolumeHardness1(it) })
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text(text = "V2") },
                    value = volumeH2.value,
                    onValueChange = { viewModel.editVolumeHardness2(it) })
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(
                    text = "объемы ушедшие на титрование кальция",
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text(text = "V1") },
                    value = volumeC1.value,
                    onValueChange = { viewModel.editVolumeCalcium1(it) })
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "V2") },
                    value = volumeC2.value,
                    onValueChange = { viewModel.editVolumeCalcium2(it) })
            }
        }

        try {
            val sample = Sample(
                number = number.value,
                trillon = trillon.value,
                volumeHardness1 = volumeH1.value.toFloat(),
                volumeHardness2 = volumeH2.value.toFloat(),
                volumeCalcium1 = volumeC1.value.toFloat(),
                volumeCalcium2 = volumeC2.value.toFloat(),
                resultHardness = resultHardness.value,
                resultCalcium = resultCalcium.value,
                resultMagnesium = resultMagnesium.value
            )
            resultHardness.value = sample.getHardnessResult(trillon.value.toFloat())
            resultCalcium.value =
                sample.getCalciumResult(trillon = trillon.value.toFloat())
            resultMagnesium.value = getMagnesiumResult(sample)
        } catch (e: Exception) {
        }

        Text(text = "Жесткость: ${resultHardness.value}")
        Text(text = "Кальций: ${resultCalcium.value}")
        Text(text = "Магний: ${resultMagnesium.value}")
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                navigator.navigate(AllScreens.MainScreen.route)
            }, modifier = Modifier.weight(1f)) {
                Text(text = "Отмена")
            }

            Button(
                onClick = {
                    viewModel.addSample(
                        sample = Sample(
                            number = number.value,
                            trillon = trillon.value,
                            volumeHardness1 = volumeH1.value.toFloat(),
                            volumeHardness2 = volumeH2.value.toFloat(),
                            volumeCalcium1 = volumeC1.value.toFloat(),
                            volumeCalcium2 = volumeC2.value.toFloat(),
                            resultHardness = resultHardness.value,
                            resultCalcium = resultCalcium.value,
                            resultMagnesium = resultMagnesium.value
                        )
                    )
                    navigator.navigate(route = AllScreens.MainScreen.route)
                },
                modifier = Modifier.weight(1f)
            ) {
                val text:String
                text = when (condition.value) {
                    false -> "не соблюдены условия сходимости"
                    true -> "сохранить"
                }
                Text(text = text)
            }
        }
    }
}
