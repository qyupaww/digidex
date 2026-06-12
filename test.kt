fun getPriorityEvolutions(digimonName: String): List<String> {
    return when(digimonName.lowercase()) {
        "agumon" -> listOf("greymon", "geogreymon", "tyrannomon", "meramon")
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

fun main() {
    val evos = listOf(
        Pair("Bakemon", "url"),
        Pair("Greymon", "url"),
        Pair("Agnimon", "url"),
        Pair("Ankylomon", "url")
    )
    val sorted = sortEvolutions("Agumon", evos)
    for (e in sorted) {
        println(e.first)
    }
}
