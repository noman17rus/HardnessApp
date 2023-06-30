package com.example.hardnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.hardnessapp.data.SampleRepository
import com.example.hardnessapp.data.SampleDb
import com.example.hardnessapp.navigation.AppNavHost
import com.example.hardnessapp.screens.AddSampleScreen
import com.example.hardnessapp.screens.MainScreen
import com.example.hardnessapp.screens.SampleViewModel
import com.example.hardnessapp.screens.SampleViewModelFactory
import com.example.hardnessapp.ui.theme.HardnessAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val dao = Room.databaseBuilder(this, SampleDb::class.java, "Samples").build().getDao()
            val dataRoom = SampleRepository(dao = dao)
            val viewModel: SampleViewModel = viewModel(factory = SampleViewModelFactory(dataRoom))
            val navigator = rememberNavController()
            HardnessAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(navigator, viewModel)
                }
            }
        }
    }
}
