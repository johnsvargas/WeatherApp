package com.johnsapps.weatherapp.domain.models

import com.johnsapps.weatherapp.data.local.UserEntity
import com.johnsapps.weatherapp.ui.principal.profile.viewModel.UserUI

data class User (
    val id: Int? = null,
    val name: String,
    val email: String,
    val phoneNumber: String
)

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        this.id?:0,
        this.name,
        this.email,
        this.phoneNumber
    )
}

fun User.toUserUI(): UserUI {
    return UserUI(
        this.name,
        this.email,
        this.phoneNumber
    )
}
