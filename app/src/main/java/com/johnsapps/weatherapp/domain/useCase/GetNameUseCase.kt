package com.johnsapps.weatherapp.domain.useCase

import com.johnsapps.weatherapp.data.repository.UserRepository
import javax.inject.Inject

class GetNameUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.user

}