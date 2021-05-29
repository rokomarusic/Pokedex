package com.example.pokedex.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
        val height: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val weight: Int, val sprites: Sprites
) : Serializable

data class Sprites(val other: SpritesOthers) : Serializable


data class SpritesOthers(
        @SerializedName("official-artwork")
        @Expose
        val officialArtwork: OfficialArtwork
) : Serializable

data class OfficialArtwork(val front_default: String) : Serializable