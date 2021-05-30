package com.example.pokedex.util

import android.content.res.Resources
import com.example.pokedex.R
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
}