package com.example.pokedex.type.ui.main

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.core.graphics.drawable.updateBounds
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDamageOverviewBinding
import com.example.pokedex.models.Info
import com.example.pokedex.models.PokemonType
import com.example.pokedex.type.TypeAdapter
import com.example.pokedex.util.Util


class DamageOverviewFragment(val type: PokemonType) : Fragment() {

    private var _binding: FragmentDamageOverviewBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDamageOverviewBinding.inflate(inflater, container, false)

        setupOffensiveLayout()
        setupDefensiveLayout()

        return binding.root
    }

    private fun setupOffensiveLayout() {
        binding.offensiveLayout.tvOffDef.text = getString(R.string.offensive)
        binding.offensiveLayout.layoutDoubleDamage.gwtype.layoutManager = GridLayoutManager(this.context, 3)
        binding.offensiveLayout.layoutDoubleDamage.root.background.alpha = 25
        this.context?.let { ContextCompat.getColor(it, R.color.success) }?.let { binding.offensiveLayout.layoutDoubleDamage.root.background.setTint(it) }
        val doubleDamageAdapter = TypeAdapter(type.damage_relations.double_damage_to as MutableList<Info>)
        binding.offensiveLayout.layoutDoubleDamage.gwtype.adapter = doubleDamageAdapter
        binding.offensiveLayout.layoutDoubleDamage.tvDmgValue.text = resources.getString(R.string.double_damage)
        binding.offensiveLayout.layoutDoubleDamage.tvDmgValue.setTextColor(resources.getColor(R.color.success, this.context?.theme))
        binding.offensiveLayout.layoutHalfDamage.gwtype.layoutManager = GridLayoutManager(this.context, 3)
        /*val sd = ShapeDrawable(RectShape())
        sd.paint.color = resources.getColor(R.color.success)
        sd.paint.style = Paint.Style.STROKE
        sd.paint.strokeWidth = 8.0F
        binding.offensiveLayout.layoutHalfDamage.root.background = sd*/
        binding.offensiveLayout.layoutHalfDamage.root.background.alpha = 25
        if (type.damage_relations.double_damage_to.isEmpty()) {
            binding.offensiveLayout.layoutDoubleDamage.tvNone.visibility = View.VISIBLE
        }


        this.context?.let { ContextCompat.getColor(it, R.color.error) }?.let { binding.offensiveLayout.layoutHalfDamage.root.background.setTint(it) }
        val halfDamageAdapter = TypeAdapter(type.damage_relations.half_damage_to as MutableList<Info>)
        binding.offensiveLayout.layoutHalfDamage.gwtype.adapter = halfDamageAdapter
        binding.offensiveLayout.layoutHalfDamage.tvDmgValue.text = resources.getString(R.string.half_damage)
        binding.offensiveLayout.layoutHalfDamage.tvDmgValue.setTextColor(resources.getColor(R.color.error, this.context?.theme))
        if (type.damage_relations.half_damage_to.isEmpty()) {
            binding.offensiveLayout.layoutHalfDamage.tvNone.visibility = View.VISIBLE
        }

        binding.offensiveLayout.layoutNoDamage.gwtype.layoutManager = GridLayoutManager(this.context, 3)
        this.context?.let { ContextCompat.getColor(it, R.color.surface_2) }?.let { binding.offensiveLayout.layoutNoDamage.root.background.setTint(it) }
        val noDamageAdapter = TypeAdapter(type.damage_relations.no_damage_to as MutableList<Info>)
        binding.offensiveLayout.layoutNoDamage.gwtype.adapter = noDamageAdapter
        binding.offensiveLayout.layoutNoDamage.tvDmgValue.text = resources.getString(R.string.no_damage)
        binding.offensiveLayout.layoutNoDamage.tvDmgValue.setTextColor(resources.getColor(R.color.cold_gray, this.context?.theme))
        if (type.damage_relations.no_damage_to.isEmpty()) {
            binding.offensiveLayout.layoutNoDamage.tvNone.visibility = View.VISIBLE
        }

    }

    fun setupDefensiveLayout() {
        binding.defensiveLayout.layoutDoubleDamage.gwtype.layoutManager = GridLayoutManager(this.context, 3)
        binding.defensiveLayout.layoutDoubleDamage.root.background.alpha = 25
        this.context?.let { ContextCompat.getColor(it, R.color.success) }?.let { binding.defensiveLayout.layoutDoubleDamage.root.background.setTint(it) }
        val doubleDamageAdapter = TypeAdapter(type.damage_relations.double_damage_from as MutableList<Info>)
        binding.defensiveLayout.layoutDoubleDamage.gwtype.adapter = doubleDamageAdapter
        binding.defensiveLayout.layoutDoubleDamage.tvDmgValue.text = resources.getString(R.string.double_damage)
        binding.defensiveLayout.layoutDoubleDamage.tvDmgValue.setTextColor(resources.getColor(R.color.success, this.context?.theme))

        binding.defensiveLayout.layoutHalfDamage.gwtype.layoutManager = GridLayoutManager(this.context, 3)
        /*val sd = ShapeDrawable(RectShape())
        sd.paint.color = resources.getColor(R.color.success)
        sd.paint.style = Paint.Style.STROKE
        sd.paint.strokeWidth = 8.0F
        binding.offensiveLayout.layoutHalfDamage.root.background = sd*/
        binding.defensiveLayout.layoutHalfDamage.root.background.alpha = 25
        if (type.damage_relations.double_damage_from.isEmpty()) {
            binding.defensiveLayout.layoutDoubleDamage.tvNone.visibility = View.VISIBLE
        }


        this.context?.let { ContextCompat.getColor(it, R.color.error) }?.let { binding.defensiveLayout.layoutHalfDamage.root.background.setTint(it) }
        val halfDamageAdapter = TypeAdapter(type.damage_relations.half_damage_from as MutableList<Info>)
        binding.defensiveLayout.layoutHalfDamage.gwtype.adapter = halfDamageAdapter
        binding.defensiveLayout.layoutHalfDamage.tvDmgValue.text = resources.getString(R.string.half_damage)
        binding.defensiveLayout.layoutHalfDamage.tvDmgValue.setTextColor(resources.getColor(R.color.error, this.context?.theme))
        if (type.damage_relations.half_damage_from.isEmpty()) {
            binding.defensiveLayout.layoutHalfDamage.tvNone.visibility = View.VISIBLE
        }


        binding.defensiveLayout.layoutNoDamage.gwtype.layoutManager = GridLayoutManager(this.context, 3)
        this.context?.let { ContextCompat.getColor(it, R.color.surface_2) }?.let { binding.defensiveLayout.layoutNoDamage.root.background.setTint(it) }
        val noDamageAdapter = TypeAdapter(type.damage_relations.no_damage_from as MutableList<Info>)
        binding.defensiveLayout.layoutNoDamage.gwtype.adapter = noDamageAdapter
        binding.defensiveLayout.layoutNoDamage.tvDmgValue.text = resources.getString(R.string.no_damage)
        binding.defensiveLayout.layoutNoDamage.tvDmgValue.setTextColor(resources.getColor(R.color.cold_gray, this.context?.theme))
        if (type.damage_relations.no_damage_from.isEmpty()) {
            binding.defensiveLayout.layoutNoDamage.tvNone.visibility = View.VISIBLE
        }
    }

}
