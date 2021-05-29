package com.example.pokedex.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonSimple

object PokemonDiffUtil : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.height == newItem.height
                && oldItem.weight == newItem.weight
                && oldItem.id == newItem.id
                && oldItem.order == newItem.order
    }

}
