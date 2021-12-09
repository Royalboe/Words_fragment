package com.example.wordsapp.letter

import android.content.Context
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.R

class LetterViewModel: ViewModel() {
    var isLinearLayoutManager = true

    fun chooseLayout(context: Context, recyclerView: RecyclerView) {
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = LetterAdapter(context)
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = LetterAdapter(context)
            }
        }
    }
    fun setIcon(context: Context, menuItem: MenuItem?) {
        if (menuItem == null)
            return
        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(context, R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(context, R.drawable.ic_linear_layout)
    }

}