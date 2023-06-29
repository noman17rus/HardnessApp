package com.example.hardnessapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true)
@Composable
fun MainScreen() {
    Scaffold {
        LazyColumn(modifier = Modifier.padding(it).fillMaxWidth()) {
            repeat(10) {
                item {
                    Column() {
                        Text(text = "Number")
                        Text(text = "Hardness")
                        Text(text = "Calcium")
                        Text(text = "Magnesium")
                        Text(text = "")
                    }
                }
            }

        }
    }
}