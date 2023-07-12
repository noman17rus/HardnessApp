package com.example.hardnessapp.screens.tools.extentions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.screens.SampleViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSample(sample: Sample, viewModel: SampleViewModel) {
    val fontSize = 18.sp
    val padding = 8.dp
    var dialogState by remember { mutableStateOf(false) }
    androidx.compose.material3.Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(0.85f)) {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Номер пробы: ${sample.number}",
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(modifier = Modifier) {
                            val result = Result(sample)
                            when (result.magnesiumResult) {
                                0f -> Text(text = "Жесткость: ${result.hardnessResult.parseResultWithDeltaToString(result.hardnessDelta)}")
                                else -> {
                                    Text(text = "Жесткость: ${result.hardnessResult.parseResultWithDeltaToString(result.hardnessDelta)}")
                                    Text(text = "Кальций: ${result.calciumResult.parseResultWithDeltaToString(result.calciumDelta)}")
                                    Text(text = "Магний: ${result.magnesiumResult.parseResultWithDeltaToString(result.magnesiumDelta)}")
                                }
                            }

                        }

                    }

                }
            }
            Box(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            dialogState = !dialogState
                        }
                        .size(46.dp)
                )
            }
            if (dialogState) {
                AlertDialog(onDismissRequest = { dialogState = false }) {
                    Column(
                        modifier = Modifier.background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(text = "Подтвердить удаление записи?")
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(onClick = {
                                dialogState = false
                            }) {
                                Text(text = "Нет")
                            }
                            Button(onClick = {
                                viewModel.deleteSample(sample = sample)
                            }) {
                                Text(text = "Да")
                            }
                        }
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConformDeleteDialog(navigator: NavHostController, sample: Sample) {

}