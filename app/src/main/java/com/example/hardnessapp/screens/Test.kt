package com.example.hardnessapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.screens.tools.extentions.FAB
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test(viewModel: SampleViewModel, navigator: NavHostController) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(SheetState(false))
    val list: MutableState<List<Sample>> = remember { mutableStateOf(listOf()) }
    list.value = viewModel.listOfSamples.observeAsState(listOf()).value
    viewModel.readAllSamples()
    Scaffold(floatingActionButton = { FAB(navigator = navigator) }) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 128.dp,
            sheetContent = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(20.dp))
                }
            }) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(list.value) {
                        Column(
                            modifier = Modifier
                                .clickable {
                                    scope.launch { scaffoldState.bottomSheetState.expand() }
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
    }

}