package renard.remi.ping.extension

import android.content.Context
import androidx.datastore.dataStore
import renard.remi.ping.data.datastore.AppSettingsSerializer

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)