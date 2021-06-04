package com.example.pokedex.util

import android.content.res.Resources
import com.example.pokedex.R
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonSimple
import com.example.pokedex.models.Stat
import kotlin.math.round

object Util {

    fun returnId(id: Int): String {
        if (id / 10 == 0) {
            return "00$id"
        }

        if (id / 100 == 0) {
            return "0$id"
        }

        return id.toString()
    }

    fun capitalizeFirstLetter(name: String): String {
        return name.substring(0, 1).toUpperCase() + name.substring(1)
    }

    fun getTypeColor(name: String): Int {
        when (name) {
            "grass" -> return R.color.flat_pokemon_type_grass
            "poison" -> return R.color.flat_pokemon_type_poison
            "fire" -> return R.color.flat_pokemon_type_fire
            "water" -> return R.color.flat_pokemon_type_water
            "normal" -> return R.color.flat_pokemon_type_normal
            "fighting" -> return R.color.flat_pokemon_type_fighting
            "flying" -> return R.color.flat_pokemon_type_flying
            "rock" -> return R.color.flat_pokemon_type_rock
            "ground" -> return R.color.flat_pokemon_type_ground
            "bug" -> return R.color.flat_pokemon_type_bug
            "ghost" -> return R.color.flat_pokemon_type_ghost
            "steel" -> return R.color.flat_pokemon_type_steel
            "electric" -> return R.color.flat_pokemon_type_electric
            "psychic" -> return R.color.flat_pokemon_type_psychic
            "ice" -> return R.color.flat_pokemon_type_ice
            "dragon" -> return R.color.flat_pokemon_type_dragon
            "dark" -> return R.color.flat_pokemon_type_dark
            "fairy" -> return R.color.flat_pokemon_type_fairy
        }

        return R.color.design_default_color_error
    }

    fun kgToLbs(kg: Int): Double {
        return round(kg.toFloat() / 10 * 2.20462262 * 10) / 10
    }

    fun heightToImperial(m: Int, resources: Resources): String {
        val feet = m.toFloat() / 0.3048 / 10
        val leftover = (m.toFloat() / 10) % 0.3048
        val inch = leftover * 39.37
        return resources.getString(R.string.height_value, round(feet).toInt().toString(),
                round(inch).toInt().toString(),
                (m.toFloat() / 10).toString())
    }

    fun getStatName(stat: String): Int {
        when (stat) {
            "hp" -> return R.string.hp
            "attack" -> return R.string.attack
            "defense" -> return R.string.defense
            "special-attack" -> return R.string.special_attack
            "special-defense" -> return R.string.special_defense
            "speed" -> return R.string.speed
        }

        return R.string.app_name
    }

    fun getStatColor(stat: String): Int {
        when (stat) {
            "hp" -> return R.color.flat_base_stats_01_hp
            "attack" -> return R.color.flat_base_stats_02_attack
            "defense" -> return R.color.flat_base_stats_03_defense
            "special-attack" -> return R.color.flat_base_stats_04_sp_atk
            "special-defense" -> return R.color.flat_base_stats_05_sp_def
            "speed" -> return R.color.flat_base_stats_06_speed
        }

        return R.color.black
    }

    fun getTotalStat(stats: List<Stat>): Int {
        var sum = 0
        for (stat in stats) {
            sum += stat.base_stat
        }
        return sum
    }

    fun getEvolution(level: Int): Int {
        return if (level == 0) {
            R.string.unevolved
        } else if (level == 1) {
            R.string.evolution1
        } else {
            R.string.evolution2
        }
    }

    fun getGenNumber(generation: String): String {
        val temp = generation.split("-")
        return if (temp.size == 2) {
            temp[1].toUpperCase()
        } else {
            temp[0]
        }
    }

    fun getGenColor(generation: String): Int {
        return when (generation) {
            "generation-i" -> R.color.flat_pokemon_type_grass
            "generation-ii" -> R.color.flat_pokemon_type_bug
            "generation-iii" -> R.color.flat_pokemon_type_undefined
            "generation-iv" -> R.color.flat_pokemon_type_ghost
            "generation-v" -> R.color.flat_pokemon_type_water
            "generation-vi" -> R.color.flat_pokemon_type_fighting
            "generation-vii" -> R.color.flat_pokemon_type_fire
            "generation-viii" -> R.color.flat_pokemon_type_poison
            else -> R.color.black
        }
    }

    fun getCategoryColor(category: String): Int {
        return when (category) {
            "physical" -> R.color.error
            "special" -> R.color.cold_gray
            "status" -> R.color.dark_alpha
            else -> R.color.surface_1
        }
    }

    fun getMoveName(move: String): String {
        if (!move.contains("-")) {
            return move
        }

        val temp = move.split("-")
        return capitalizeFirstLetter(temp[0]) + " " + temp[1]
    }

    fun getPokemonSimple(pokemon: Pokemon, list: List<Pokemon>?): PokemonSimple {
        val name = pokemon.name
        val url = "https://pokeapi.co/api/v2/pokemon/${pokemon.id}/"
        if (list == null) {
            return PokemonSimple(name, url)
        }
        val order = dohvatiBrojFavorita(list)
        return PokemonSimple(name, url, order)
    }

    fun getPokemonSimpleForUpdate(pokemon: Pokemon, list: List<Pokemon>?, order: Int): PokemonSimple {
        val name = pokemon.name
        val url = "https://pokeapi.co/api/v2/pokemon/${pokemon.id}/"
        return PokemonSimple(name, url, order)
    }

    private fun dohvatiBrojFavorita(list: List<Pokemon>): Int {
        var counter = 0
        for (item in list) {
            if (item.isFavourite) {
                counter++
            }
        }
        return counter
    }

    fun isFavourite(pokemon: Pokemon, list: List<Pokemon>): Boolean {
        for (item in list) {
            if (item.id == pokemon.id) {
                return true
            }
        }
        return false
    }
}