package com.example.pokedex.ui.pokemon

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemEvolutionArrowBinding
import com.example.pokedex.databinding.ItemEvolutionBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.util.Util
import kotlinx.android.synthetic.main.item_evolution_arrow.view.*

class EvolutionAdapter(private val pokemons: MutableList<Pokemon>, private val minlvls: MutableList<Int?>) : RecyclerView.Adapter<EvolutionAdapter.EvolutionViewHolder>() {

    class EvolutionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemEvolutionArrowBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evolution_arrow, parent, false)
        return EvolutionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EvolutionViewHolder, position: Int) {
        val pokemon = pokemons[position]

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, PokemonActivity::class.java)
            intent.putExtra("EXTRA_POKEMON", pokemon)
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.mainEvolutionLayout.imgPokemon.load(pokemon.sprites.other.officialArtwork.front_default) {
            size(
                256
            )
        }
        holder.binding.mainEvolutionLayout.tvPokemonName.text =
            Util.capitalizeFirstLetter(pokemon.name)
        holder.binding.mainEvolutionLayout.tvEvolved.text =
            holder.itemView.resources.getString(Util.getEvolution(position))

        //prvi type slot
        holder.binding.mainEvolutionLayout.type1.tvTypeName.text =
            pokemon.types[0].type.name.toUpperCase()
        holder.binding.mainEvolutionLayout.type1.root.background.setTint(
            ContextCompat.getColor(
                holder.itemView.context,
                Util.getTypeColor(pokemon.types[0].type.name)
            )
        )

        //postavljanje drugog type slota, ukoliko ga pokemon ima
        if (pokemon.types.size == 2) {
            holder.binding.mainEvolutionLayout.type2.tvTypeName.text = pokemon.types[1].type.name.toUpperCase()
            holder.binding.mainEvolutionLayout.type2.root.background.setTint(ContextCompat.getColor(holder.itemView.context, Util.getTypeColor(pokemon.types[1].type.name)))
        } else {
            holder.binding.mainEvolutionLayout.type2.root.visibility = View.GONE
        }

        if (position < minlvls.size) {
            if (minlvls[position] != 0) {
                holder.binding.tvLvl.text = holder.itemView.resources.getString(R.string.lvl, minlvls[position].toString())
            } else {
                holder.binding.tvLvl.text = ""
            }
        }

        if (position == pokemons.size - 1) {
            holder.binding.arrowLayout.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }
}