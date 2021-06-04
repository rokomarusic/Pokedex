package com.example.pokedex.ui.search

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentSearchBinding
import com.example.pokedex.databinding.FragmentSettingsBinding
import com.example.pokedex.ui.pokemon.PokemonActivity
import com.example.pokedex.viewmodel.PokemonViewModel
import java.util.ArrayList

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private var firstSelection = true

    private val binding get() = _binding!!

    private val model: PokemonViewModel by activityViewModels()

    private lateinit var adapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val view = binding.root

        adapter = SearchAdapter(model)

        model.favourites.value?.clear()

        model.getFavourites(requireContext())

        if (model.favourites.value.isNullOrEmpty()) {
            observeLiveData()
            initializeList()
            initializeAutoComplete()
        }

        model.favourites.observe(viewLifecycleOwner, {
            if (binding.list.adapter == null) {
                observeLiveData()
                initializeList()
                initializeAutoComplete()
            } else {
                adapter.notifyDataSetChanged()
            }
        })

        return view
    }

    private fun initializeAutoComplete() {

        model.hints.observe(viewLifecycleOwner, {
            val adapterActv = context?.resources?.let {
                model.hints.value?.let { it1 ->
                    ArrayAdapter(
                            requireContext(),
                            R.layout.select_dialog_item,
                            it1.map { pokemon -> pokemon.name }
                    )
                }
            }

            binding.actv.setAdapter(adapterActv)
        })

        binding.actv.threshold = 3

        var thresholdReached = false
        binding.actv.addTextChangedListener {
            if (!binding.actv.enoughToFilter()) {
                thresholdReached = false
            }
            if (binding.actv.enoughToFilter()) {
                if (!thresholdReached) {
                    thresholdReached = true
                    model.getHints(binding.actv.text.toString())
                } else {
                    model.hints.value?.filter { it -> it.name.startsWith(binding.actv.text.toString()) }
                }
            }


        }

        binding.actv.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position)
            val pokemonList = model.hints.value?.filter { it.name == item }
            val intent = Intent(context, PokemonActivity::class.java)
            intent.putExtra("EXTRA_POKEMON", pokemonList?.get(0))
            startActivity(intent)
        }


    }

    private fun initializeList() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun observeLiveData() {
        model.getPokemons().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        binding.list.adapter?.notifyDataSetChanged()
    }

}