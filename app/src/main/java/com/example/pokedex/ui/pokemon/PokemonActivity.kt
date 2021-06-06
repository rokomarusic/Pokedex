package com.example.pokedex.ui.pokemon

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.models.Ability
import com.example.pokedex.models.Info
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.Stat
import com.example.pokedex.type.TypeActivity
import com.example.pokedex.util.Util
import com.example.pokedex.viewmodel.PokemonViewModel
import kotlinx.android.synthetic.main.pokemon_basic_info.view.*

class PokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonBinding
    private val model: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pokemon = intent.extras?.get("EXTRA_POKEMON") as Pokemon

        supportActionBar?.title = Util.capitalizeFirstLetter(pokemon.name)

        binding.pokemonBasicInfo.pokemonBigImg.load(pokemon.sprites.other.officialArtwork.front_default) { size(512) }

        binding.pokemonBasicInfo.tvPokedexNumValue.text = Util.returnId(pokemon.order)

        model.getFavourites(this)

        model.favourites.observe(this, {
            if (model.favourites.value?.let { Util.isFavourite(pokemon, it) } == true) {
                binding.favImg.load(R.drawable.ic_star_1) { size(64) }
            } else {
                binding.favImg.load(R.drawable.ic_star_0) { size(64) }
            }
        })
        //oznaka favorita


        binding.favImg.setOnClickListener {
            if (pokemon.isFavourite) {
                binding.favImg.load(R.drawable.ic_star_0)
                model.deletePokemon(pokemon, this)
                model.favourites.value?.remove(pokemon)
            } else {
                binding.favImg.load(R.drawable.ic_star_1)
                model.insertPokemon(pokemon, this)
                model.favourites.value?.add(pokemon)
            }
            pokemon.isFavourite = !pokemon.isFavourite
        }

        //prvi type slot
        binding.pokemonBasicInfo.type1.tvTypeName.text = Util.capitalizeFirstLetter(pokemon.types[0].type.name)
        binding.pokemonBasicInfo.type1.root.background.setTint(ContextCompat.getColor(this, Util.getTypeColor(pokemon.types[0].type.name)))

        model.getType(pokemon.types[0].type.url, 1)

        model.type1.observe(this, {
            binding.pokemonBasicInfo.type1.root.setOnClickListener {
                val intent = Intent(this, TypeActivity::class.java)
                intent.putExtra("EXTRA_TYPE", model.type1.value)
                intent.putExtra("EXTRA_POKEMON", pokemon)
                startActivity(intent)
            }
        })


        //postavljanje drugog type slota, ukoliko ga pokemon ima
        if (pokemon.types.size == 2) {
            binding.pokemonBasicInfo.type2.tvTypeName.text = Util.capitalizeFirstLetter(pokemon.types[1].type.name)
            binding.pokemonBasicInfo.type2.root.background.setTint(ContextCompat.getColor(this, Util.getTypeColor(pokemon.types[1].type.name)))

            model.getType(pokemon.types[1].type.url, 2)


            model.type2.observe(this, {
                binding.pokemonBasicInfo.type2.root.setOnClickListener {
                    val intent = Intent(this, TypeActivity::class.java)
                    intent.putExtra("EXTRA_TYPE", model.type2.value)
                    intent.putExtra("EXTRA_POKEMON", pokemon)
                    startActivityForResult(intent, 1)
                }
            })

        } else {
            binding.pokemonBasicInfo.type2.root.visibility = View.GONE
        }

        //Postavljanje Grid View-a za ability-je

        val abilityAdapter = AbilityAdapter(pokemon.abilities as MutableList<Ability>)
        binding.abilities.gw.layoutManager = GridLayoutManager(this, 2)
        binding.abilities.gw.adapter = abilityAdapter

        //Postavljanje tezine i visine pokemona
        binding.hwPokemon.weightTile.imgInfoTile.load(R.drawable.ic_weight) { size(64) }
        binding.hwPokemon.weightTile.infoName.text = resources.getString(R.string.weight)
        binding.hwPokemon.weightTile.infoData.text = resources.getString(R.string.weight_value, Util.kgToLbs(pokemon.weight).toString(), (pokemon.weight.toFloat() / 10).toString())

        binding.hwPokemon.heightTile.imgInfoTile.load(R.drawable.ic_height) { size(64) }
        binding.hwPokemon.heightTile.infoName.text = resources.getString(R.string.height)
        binding.hwPokemon.heightTile.infoData.text = Util.heightToImperial(pokemon.height, resources)


        //postavljanje statova
        //total se dodaje kao još jedan stat (makar zapravo nije)
        val stats = mutableListOf<Stat>()
        stats.addAll(pokemon.stats)
        stats.add(Stat(Util.getTotalStat(pokemon.stats), 0, Info(resources.getString(R.string.total), "")))
        val statAdapter = StatAdapter(stats)
        binding.stats.statlist.layoutManager = LinearLayoutManager(this)
        binding.stats.statlist.adapter = statAdapter

        //postavljanje evolucija
        model.getEvolutionChain(pokemon.species.url)

        binding.evolutionChainRecycler.layoutManager = LinearLayoutManager(this)

        model.evolutionMap.observe(this, {
            val evolutionAdapter = model.minLvlMap.value?.let { it1 -> EvolutionChainAdapter(it, it1) }
            binding.evolutionChainRecycler.adapter = evolutionAdapter
        })
    }
}