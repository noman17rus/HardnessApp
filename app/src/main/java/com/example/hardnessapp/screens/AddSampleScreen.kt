package com.example.hardnessapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hardnessapp.navigation.AllScreens
import com.example.hardnessapp.screens.tools.extentions.Result
import com.example.hardnessapp.screens.tools.extentions.isCondition
import com.example.hardnessapp.screens.tools.extentions.parseResultWithDeltaToString
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSampleScreen(viewModel: SampleViewModel, navigator: NavHostController) {
    val context = LocalContext.current
    var number by remember { mutableStateOf("") }
    number = viewModel.number.observeAsState().value.toString()
    var sampleVolume by remember { mutableStateOf("") }
    sampleVolume = viewModel.sampleVolume.observeAsState().value.toString()
    var trillon by remember { mutableStateOf("") }
    trillon = viewModel.trillon.observeAsState().value.toString()
    var volumeH1 by remember { mutableStateOf("") }
    volumeH1 = viewModel.volH1.observeAsState().value.toString()
    var volumeH2 by remember { mutableStateOf("") }
    volumeH2 = viewModel.volH2.observeAsState().value.toString()
    var volumeC1 by remember { mutableStateOf("") }
    volumeC1 = viewModel.volC1.observeAsState().value.toString()
    var volumeC2 by remember { mutableStateOf("") }
    volumeC2 = viewModel.volC2.observeAsState().value.toString()

    var resultHardness by remember { mutableStateOf("") }
    var resultCalcium by remember { mutableStateOf("") }
    var resultMagnesium by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf(false) }

    val sample = viewModel.createSample()
    val result = Result(sample = sample)
    resultHardness = result.hardnessResult.parseResultWithDeltaToString(result.hardnessDelta)
    resultCalcium = result.calciumResult.parseResultWithDeltaToString(result.calciumDelta)
    resultMagnesium = when (result.calciumResult) {
        0f -> 0f.toString()
        else -> result.magnesiumResult.parseResultWithDeltaToString(result.magnesiumDelta)
    }


    condition = result.isCondition()

    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    )
    {
        OutlinedTextField(
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = "Номер пробы") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = number,
            onValueChange = {
                viewModel.editNumber(it)
                isError = false
            },
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = "Объем пробы взятый для анализа") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = sampleVolume, onValueChange = {
                viewModel.editSampleVolume(it)
            }
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = "Концентрация триллона-Б") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = trillon, onValueChange = { viewModel.editTrillon(it) }
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
                    value = volumeH1,
                    onValueChange = {
                        viewModel.editVolumeHardness1(it.replace(',', '.'))
                        try {
                            if(it.toFloat() < 10f) {
                                viewModel.editSampleVolume("100")
                            } else {
                                viewModel.editSampleVolume("50")
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Вdедите корректый параметр", Toast.LENGTH_SHORT).show()
                        }

                    }
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text(text = "V2") },
                    value = volumeH2,
                    onValueChange = { viewModel.editVolumeHardness2(it.replace(',', '.')) })
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
                    value = volumeC1,
                    onValueChange = { viewModel.editVolumeCalcium1(it.replace(',', '.')) })
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "V2") },
                    value = volumeC2,
                    onValueChange = { viewModel.editVolumeCalcium2(it.replace(',', '.')) })
            }
        }


        Text(text = "Жесткость: $resultHardness")
        Text(text = "Кальций: $resultCalcium")
        Text(text = "Магний: $resultMagnesium")
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    navigator.navigate(AllScreens.MainScreen.route)
                }, modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
            ) {
                Text(text = "Отмена")
            }
            Button(
                enabled = condition && !isError,
                onClick = {
                    if (number.isEmpty()) {
                        isError = true
                        Toast.makeText(context, "Введите номер пробы", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addSample(sample = sample)
                        navigator.navigate(route = AllScreens.MainScreen.route)
                        viewModel.editNumber("")
                        viewModel.editNumber("")
                        viewModel.editVolumeHardness1("")
                        viewModel.editVolumeHardness2("")
                        viewModel.editVolumeCalcium1("")
                        viewModel.editVolumeCalcium2("")
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = when (condition) {
                        true -> "Сохранить"
                        false -> "Сходимость не выполнена"
                    }
                )
            }
        }
    }
}
