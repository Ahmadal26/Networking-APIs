package com.example.taskapitask.repo

import com.example.taskapitask.interfaceAPI.PetInterface

class PetaRepository(private val api: PetInterface) {

    suspend fun getAllPets() =api.getAllPets()
}