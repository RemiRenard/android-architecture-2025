package renard.remi.ping.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import renard.remi.ping.domain.repository.SettingsRepository
import renard.remi.ping.extension.dataStore
import renard.remi.ping.ui.theme.Palette
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    override suspend fun updateAppColors(palette: Palette) {
        context.dataStore.updateData {
            it.copy(palette = palette)
        }
    }

    override suspend fun useDynamicsColors(shouldUse: Boolean) {
        context.dataStore.updateData {
            it.copy(useDynamicColors = shouldUse)
        }
    }

    override suspend fun setDarkMode(isInDarkMode: Boolean) {
        context.dataStore.updateData {
            it.copy(isInDarkMode = isInDarkMode)
        }
    }

    override suspend fun getDynamicsColors() = context.dataStore.data.first().useDynamicColors

    override suspend fun getIsInDarkMode() = context.dataStore.data.first().isInDarkMode

    override suspend fun getPaletteColors() = context.dataStore.data.first().palette
}