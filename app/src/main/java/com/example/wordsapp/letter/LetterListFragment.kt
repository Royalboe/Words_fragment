package com.example.wordsapp.letter

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.R
import com.example.wordsapp.data.SettingsDataStore
import com.example.wordsapp.databinding.FragmentLetterListBinding
import kotlinx.coroutines.launch


class LetterListFragment : Fragment() {
       private lateinit var settingsDataStore: SettingsDataStore
       private var _binding: FragmentLetterListBinding? = null
       private val binding get() = _binding!!
       private lateinit var recyclerView: RecyclerView
       //  returns the Context this fragment is currently associated with, initialized to requireContext()
       private lateinit var contexts: Context
       private val letterViewModel: LetterViewModel by viewModels()


       // Set the setHasOptionMenu to true to allow option menu
       override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setHasOptionsMenu(true)
       }

       // Override the onCreateView to inflate the layout
       override fun onCreateView(
              inflater: LayoutInflater, container: ViewGroup?,
              savedInstanceState: Bundle?
       ): View {
              _binding = FragmentLetterListBinding.inflate(inflater, container, false)
              return binding.root
       }

       // Bind the views contained in the inflated layout
       override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
              recyclerView = binding.recyclerView
              contexts = requireContext()
              letterViewModel.chooseLayout(contexts, recyclerView)
              settingsDataStore = SettingsDataStore(contexts)
              settingsDataStore.preferencesFlow.asLiveData().observe(viewLifecycleOwner, {
                     letterViewModel.isLinearLayoutManager = it
                     letterViewModel.chooseLayout(contexts,recyclerView)
                     activity?.invalidateOptionsMenu()
              })
       }

       // Sent the _binding property to  null to restore the value
       override fun onDestroyView() {
              super.onDestroyView()
              _binding = null
       }

       // Unlike the activity class the fragment class does not have a global MenuInflater property
       // the property is however passed into the parameter,
       override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
              inflater.inflate(R.menu.layout_menu, menu)

              val layoutButton = menu.findItem(R.id.action_switch_layout)
              letterViewModel.setIcon(contexts, layoutButton)
       }



       override fun onOptionsItemSelected(item: MenuItem): Boolean {
              return when (item.itemId) {
                     R.id.action_switch_layout -> {
                            // Sets isLinearLayoutManager (a Boolean) to the opposite value
                            letterViewModel.isLinearLayoutManager = !letterViewModel.isLinearLayoutManager
                            // Sets layout and icon
                            letterViewModel.chooseLayout(contexts, recyclerView)
                            letterViewModel.setIcon(contexts, item)
                            lifecycleScope.launch {
                                   settingsDataStore
                                          .saveLayoutToPreferencesStore(
                                                 letterViewModel.isLinearLayoutManager, contexts
                                          )
                            }

                            return true
                     }
                     //  Otherwise, do nothing and use the core event handling

                     // when clauses require that all possible paths be accounted for explicitly,
                     //  for instance both the true and false cases if the value is a Boolean,
                     //  or an else to catch all unhandled cases.
                     else -> super.onOptionsItemSelected(item)
              }
       }

}