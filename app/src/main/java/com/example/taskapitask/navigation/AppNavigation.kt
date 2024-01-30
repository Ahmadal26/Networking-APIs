package com.example.taskapitask.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskapitask.ViewModel.PetsViewModel
import com.example.taskapitask.composable.AddPetScreen
import com.example.taskapitask.composable.PetListScreen


    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun AppContent() {
        val petViewModel: PetsViewModel = viewModel()
        val navController = rememberNavController()

        Scaffold(
            topBar = { TopAppBar(title = { Text("My pet List") }) }
            ,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate("addPet")
                }) {
                    Text("+")
                }
            }
        ) { padding ->
            NavHost(navController = navController, startDestination = "PetListScreen") {
                composable("PetListScreen") {

                    PetListScreen(petsViewModel = petViewModel, modifier = Modifier.padding(padding))
                }
                composable("addPet") {
                    AddPetScreen(petViewModel, back = {
                        navController.popBackStack()
                    })




                }
            }
        }
    }
