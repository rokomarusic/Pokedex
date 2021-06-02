package com.example.pokedex.ui.search

import android.R
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
import com.example.pokedex.viewmodel.PokemonViewModel
import java.util.ArrayList

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private var firstSelection = true

    private val binding get() = _binding!!

    private val model: PokemonViewModel by activityViewModels()

    private val adapter = SearchAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val view = binding.root

        observeLiveData()
        initializeList()

        initializeAutoComplete()

        return view
    }

    private fun initializeAutoComplete() {

        model.hints.observe(viewLifecycleOwner, {
            val adapterActv = context?.resources?.let {
                model.getPokemons().value?.let { it1 ->
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
                } else {
                    model.hints.value?.filter { it -> it.startsWith(binding.actv.text.toString()) }
                }
            }


        }

        binding.actv.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                model.pokemonLiveData.value?.filter { it -> it.name.startsWith(binding.actv.text.toString()) }
                adapter.notifyDataSetChanged()

            }
            return@setOnEditorActionListener true;
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
}