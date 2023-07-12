package com.example.hardnessapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hardnessapp.data.Sample
import com.example.hardnessapp.screens.tools.extentions.CardSample
import com.example.hardnessapp.screens.tools.extentions.FAB
import com.example.hardnessapp.screens.tools.extentions.Result

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: SampleViewModel, navigator: NavHostController) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val list: MutableState<List<Sample>> = remember { mutableStateOf(listOf()) }
    list.value = viewModel.listOfSamples.observeAsState(listOf()).value.sortedByDescending { it.number }
    viewModel.readAllSamples()

    val sheetContentState: MutableState<Map<String, String>> = remember { mutableStateOf((mapOf())) }

    // App content
    Scaffold(
        floatingActionButton = { FAB(navigator = navigator) },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list.value) {
                Column(
                    modifier = Modifier
                        .clickable {
                            openBottomSheet = !openBottomSheet
                            sheetContentState.value = getListForBoxSheet(it)
                        }
                        .fillMaxWidth()
                )
                {
                    CardSample(sample = it, viewModel = viewModel)
                }
            }

        }
    }

    // Sheet content
    if (openBottomSheet) {
        val windowInsets = WindowInsets(0)
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {
            BoxSheet(map = sheetContentState.value)
            Spacer(modifier = Modifier.size(36.dp))
        }
    }
}

fun getListForBoxSheet(sample: Sample): Map<String, String> {
    val list = mutableMapOf<String, String>()
    val result = Result(sample)
    list["Расхождение"] = result.getDiscrepancy()
    list["Норматив"] = result.getStandard()
    list["Номер"] = sample.number
    return list
}

@Composable
fun BoxSheet(map: Map<String, String>) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Номер пробы: ${map["Номер"] ?: ""}")
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = map["Расхождение"] ?: "")
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = map["Норматив"] ?: "")
            }
        }

    }
}