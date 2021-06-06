package com.example.pokedex.ui.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemAbilityBinding
import com.example.pokedex.models.Ability
import com.example.pokedex.util.Util

class AbilityAdapter(private val abilities: MutableList<Ability>) : RecyclerView.Adapter<AbilityAdapter.AbilityViewHolder>() {

    class AbilityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAbilityBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ability, parent, false)
        return AbilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        val ability = abilities[position]
        holder.binding.tvAbilityName.text = Util.capitalizeFirstLetter(ability.ability.name)
        if (ability.is_hidden) {
            holder.binding.tvHiddenAbility.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return abilities.size
    }
}