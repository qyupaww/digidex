package com.qyupaww.jetpackcomposedigidex.repository

import com.qyupaww.jetpackcomposedigidex.data.remote.DigimonApi
import com.qyupaww.jetpackcomposedigidex.data.remote.responses.Digimon
import com.qyupaww.jetpackcomposedigidex.data.remote.responses.DigimonList
import com.qyupaww.jetpackcomposedigidex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class DigimonRepository @Inject constructor(
    private val api: DigimonApi,
    private val dao: com.qyupaww.jetpackcomposedigidex.data.local.DigimonDao
) {
    suspend fun getDigimonList(pageSize: Int, page: Int, name: String? = null): Resource<DigimonList> {
        return try {
            val response = api.getDigimonList(pageSize, page, name)
            
            // Save to local database
            val entities = response.content.map { 
                com.qyupaww.jetpackcomposedigidex.data.local.DigimonEntity(id = it.id, name = it.name, imageUrl = it.image)
            }
            dao.insertDigimons(entities)
            
            Resource.Success(response)
        } catch (e: Exception) {
            // Fallback to local database
            val query = name ?: ""
            val offset = page * pageSize
            val localDigimons = dao.getDigimons(limit = pageSize, offset = offset, searchQuery = query)
            val count = dao.getDigimonCount(query)
            
            if (localDigimons.isNotEmpty()) {
                val contentList = localDigimons.map {
                    com.qyupaww.jetpackcomposedigidex.data.remote.responses.Content(
                        href = "", 
                        id = it.id, 
                        image = it.imageUrl, 
                        name = it.name
                    )
                }
                
                val pageable = com.qyupaww.jetpackcomposedigidex.data.remote.responses.Pageable(
                    currentPage = page,
                    elementsOnPage = pageSize,
                    nextPage = "",
                    previousPage = "",
                    totalElements = count,
                    totalPages = (count / pageSize) + 1
                )
                
                val localResponse = DigimonList(content = contentList, pageable = pageable)
                Resource.Success(localResponse)
            } else {
                Resource.Error(message = "An error occurred and no local data is available.")
            }
        }
    }

    suspend fun getDigimonInfo(digimonName: String): Resource<Digimon> {
        return try {
            val response = api.getDigimonInfo(digimonName)
            
            val gson = com.google.gson.Gson()
            val jsonString = gson.toJson(response)
            val detailEntity = com.qyupaww.jetpackcomposedigidex.data.local.DigimonDetailEntity(
                name = digimonName,
                jsonString = jsonString
            )
            dao.insertDigimonDetail(detailEntity)
            
            Resource.Success(response)
        } catch (e: Exception) {

            val localDetail = dao.getDigimonDetail(digimonName)
            if (localDetail != null) {
                val gson = com.google.gson.Gson()
                val digimon = gson.fromJson(localDetail.jsonString, Digimon::class.java)
                Resource.Success(digimon)
            } else {
                Resource.Error(message = "An error occurred and no local data is available.")
            }
        }
    }
}
