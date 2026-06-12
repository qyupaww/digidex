package com.qyupaww.jetpackcomposedigidex.digimondetail

import androidx.lifecycle.ViewModel
import com.qyupaww.jetpackcomposedigidex.data.remote.responses.Digimon
import com.qyupaww.jetpackcomposedigidex.repository.DigimonRepository
import com.qyupaww.jetpackcomposedigidex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DigimonDetailViewModel @Inject constructor(
    private val repository: DigimonRepository
): ViewModel () {

    suspend fun getDigimonInfo(digimonName: String): Resource<Digimon> {
        return repository.getDigimonInfo(digimonName)
    }

}