package com.example.hardnessapp.screens.tools.extentions

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hardnessapp.navigation.AllScreens

@Composable
fun FAB(navigator: NavHostController) {
    FloatingActionButton(
        onClick = { navigator.navigate(route = AllScreens.AddScreen.route) },
        modifier = Modifier.size(50.dp),
        shape = ShapeDefaults.Medium
    ) {
        Icon(Icons.Default.Add, contentDescription = null)
    }
}