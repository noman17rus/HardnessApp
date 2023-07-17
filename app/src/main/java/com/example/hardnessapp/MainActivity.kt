package com.example.hardnessapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.hardnessapp.data.SampleDb
import com.example.hardnessapp.data.SampleRepositoryImpl
import com.example.hardnessapp.navigation.AppNavHost
import com.example.hardnessapp.screens.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SampleViewModel by viewModels()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {



            val dao = Room.databaseBuilder(this, SampleDb::class.java, "Samples").build().getDao()
            val dataRoom = SampleRepositoryImpl(dao = dao)
//            val viewModel: SampleViewModel = viewModel(factory = SampleViewModelFactory(dataRoom))
            val navigator = rememberNavController()
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
