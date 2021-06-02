package com.example.pokedex.models

import java.io.Serializable

data class PokemonType(val damage_relations: DamageRelations, val moves: List<Info>, val name: String) : Serializable

data class PokemonMove(val generation: Info, val damage_class: Info?, val name: String, val power: Int, val pp: Int) : Serializable

data class DamageRelations(
        val double_damage_from: List<Info>,
        val double_damage_to: List<Info>,
        val half_damage_from: List<Info>,
        val half_damage_to: List<Info>,
        val no_damage_from: List<Info>,
        val no_damage_to: List<Info>
) : Serializable

