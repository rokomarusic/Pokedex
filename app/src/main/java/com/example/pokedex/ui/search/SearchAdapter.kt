package com.example.pokedex.ui.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.util.Util
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.paging.PokemonDiffUtil
import com.example.pokedex.ui.pokemon.PokemonActivity
import com.example.pokedex.viewmodel.PokemonViewModel

class SearchAdapter(private val model: PokemonViewModel) :
        PagedListAdapter<Pokemon, SearchAdapter.PokemonSearchViewHolder>(PokemonDiffUtil) {


    class PokemonSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPokemonBinding.bind(view)
        fun bindPost(pokemon: Pokemon, context: Context, model: PokemonViewModel) {
            binding.tvNamePokemon.text = Util.capitalizeFirstLetter(pokemon.name)
            binding.pokemonImg.load(pokemon.sprites.other.officialArtwork.front_default) { size(256) }
            binding.tvOrderPokemon.text = Util.returnId(pokemon.order)

            println("FAVORITO " + model.favourites.value)

            if (model.favourites.value?.let { Util.isFavourite(pokemon, it) } == true) {
                binding.favImg.load(R.drawable.ic_star_1) { size(64) }
            } else {
                binding.favImg.load(R.drawable.ic_star_0) { size(64) }
            }

            binding.root.setOnClickListener {
                val intent = Intent(context, PokemonActivity::class.java)
                intent.putExtra("EXTRA_POKEMON", pokemon)
                context.startActivity(intent)
            }

            binding.favImg.setOnClickListener {
                if (pokemon.isFavourite) {
                    binding.favImg.load(R.drawable.ic_star_0)
                    model.deletePokemon(pokemon, context)
                    model.favourites.value?.remove(pokemon)
                } else {
                    binding.favImg.load(R.drawable.ic_star_1)
                    model.insertPokemon(pokemon, context)
                    model.favourites.value?.add(pokemon)
                }
                pokemon.isFavourite = !pokemon.isFavourite
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonSearchViewHolder, position: Int) {
        Log.i("TAGTAG", getItem(position).toString())
        getItem(position)?.let { holder.bindPost(it, holder.itemView.context, model) }

    }

}