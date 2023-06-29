package com.example.hardnessapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun AddSampleScreen() {

    val number = remember { mutableStateOf("") }
    val trillon = remember { mutableStateOf(Float) }
    val volumeH1 = remember { mutableStateOf(Float) }
    val volumeH2 = remember { mutableStateOf(Float) }
    val volumeC1 = remember { mutableStateOf(Float) }
    val volumeC2 = remember { mutableStateOf(Float) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    )
    {
        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = "номер пробы", onValueChange = {}
        )
        OutlinedTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = "0.0100", onValueChange = {}
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
                OutlinedTextField(label = { Text(text = "V1") }, value = "", onValueChange = {})
                OutlinedTextField(label = { Text(text = "V2") }, value = "", onValueChange = {})
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
                OutlinedTextField(label = { Text(text = "V1") }, value = "", onValueChange = {})
                OutlinedTextField(label = { Text(text = "V2") }, value = "", onValueChange = {})
            }
        }
        Text(text = "Жесткость: =")
        Text(text = "Кальций: =")
        Text(text = "Магний: =")
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Text(text = "Отмена")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Text(text = "Сохранить")
            }
        }
    }


}