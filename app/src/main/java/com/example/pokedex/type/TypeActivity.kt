package com.example.pokedex.type

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityTypeBinding
import com.example.pokedex.models.PokemonType
import com.example.pokedex.type.ui.main.SectionsPagerAdapter
import com.example.pokedex.util.Util

class TypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val type = intent.extras?.get("EXTRA_TYPE") as PokemonType

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setBackgroundDrawable(getDrawable(Util.getTypeColor(type.name)))
        binding.tabs.background.setTint(ContextCompat.getColor(this, Util.getTypeColor(type.name)))

        supportActionBar?.title = getString(R.string.type_name, Util.capitalizeFirstLetter(type.name))

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, type)


        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)

    }
}