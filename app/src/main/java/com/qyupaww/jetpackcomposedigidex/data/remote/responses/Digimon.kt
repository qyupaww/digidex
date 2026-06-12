package com.qyupaww.jetpackcomposedigidex.data.remote.responses

data class Digimon(
    val attributes: List<Attribute>?,
    val descriptions: List<Description>?,
    val fields: List<Field>?,
    val id: Int,
    val images: List<Image>?,
    val levels: List<Level>?,
    val name: String,
    val nextEvolutions: List<NextEvolution>?,
    val priorEvolutions: List<PriorEvolution>?,
    val releaseDate: String?,
    val skills: List<Skill>?,
    val types: List<Type>?,
    val xAntibody: Boolean
)