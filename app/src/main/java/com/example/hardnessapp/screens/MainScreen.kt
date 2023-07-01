package com.example.hardnessapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.navigation.AllScreens
import com.example.hardnessapp.screens.tools.extentions.FAB

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: SampleViewModel, navigator: NavHostController) {
    val list: MutableState<List<Sample>> = remember { mutableStateOf(listOf()) }
    list.value = viewModel.listOfSamples.observeAsState(listOf()).value
    viewModel.readAllSamples()
    Scaffold(
        floatingActionButton = { FAB(navigator = navigator) }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            items(list.value) {
                Column(
                    modifier = Modifier
                        .clickable {
                            viewModel.deleteSample(it)
                        }
                ) {
                    Text(text = "Number ${it.number}")
                    Text(text = "Hardness ${it.resultHardness}")
                    Text(text = "Calcium ${it.resultCalcium}")
                    Text(text = "Magnesium ${it.resultMagnesium}")
                    Text(text = "")
                }

            }
        }
    }

}