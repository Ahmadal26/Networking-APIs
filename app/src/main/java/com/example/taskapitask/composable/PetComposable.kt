package com.example.taskapitask.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.taskapitask.ViewModel.PetsViewModel
import com.example.taskapitask.model.Pet


@Composable
fun PetListScreen(petsViewModel: PetsViewModel = viewModel(), modifier: Modifier = Modifier) {
    val pets = petsViewModel.pets

    LazyColumn(modifier = modifier) {
        items(pets) { pet ->
            PetsItem(pet, petsViewModel)
        }

    }
}


@Composable
fun PetsItem(pet: Pet, petsViewModel: PetsViewModel = viewModel()) {


    Card(
        modifier = Modifier
            .background(Color.DarkGray)
            .padding(20.dp)
            .fillMaxWidth(),


        //.border(width = 10.dp, brush = Brush)
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),

        ) {
        Row(
            Modifier
                .padding(10.dp)
                .height(150.dp),
            verticalAlignment = Alignment.CenterVertically,
        )

        {
            AsyncImage(
                model = pet.image,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
            Column {
                Text(
                    text = "name: " + pet.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " Gender: " + pet.gender,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "  age: ${pet.age}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
            }

        }
        Button(onClick = {
            petsViewModel.deletePet(pet.id)
            petsViewModel.fetchPets()
        }, modifier = Modifier) {
            Text(text = "Delete")

        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPetScreen(petsViewModel: PetsViewModel = viewModel(), back: () -> Unit = {}) {
    // State variables for input fields
    var name by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add a New Pet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            InputField(
                value = name,
                onValueChange = { name = it },
                label = "Name"
            )
            InputField(
                value = age,
                onValueChange = { age = it },
                label = "Age"
            )
            InputField(
                value = gender,
                onValueChange = { gender = it },
                label = "Gender",
                keyboardType = KeyboardType.Number
            )
            InputField(
                value = image,
                onValueChange = { image = it },
                label = "Image URL"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPet = Pet(
                        id = 0,  // Assuming ID is generated by the server
                        name = name,
                        age = age.toInt(),
                        gender = gender,
                        image = image,
                        adopted = toString().toBoolean(),
                    )
                    petsViewModel.addPet(newPet)
                    petsViewModel.fetchPets()
                    back()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Pet")
            }
        }
    }
}


@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}