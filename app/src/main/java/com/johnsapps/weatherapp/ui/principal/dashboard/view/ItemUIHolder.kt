package com.johnsapps.weatherapp.ui.principal.dashboard.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.databinding.ItemCardBinding
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.ItemUI

class ItemUIHolder(view: View) : ViewHolder(view) {
    private val binding = ItemCardBinding.bind(view)

    fun render(item: ItemUI) {
        val context = binding.root.context
        binding.ivIconItem.setImageResource(item.imageResource)
        if (item.title.isNullOrEmpty()) {
            binding.tvTitleItem.text = context.getText(item.titleInt)
        } else {
            binding.tvTitleItem.text =
                String.format(
                    context.resources.getString(R.string.label_item_min),
                    item.title
                )
        }
        binding.tvValueItem.text = setSubTitleFormat(item.imageResource, item.subTitle, context)
    }

    private fun setSubTitleFormat(resource: Int, value: String, context: Context): String {
        return when (resource) {
            R.drawable.ic_temperature -> {
                String.format(
                    context.resources.getString(R.string.label_item_min),
                    value
                )
            }

            R.drawable.ic_weather -> {
                String.format(
                    context.resources.getString(R.string.label_item_Centigrade),
                    value
                )

            }

            R.drawable.ic_degree -> {
                String.format("$value %%")

            }

            R.drawable.ic_wind -> {

                String.format(
                    context.resources.getString(R.string.label_item_kilometer),
                    value
                )

            }

            else -> {
                value
            }
        }
    }
}