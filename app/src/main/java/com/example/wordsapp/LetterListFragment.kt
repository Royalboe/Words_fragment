package com.example.wordsapp

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding



class LetterListFragment : Fragment() {
       private var _binding: FragmentLetterListBinding? = null
       private val binding get() = _binding!!
       private lateinit var recyclerView: RecyclerView
       private var isLinearLayoutManager = true
       //  returns the Context this fragment is currently associated with, initialized to requireContext()
       private lateinit var contexts: Context


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
              chooseLayout()
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
              setIcon(layoutButton)
       }


       private fun chooseLayout() {
               contexts = requireContext()
              when (isLinearLayoutManager) {
                     true -> {
                            recyclerView.layoutManager = LinearLayoutManager(contexts)
                            recyclerView.adapter = LetterAdapter(contexts)
                     }
                     false -> {
                            recyclerView.layoutManager = GridLayoutManager(contexts, 4)
                            recyclerView.adapter = LetterAdapter(contexts)
                     }
              }
       }
       private fun setIcon(menuItem: MenuItem?) {
              if (menuItem == null)
                     return
              menuItem.icon =
                     if (isLinearLayoutManager)
                            ContextCompat.getDrawable(contexts, R.drawable.ic_grid_layout)
                     else ContextCompat.getDrawable(contexts, R.drawable.ic_linear_layout)
       }

       override fun onOptionsItemSelected(item: MenuItem): Boolean {
              return when (item.itemId) {
                     R.id.action_switch_layout -> {
                            // Sets isLinearLayoutManager (a Boolean) to the opposite value
                            isLinearLayoutManager = !isLinearLayoutManager
                            // Sets layout and icon
                            chooseLayout()
                            setIcon(item)

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