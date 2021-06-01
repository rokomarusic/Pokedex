package com.example.pokedex.ui.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemEvolutionChainBinding
import com.example.pokedex.models.Pokemon

class EvolutionChainAdapter(val map: Map<Int, MutableList<Pokemon>>, val mapMinLvl: Map<Int, MutableList<Int?>>) : RecyclerView.Adapter<EvolutionChainAdapter.EvolutionChainViewHolder>() {

    class EvolutionChainViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemEvolutionChainBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionChainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evolution_chain, parent, false)
        return EvolutionChainViewHolder(view)
    }

    override fun onBindViewHolder(holder: EvolutionChainViewHolder, position: Int) {
        println("ON POSITION " + map[position])
        holder.binding.evolutionRecycler.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL, false)
        val adapter = map[position]?.let { mapMinLvl[position]?.let { it1 -> EvolutionAdapter(it, it1) } }
        holder.binding.evolutionRecycler.adapter = adapter
    }

    override fun getItemCount(): Int {
        return map.keys.size
    }
}