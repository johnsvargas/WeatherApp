package com.johnsapps.weatherapp.domain.models

import com.johnsapps.weatherapp.R
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.ItemUI
import com.johnsapps.weatherapp.ui.principal.dashboard.viewModel.uiModels.WeatherLocaleUI

data class WeatherItem(
    val name: String,
    val country: String,
    val currentTemp: Float,
    val currentWindKph: Float,
    val currentDegree: Int,
    val maxTemp: Float,
    val minTemp: Float,
    val sunRise: String,
    val sunSet: String
)

fun WeatherItem.toWeatherUI(): WeatherLocaleUI {
    return WeatherLocaleUI(
        sunRise = this.sunRise,
        sunSet = this.sunSet,
        country = this.country,
        name = this.name
    )
}

fun WeatherItem.toListOfItemUI(): List<ItemUI> {
    val listMutable: MutableList<ItemUI> = mutableListOf()
    listMutable.add(
        ItemUI(
        titleInt = R.string.label_item_weather,
        subTitle = this.currentTemp.toString(),
        imageResource = R.drawable.ic_weather,
    ))

    listMutable.add(
        ItemUI(
            titleInt = R.string.label_item_degree,
            subTitle = this.currentDegree.toString(),
            imageResource = R.drawable.ic_degree,
        ))

    listMutable.add(
        ItemUI(
            title = this.maxTemp.toString(),
            titleInt = 0 ,
            subTitle = this.minTemp.toString(),
            imageResource = R.drawable.ic_temperature,
        ))

    listMutable.add(
        ItemUI(
            titleInt = R.string.label_item_wind,
            subTitle = this.currentWindKph.toString(),
            imageResource = R.drawable.ic_wind,
        ))
    return listMutable.toList()
}