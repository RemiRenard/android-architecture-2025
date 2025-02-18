package renard.remi.ping.ui.home

import renard.remi.ping.domain.model.User

data class HomeState(
    val isLoading : Boolean = false,
    val user: User? = null
)