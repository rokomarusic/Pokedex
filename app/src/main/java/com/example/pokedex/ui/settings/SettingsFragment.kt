package com.example.pokedex.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentSettingsBinding
import com.example.pokedex.locale.MyPreference
import com.example.pokedex.type.TypeActivity
import com.example.pokedex.viewmodel.PokemonViewModel

class SettingsFragment : Fragment() {

    lateinit var preference: MyPreference

    private var _binding: FragmentSettingsBinding? = null

    private var firstSelection = true

    private val binding get() = _binding!!

    private val model: PokemonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val view = binding.root

        model.getFavourites(requireContext())

        binding.buttonInfo.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        model.favourites.observe(viewLifecycleOwner, {
            binding.clearFavsButton.setOnClickListener {
                val dialogFragment = ClearDialogFragment(model)
                dialogFragment.show(childFragmentManager, "")
            }
        })



        preference = MyPreference(binding.root.context)

        binding.langSpinner.adapter = context?.resources?.let {
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                it.getStringArray(R.array.langs)
            )
        }

        var selectedLang = preference.getLang()
        if (selectedLang.equals("en")) {
            binding.langSpinner.setSelection(0)
        } else {
            binding.langSpinner.setSelection(1)
            selectedLang = "hr"
        }
        binding.langSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                if (firstSelection) {
                    firstSelection = false
                } else {
                    val lang = parent?.getItemAtPosition(position) as String
                    val chosenLang = when (lang) {
                        context?.getString(R.string.cro) -> "hr"
                        context?.getString(R.string.eng) -> "en"
                        else -> ""
                    }
                    if (!selectedLang?.equals(chosenLang)!!) {
                        preference.setLang(if (chosenLang.equals("hr")) "hr" else "en")
                        activity?.recreate()
                    }


                }
            }

        }



        return view
    }
}