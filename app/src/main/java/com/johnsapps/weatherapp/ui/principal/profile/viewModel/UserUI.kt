package com.johnsapps.weatherapp.ui.principal.profile.viewModel

import com.johnsapps.weatherapp.domain.models.User

data class UserUI(
    val name: String,
    val email: String,
    val phoneNumber: String
)

fun UserUI.toUser(id: Int): User {
    return User(
        id, this.name, this.email, this.phoneNumber
    )
}