package com.example.taskapitask.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapitask.interfaceAPI.PetInterface
import com.example.taskapitask.model.Pet
import com.example.taskapitask.repo.PetaRepository
import com.example.taskapitask.singleton.RetrofitHelper
import kotlinx.coroutines.launch

class PetsViewModel : ViewModel() {

    private val petInterface = RetrofitHelper.getInstance().create(PetInterface::class.java)
    private val repository = PetaRepository(petInterface)

    var pets by mutableStateOf(listOf<Pet>())


    init {
        fetchPets()
    }

    fun fetchPets() {
        viewModelScope.launch {

            try {
                pets = repository.getAllPets()


            } catch (e: Exception) {

                println(
                    "Error, can not get pets"
                )
            }
        }
    }

    fun addPet(pet: Pet) {

        viewModelScope.launch {
            try {

                var response = petInterface.addPet(pet)

                if (response.isSuccessful && response.body() != null) {

                    print("Added a pet successfully:)")

                } else {

                    print("can not add pet, check the body well")
                }


            } catch (e: Exception) {
                print("Network issue")
            }
        }
    }
    fun deletePet(petID:Int){
        viewModelScope.launch {
            try {
                var resposnse = petInterface.deletePet(petID)
                print("Pet deleted successfully")
            }catch (e:Exception){
                print("can not delete pet, check the ID well")
            }
        }
    }
}