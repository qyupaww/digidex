package com.qyupaww.jetpackcomposedigidex.digimondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.qyupaww.jetpackcomposedigidex.data.remote.responses.Digimon
import com.qyupaww.jetpackcomposedigidex.util.Resource
import com.qyupaww.jetpackcomposedigidex.util.neoBrutalismStyle

@Composable
fun DigimonDetailScreen(
    dominantColor: Color,
    digimonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    digimonImageSize: Dp = 200.dp,
    viewModel: DigimonDetailViewModel = hiltViewModel()
){
    val digimonInfo = produceState<Resource<Digimon>>(initialValue = Resource.Loading()) {
        value = viewModel.getDigimonInfo(digimonName)
    }.value
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(bottom = 16.dp)
    ) {
        DigimonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        DigimonDetailStateWrapper(
            digimonInfo = digimonInfo,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + digimonImageSize / 2f, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .neoBrutalismStyle(
                    borderWidth = 1.dp,
                    offsetX = 4.dp,
                    offsetY = 4.dp,
                    cornerRadius = 10.dp
                )
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + digimonImageSize / 2f, start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            if(digimonInfo is Resource.Success) {
                digimonInfo.data?.images?.firstOrNull()?.href?.let { imageUrl ->
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = digimonInfo.data.name ?: "",
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.scale(0.5f)
                            )
                        },
                        modifier = Modifier
                            .size(digimonImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DigimonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
){
    Box (
        contentAlignment = Alignment.TopStart,
        modifier = modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .offset(16.dp, 16.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.2f))
                .clickable {
                    navController.popBackStack()
                }
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun DigimonDetailStateWrapper(
    digimonInfo: Resource<Digimon>,
    navController: NavController,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
){
    when(digimonInfo) {
        is Resource.Success -> {
            DigimonDetailSection(
                digimonInfo = digimonInfo.data!!,
                navController = navController,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }
        is Resource.Error -> {
            Text(
                text = digimonInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun DigimonDetailSection(
    digimonInfo: Digimon,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${digimonInfo.id} ${digimonInfo.name.replaceFirstChar { it.uppercase() }}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
        
        @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
        androidx.compose.foundation.layout.FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            val types = digimonInfo.types ?: emptyList()
            if (types.isNotEmpty()) {
                DigimonTypeSection(types = types)
            }
            
            val attributes = digimonInfo.attributes ?: emptyList()
            if (attributes.isNotEmpty()) {
                DigimonAttributeSection(attributes = attributes)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        // Basic Info Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DigimonInfoItem(
                title = "Level",
                value = digimonInfo.levels?.firstOrNull()?.level ?: "Unknown"
            )
            val releaseDate = digimonInfo.releaseDate ?: ""
            DigimonInfoItem(
                title = "Debut Year",
                value = releaseDate.ifEmpty { "Unknown" }
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Lore / Description
        val descriptions = digimonInfo.descriptions ?: emptyList()
        if (descriptions.isNotEmpty()) {
            DigimonLoreSection(descriptions = descriptions)
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        // Skills
        val skills = digimonInfo.skills ?: emptyList()
        if (skills.isNotEmpty()) {
            DigimonSkillsSection(skills = skills)
        }
        
        // Evolutions
        val priorEvolutions = digimonInfo.priorEvolutions ?: emptyList()
        if (priorEvolutions.isNotEmpty()) {
            DigimonEvolutionSection(
                title = "Evolves From",
                evolutions = com.qyupaww.jetpackcomposedigidex.util.sortEvolutions(
                    digimonInfo.name, 
                    priorEvolutions.map { Pair(it.digimon, it.image) }
                ),
                navController = navController
            )
        }
        
        val nextEvolutions = digimonInfo.nextEvolutions ?: emptyList()
        if (nextEvolutions.isNotEmpty()) {
            DigimonEvolutionSection(
                title = "Evolves To",
                evolutions = com.qyupaww.jetpackcomposedigidex.util.sortEvolutions(
                    digimonInfo.name,
                    nextEvolutions.map { Pair(it.digimon, it.image) }
                ),
                navController = navController
            )
        }
        
        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun DigimonTypeSection(types: List<com.qyupaww.jetpackcomposedigidex.data.remote.responses.Type>) {
    for(type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .neoBrutalismStyle(
                        borderWidth = 1.dp,
                        offsetX = 2.dp,
                        offsetY = 2.dp,
                        cornerRadius = 50.dp 
                    )
                    .background(com.qyupaww.jetpackcomposedigidex.util.parseTypeToColor(type), CircleShape)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = type.type.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
}

@Composable
fun DigimonAttributeSection(attributes: List<com.qyupaww.jetpackcomposedigidex.data.remote.responses.Attribute>) {
    for(attr in attributes) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .neoBrutalismStyle(
                        borderWidth = 1.dp,
                        offsetX = 2.dp,
                        offsetY = 2.dp,
                        cornerRadius = 50.dp // To match CircleShape
                    )
                    .background(com.qyupaww.jetpackcomposedigidex.util.parseAttributeToColor(attr.attribute), CircleShape)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = attr.attribute.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
}

@Composable
fun DigimonInfoItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun DigimonLoreSection(descriptions: List<com.qyupaww.jetpackcomposedigidex.data.remote.responses.Description>) {
    val loreText = com.qyupaww.jetpackcomposedigidex.util.parseEnglishDescription(descriptions)
    
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(
            text = "Lore",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = loreText,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
            textAlign = TextAlign.Justify,
            lineHeight = 24.sp
        )
    }
}

@Composable
fun DigimonSkillsSection(skills: List<com.qyupaww.jetpackcomposedigidex.data.remote.responses.Skill>) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(
            text = "Signature Skills",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        for (skill in skills) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .neoBrutalismStyle(
                    borderWidth = 1.dp,
                    offsetX = 3.dp,
                    offsetY = 3.dp,
                    cornerRadius = 8.dp
                )
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(12.dp)
            ) {
                Text(
                    text = skill.skill,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                if (skill.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = skill.description,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.8f),
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DigimonEvolutionSection(
    title: String,
    evolutions: List<Pair<String, String>>,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(evolutions.size) { index ->
                val evolution = evolutions[index]
                val digimonName = evolution.first
                val imageUrl = evolution.second
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {

                            val defaultColorArgb = Color.White.toArgb()
                            navController.navigate("digimon_detail_screen/${defaultColorArgb}/${digimonName}")
                        }
                        .padding(8.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = digimonName,
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.scale(0.5f)
                            )
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.2f))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = digimonName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.8f) 
                    )
                }
            }
        }
    }
}
