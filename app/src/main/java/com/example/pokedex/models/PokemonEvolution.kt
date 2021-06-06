package com.example.pokedex.models

import java.io.Serializable

data class PokemonEvolution(val chain: Chain) : Serializable

data class PokemonSpecies(val evolution_chain: SpeciesInfo, val varieties: List<Variety>) : Serializable

data class Variety(val is_default: Boolean, val pokemon: Info) : Serializable

data class SpeciesInfo(val url: String) : Serializable

data class Chain(
        val evolves_to: List<Chain>,
        val species: Info,
        val evolution_details: List<EvolutionDetail>) : Serializable

data class EvolutionDetail(
        val min_level: Int
) : Serializable
