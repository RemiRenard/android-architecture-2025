package renard.remi.ping.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import renard.remi.ping.extension.dataStore
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(
        MainState(
            isConnected = runBlocking {
                context.dataStore.data.first().accessToken?.isNotBlank() == true
            }
        )
    )

    val state: StateFlow<MainState>
        get() = _state.asStateFlow()

    init {
        checkSession(context = context)
    }

    private fun checkSession(context: Context) = viewModelScope.launch {
        context.dataStore.data.collect {
            _state.update { state ->
                state.copy(isConnected = it.accessToken?.isNotBlank() == true)
            }
        }
    }
}
