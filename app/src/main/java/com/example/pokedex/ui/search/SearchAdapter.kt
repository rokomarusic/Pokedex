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
import com.example.pokedex.ui.pokemon.PokemonActivity

class SearchAdapter :
        PagedListAdapter<Pokemon, SearchAdapter.PokemonSearchViewHolder>(PokemonDiffUtil) {


    class PokemonSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPokemonBinding.bind(view)
        fun bindPost(pokemon: Pokemon, context: Context) {
            binding.tvNamePokemon.text = Util.capitalizeFirstLetter(pokemon.name)
            binding.pokemonImg.load(pokemon.sprites.other.officialArtwork.front_default) { size(256) }
            binding.tvOrderPokemon.text = Util.returnId(pokemon.order)

            binding.root.setOnClickListener {
                val intent = Intent(context, PokemonActivity::class.java)
                intent.putExtra("EXTRA_POKEMON", pokemon)
                context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonSearchViewHolder, position: Int) {
        Log.i("TAGTAG", getItem(position).toString())
        getItem(position)?.let { holder.bindPost(it, holder.itemView.context) }

    }

}