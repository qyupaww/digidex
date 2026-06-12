package com.qyupaww.jetpackcomposedigidex.digimonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.qyupaww.jetpackcomposedigidex.data.models.DigidexListEntry
import com.qyupaww.jetpackcomposedigidex.repository.DigimonRepository
import com.qyupaww.jetpackcomposedigidex.util.Constants.PAGE_SIZE
import com.qyupaww.jetpackcomposedigidex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigimonListViewModel @Inject constructor(
    private val repository: DigimonRepository
) : ViewModel() {

    private var curPage = 0
    private var searchJob: kotlinx.coroutines.Job? = null

    var digimonList = mutableStateOf<List<DigidexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    var isSearching = mutableStateOf(false)
    var searchQuery = mutableStateOf("")

    init {
        loadDigimonPaginated()
    }

    fun searchDigimonList(query: String) {
        if (searchQuery.value == query) return
        searchQuery.value = query
        isSearching.value = query.isNotEmpty()
        
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            kotlinx.coroutines.delay(300L)
            
            curPage = 0
            digimonList.value = emptyList()
            endReached.value = false
            loadError.value = ""
            
            loadDigimonPaginated()
        }
    }

    fun loadDigimonPaginated() {
        if (isLoading.value) return
        viewModelScope.launch {
            isLoading.value = true
            val queryParam = if (searchQuery.value.trim().isNotEmpty()) searchQuery.value.trim() else null
            
            val result = repository.getDigimonList(pageSize = PAGE_SIZE, page = curPage, name = queryParam)
            when(result){
                is Resource.Success -> {
                    val elements = result.data?.content ?: emptyList()
                    val totalElements = result.data?.pageable?.totalElements ?: 0
                    
                    endReached.value = (curPage * PAGE_SIZE) + elements.size >= totalElements
                    
                    val digidexEntry = elements.mapIndexed { index, entry ->
                        val url = entry.image
                        DigidexListEntry(entry.name.replaceFirstChar { it.uppercase() }, url, entry.id)
                    }
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    digimonList.value += digidexEntry
                }
                is Resource.Error -> {
                    if (queryParam != null && (result.message?.contains("404") == true || result.message?.contains("An unknown error") == true)) {
                        endReached.value = true
                        loadError.value = ""
                    } else {
                        loadError.value = result.message!!
                    }
                    isLoading.value = false
                }
                else -> {

                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit){
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}