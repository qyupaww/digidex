package com.qyupaww.jetpackcomposedigidex.util

import androidx.compose.ui.graphics.Color
import com.qyupaww.jetpackcomposedigidex.data.remote.responses.Type
import com.qyupaww.jetpackcomposedigidex.ui.theme.AttrData
import com.qyupaww.jetpackcomposedigidex.ui.theme.AttrFree
import com.qyupaww.jetpackcomposedigidex.ui.theme.AttrUnknown
import com.qyupaww.jetpackcomposedigidex.ui.theme.AttrVaccine
import com.qyupaww.jetpackcomposedigidex.ui.theme.AttrVirus

fun parseTypeToColor(type: Type): Color {
    val typeName = type.type.lowercase()
    
    return when {
        typeName.contains("dragon") || typeName.contains("reptile") || typeName.contains("dinosaur") -> Color(0xFFE57373) // Red
        typeName.contains("bird") || typeName.contains("avian") -> Color(0xFF64B5F6) // Light Blue
        typeName.contains("beast") || typeName.contains("animal") -> Color(0xFFFFB74D) // Orange
        typeName.contains("plant") || typeName.contains("insect") || typeName.contains("nature") -> Color(0xFF81C784) // Green
        typeName.contains("machine") || typeName.contains("cyborg") || typeName.contains("android") || typeName.contains("mutant") -> Color(0xFF90A4AE) // Blue-Gray
        typeName.contains("angel") || typeName.contains("holy") || typeName.contains("god") -> Color(0xFFFFD54F) // Yellow
        typeName.contains("demon") || typeName.contains("undead") || typeName.contains("ghost") || typeName.contains("evil") -> Color(0xFF9575CD) // Purple
        typeName.contains("aquatic") || typeName.contains("water") || typeName.contains("sea") || typeName.contains("amphibian") || typeName.contains("fish") -> Color(0xFF4FC3F7) // Cyan
        typeName.contains("puppet") || typeName.contains("fairy") -> Color(0xFFF06292) // Pink
        typeName.contains("mollusk") || typeName.contains("slime") || typeName.contains("larva") -> Color(0xFFAED581) // Light Green
        else -> Color(0xFFAAAAAA) // Default Light Grey
    }
}

fun parseAttributeToColor(attributeName: String): Color {
    return when {
        attributeName.lowercase().contains("vaccine") -> AttrVaccine
        attributeName.lowercase().contains("data") -> AttrData
        attributeName.lowercase().contains("virus") -> AttrVirus
        attributeName.lowercase().contains("free") -> AttrFree
        else -> AttrUnknown
    }
}

fun parseEnglishDescription(descriptions: List<com.qyupaww.jetpackcomposedigidex.data.remote.responses.Description>): String {
    val englishDesc = descriptions.find { it.language == "en_us" }?.description
    return englishDesc ?: descriptions.firstOrNull()?.description ?: "No description available."
}

fun getPriorityEvolutions(digimonName: String): List<String> {
    return when(digimonName.lowercase()) {
        // Agumon Line
        "botamon" -> listOf("koromon", "agumon")
        "koromon" -> listOf("agumon", "toyagumon", "agumon (black)")
        "agumon" -> listOf("koromon", "greymon", "geogreymon", "tyrannomon", "meramon")
        "greymon" -> listOf("agumon", "metalgreymon", "metalgreymon (virus)", "skullgreymon")
        "metalgreymon" -> listOf("greymon", "wargreymon", "blitzgreymon")
        "wargreymon" -> listOf("metalgreymon", "omegamon", "omegamon alter-s")
        
        // Gabumon Line
        "punimon" -> listOf("tsunomon", "gabumon")
        "tsunomon" -> listOf("gabumon", "psychemon", "elecmon")
        "gabumon" -> listOf("tsunomon", "garurumon", "garurumon (black)", "gururumon")
        "garurumon" -> listOf("gabumon", "weregarurumon", "weregarurumon (black)")
        "weregarurumon" -> listOf("garurumon", "metalgarurumon", "cresgarurumon")
        "metalgarurumon" -> listOf("weregarurumon", "omegamon")
        
        // Piyomon Line
        "nyokimon" -> listOf("pyocomon", "piyomon", "biyomon")
        "pyocomon" -> listOf("piyomon", "biyomon")
        "piyomon" -> listOf("pyocomon", "birdramon", "aquilamon")
        "biyomon" -> listOf("pyocomon", "birdramon", "aquilamon")
        "birdramon" -> listOf("piyomon", "biyomon", "garudamon")
        "garudamon" -> listOf("birdramon", "hououmon", "phoenixmon")
        
        // Tentomon Line
        "pabumon" -> listOf("motimon", "tentomon")
        "motimon" -> listOf("tentomon", "hagurumon", "kunemon")
        "tentomon" -> listOf("kabuterimon", "kuwagamon")
        "kabuterimon" -> listOf("atlurkabuterimon", "atlurkabuterimon (red)", "megakabuterimon")
        "atlurkabuterimon" -> listOf("heraklekabuterimon", "herculeskabuterimon")
        "megakabuterimon" -> listOf("heraklekabuterimon", "herculeskabuterimon")
        
        // Palmon Line
        "yuramon" -> listOf("tanemon", "palmon")
        "tanemon" -> listOf("palmon", "aruraumon", "floramon")
        "palmon" -> listOf("togemon", "woodmon")
        "togemon" -> listOf("lilimon", "lillymon")
        "lilimon" -> listOf("rosemon", "bancho lillymon")
        "lillymon" -> listOf("rosemon", "bancho lillymon")
        
        // Gomamon Line
        "pichimon" -> listOf("pukamon", "bukamon", "gomamon")
        "pukamon" -> listOf("gomamon", "syakomon")
        "bukamon" -> listOf("gomamon", "syakomon")
        "gomamon" -> listOf("ikkakumon")
        "ikkakumon" -> listOf("zudomon")
        "zudomon" -> listOf("vikemon", "plesiomon")
        
        // Patamon Line
        "poyomon" -> listOf("tokomon", "patamon")
        "tokomon" -> listOf("patamon", "tsukaimon")
        "patamon" -> listOf("angemon", "pegasmon")
        "angemon" -> listOf("holyangemon", "magnaangemon")
        "holyangemon" -> listOf("seraphimon", "godgoldramon")
        "magnaangemon" -> listOf("seraphimon", "godgoldramon")
        
        // Tailmon Line
        "yukimibotamon" -> listOf("nyaromon", "plotmon", "salamon")
        "nyaromon" -> listOf("plotmon", "salamon", "salamandermon")
        "plotmon" -> listOf("tailmon", "gatomon", "blacktailmon")
        "salamon" -> listOf("tailmon", "gatomon", "blacktailmon")
        "tailmon" -> listOf("angewomon", "nefertimon", "silphymon")
        "gatomon" -> listOf("angewomon", "nefertimon", "silphymon")
        "angewomon" -> listOf("holydramon", "magnadramon", "ophanimon")
        
        // V-mon Line
        "chicomon" -> listOf("chibimon", "demiveemon", "v-mon", "veemon")
        "chibimon" -> listOf("v-mon", "veemon")
        "demiveemon" -> listOf("v-mon", "veemon")
        "v-mon" -> listOf("xv-mon", "exveemon", "v-dramon", "fladramon", "lighdramon", "magnamon")
        "veemon" -> listOf("xv-mon", "exveemon", "v-dramon", "fladramon", "lighdramon", "magnamon")
        "xv-mon" -> listOf("paildramon", "dinobeemon")
        "exveemon" -> listOf("paildramon", "dinobeemon")
        "paildramon" -> listOf("imperialdramon", "imperialdramon: dragon mode", "imperialdramon fighter mode")
        
        // Guilmon Line
        "jyarimon" -> listOf("gigimon", "guilmon")
        "gigimon" -> listOf("guilmon")
        "guilmon" -> listOf("growmon", "growlmon")
        "growmon" -> listOf("megalogrowmon", "wargrowlmon")
        "growlmon" -> listOf("megalogrowmon", "wargrowlmon")
        "megalogrowmon" -> listOf("dukemon", "gallantmon", "megidramon")
        "wargrowlmon" -> listOf("dukemon", "gallantmon", "megidramon")
        
        // Terriermon Line
        "zerimon" -> listOf("gummymon", "terriermon")
        "gummymon" -> listOf("terriermon")
        "terriermon" -> listOf("galgomon", "gargomon", "rapidmon (armor)")
        "galgomon" -> listOf("rapidmon")
        "gargomon" -> listOf("rapidmon")
        "rapidmon" -> listOf("saintgalgomon", "megagargomon")
        
        // Renamon Line
        "relemon" -> listOf("pokomon", "viximon", "renamon")
        "pokomon" -> listOf("renamon")
        "viximon" -> listOf("renamon")
        "renamon" -> listOf("kyubimon")
        "kyubimon" -> listOf("taomon")
        "taomon" -> listOf("sakuyamon")
        
        else -> emptyList()
    }
}

fun sortEvolutions(digimonName: String, evolutions: List<Pair<String, String>>): List<Pair<String, String>> {
    val priorities = getPriorityEvolutions(digimonName)
    
    return evolutions.sortedWith { a, b ->
        val nameA = a.first.lowercase()
        val nameB = b.first.lowercase()
        
        val indexA = priorities.indexOf(nameA)
        val indexB = priorities.indexOf(nameB)
        
        if (indexA != -1 && indexB != -1) {
            indexA.compareTo(indexB)
        } else if (indexA != -1) {
            -1
        } else if (indexB != -1) {
            1
        } else {
            val baseParts = digimonName.lowercase().replace("mon", "")
            val hasBaseA = if (baseParts.length > 2) nameA.contains(baseParts) else false
            val hasBaseB = if (baseParts.length > 2) nameB.contains(baseParts) else false
            
            if (hasBaseA && !hasBaseB) -1
            else if (!hasBaseA && hasBaseB) 1
            else nameA.compareTo(nameB)
        }
    }
}