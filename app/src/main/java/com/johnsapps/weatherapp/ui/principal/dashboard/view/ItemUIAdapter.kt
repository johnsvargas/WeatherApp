package com.johnsapps.weatherapp.ui.principal.dashboard.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.ItemUI

class ItemUIAdapter(
    private val list:List<ItemUI>
): RecyclerView.Adapter<ItemUIHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemUIHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemUIHolder(layoutInflater.inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemUIHolder, position: Int) {
        val itemUI = list[position]
        holder.render(itemUI)
    }
}